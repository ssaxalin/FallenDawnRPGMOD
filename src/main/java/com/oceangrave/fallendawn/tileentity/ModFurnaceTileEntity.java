package com.oceangrave.fallendawn.tileentity;

import com.oceangrave.fallendawn.block.ModFurnaceBlock;
import com.oceangrave.fallendawn.container.ModFurnaceContainer;
import com.oceangrave.fallendawn.init.ModBlocks;
import com.oceangrave.fallendawn.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.AbstractCookingRecipe;
import net.minecraft.item.crafting.FurnaceRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.FurnaceTileEntity;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.RangedWrapper;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Optional;


public class ModFurnaceTileEntity extends TileEntity implements ITickableTileEntity, INamedContainerProvider {

	public static final int FUEL_SLOT = 0;
	public static final int INPUT_SLOT = 1;
	public static final int OUTPUT_SLOT = 2;

	private static final String INVENTORY_TAG = "inventory";
	private static final String SMELT_TIME_LEFT_TAG = "smeltTimeLeft";
	private static final String MAX_SMELT_TIME_TAG = "maxSmeltTime";
	private static final String FUEL_BURN_TIME_LEFT_TAG = "fuelBurnTimeLeft";
	private static final String MAX_FUEL_BURN_TIME_TAG = "maxFuelBurnTime";

	public final ItemStackHandler inventory = new ItemStackHandler(3) {
		@Override
		public boolean isItemValid(final int slot, @Nonnull final ItemStack stack) {
			switch (slot) {
				case FUEL_SLOT:
					return FurnaceTileEntity.isFuel(stack);
				case INPUT_SLOT:
					return isInput(stack);
				case OUTPUT_SLOT:
					return isOutput(stack);
				default:
					return false;
			}
		}

		@Override
		protected void onContentsChanged(final int slot) {
			super.onContentsChanged(slot);
			ModFurnaceTileEntity.this.markDirty();
		}
	};

	// Store the capability lazy optionals as fields to keep the amount of objects we use to a minimum
	private final LazyOptional<ItemStackHandler> inventoryCapabilityExternal = LazyOptional.of(() -> this.inventory);
	// Machines (hoppers, pipes) connected to this furnace's top can only insert/extract items from the input slot
	private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalUp = LazyOptional.of(() -> new RangedWrapper(this.inventory, INPUT_SLOT, INPUT_SLOT + 1));
	// Machines (hoppers, pipes) connected to this furnace's bottom can only insert/extract items from the output slot
	private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalDown = LazyOptional.of(() -> new RangedWrapper(this.inventory, OUTPUT_SLOT, OUTPUT_SLOT + 1));
	// Machines (hoppers, pipes) connected to this furnace's side can only insert/extract items from the fuel and input slots
	private final LazyOptional<IItemHandlerModifiable> inventoryCapabilityExternalSides = LazyOptional.of(() -> new RangedWrapper(this.inventory, FUEL_SLOT, INPUT_SLOT + 1));

	public short smeltTimeLeft = -1;
	public short maxSmeltTime = -1;
	public short fuelBurnTimeLeft = -1;
	public short maxFuelBurnTime = -1;
	private boolean lastBurning = false;

	public ModFurnaceTileEntity() {
		super(ModTileEntityTypes.MOD_FURNACE);
	}


	private boolean isInput(final ItemStack stack) {
		if (stack.isEmpty())
			return false;
		return getRecipe(stack).isPresent();
	}

	private boolean isOutput(final ItemStack stack) {
		final Optional<ItemStack> result = getResult(inventory.getStackInSlot(INPUT_SLOT));
		return result.isPresent() && ItemStack.areItemsEqual(result.get(), stack);
	}


	private Optional<FurnaceRecipe> getRecipe(final ItemStack input) {
		// Due to vanilla's code we need to pass an IInventory into RecipeManager#getRecipe so we make one here.
		return getRecipe(new Inventory(input));
	}


	private Optional<FurnaceRecipe> getRecipe(final IInventory inventory) {
		return world.getRecipeManager().getRecipe(IRecipeType.SMELTING, inventory, world);
	}


	private Optional<ItemStack> getResult(final ItemStack input) {
		// Due to vanilla's code we need to pass an IInventory into RecipeManager#getRecipe and
		// AbstractCookingRecipe#getCraftingResult() so we make one here.
		final Inventory dummyInventory = new Inventory(input);
		return getRecipe(dummyInventory).map(recipe -> recipe.getCraftingResult(dummyInventory));
	}

	/**
	 * Called every tick to update our tile entity
	 */
	@Override
	public void tick() {
		if (world == null || world.isRemote)
			return;



		boolean hasFuel = false;
		if (isBurning()) {
			hasFuel = true;
			--fuelBurnTimeLeft;
		}



		final ItemStack input = inventory.getStackInSlot(INPUT_SLOT);
		final ItemStack result = getResult(input).orElse(ItemStack.EMPTY);

		if (!result.isEmpty() && isInput(input)) {
			final boolean canInsertResultIntoOutput = inventory.insertItem(OUTPUT_SLOT, result, true).isEmpty();
			if (canInsertResultIntoOutput) {
				if (!hasFuel)
					if (burnFuel())
						hasFuel = true;
				if (hasFuel) {
					if (smeltTimeLeft == -1) {
						smeltTimeLeft = maxSmeltTime = getSmeltTime(input);
					} else {
						--smeltTimeLeft;
						if (smeltTimeLeft == 0) {
							inventory.insertItem(OUTPUT_SLOT, result, false);
							if (input.hasContainerItem()) {
								final ItemStack containerStack = input.getContainerItem();
								input.shrink(1);
								insertOrDropStack(FUEL_SLOT, containerStack);
							} else {
								input.shrink(1);
							}
							inventory.setStackInSlot(INPUT_SLOT, input);
							smeltTimeLeft = -1;
						}
					}
				} else
					if (smeltTimeLeft < maxSmeltTime)
						++smeltTimeLeft;
			}
		} else
			smeltTimeLeft = maxSmeltTime = -1;


		if (lastBurning != hasFuel) {

			this.markDirty();

			final BlockState newState = this.getBlockState()
					.with(ModFurnaceBlock.BURNING, hasFuel);

			world.setBlockState(pos, newState, 2);


			lastBurning = hasFuel;
		}

	}


	private void insertOrDropStack(final int slot, final ItemStack stack) {
		final boolean canInsertContainerItemIntoSlot = inventory.insertItem(slot, stack, true).isEmpty();
		if (canInsertContainerItemIntoSlot)
			inventory.insertItem(slot, stack, false);
		else // Drop the stack if we can't insert it
			InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
	}


	private short getSmeltTime(final ItemStack input) {
		return getRecipe(input)
				.map(AbstractCookingRecipe::getCookTime)
				.orElse(200)
				.shortValue();
	}


	private boolean burnFuel() {
		final ItemStack fuelStack = inventory.getStackInSlot(FUEL_SLOT);
		if (!fuelStack.isEmpty()) {
			final int burnTime = ForgeHooks.getBurnTime(fuelStack);
			if (burnTime > 0) {
				fuelBurnTimeLeft = maxFuelBurnTime = ((short) burnTime);
				if (fuelStack.hasContainerItem()) {
					final ItemStack containerStack = fuelStack.getContainerItem();
					fuelStack.shrink(1); // Shrink now to make space in the slot.
					insertOrDropStack(FUEL_SLOT, containerStack);
				} else {
					fuelStack.shrink(1);
				}
				inventory.setStackInSlot(FUEL_SLOT, fuelStack); // Update the data
				return true;
			}
		}
		fuelBurnTimeLeft = maxFuelBurnTime = -1;
		return false;
	}

	public boolean isBurning() {
		return this.fuelBurnTimeLeft > 0;
	}

	/**
	 * Retrieves the Optional handler for the capability requested on the specific side.
	 *
	 * @param cap  The capability to check
	 * @param side The Direction to check from. CAN BE NULL! Null is defined to represent 'internal' or 'self'
	 * @return The requested an optional holding the requested capability.
	 */
	@Nonnull
	@Override
	public <T> LazyOptional<T> getCapability(@Nonnull final Capability<T> cap, @Nullable final Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (side == null)
				return inventoryCapabilityExternal.cast();
			switch (side) {
				case DOWN:
					return inventoryCapabilityExternalDown.cast();
				case UP:
					return inventoryCapabilityExternalUp.cast();
				case NORTH:
				case SOUTH:
				case WEST:
				case EAST:
					return inventoryCapabilityExternalSides.cast();
			}
		}
		return super.getCapability(cap, side);
	}

	@Override
	public void onLoad() {
		super.onLoad();
		// We set this in onLoad instead of the constructor so that TileEntities
		// constructed from NBT (saved tile entities) have this set to the proper value
		if (world != null && !world.isRemote)
			lastBurning = isBurning();
	}

	/**
	 * Read saved data from disk into the tile.
	 */
	@Override
	public void read(final CompoundNBT compound) {
		super.read(compound);
		this.inventory.deserializeNBT(compound.getCompound(INVENTORY_TAG));
		this.smeltTimeLeft = compound.getShort(SMELT_TIME_LEFT_TAG);
		this.maxSmeltTime = compound.getShort(MAX_SMELT_TIME_TAG);
		this.fuelBurnTimeLeft = compound.getShort(FUEL_BURN_TIME_LEFT_TAG);
		this.maxFuelBurnTime = compound.getShort(MAX_FUEL_BURN_TIME_TAG);
	}

	/**
	 * Write data from the tile into a compound tag for saving to disk.
	 */
	@Nonnull
	@Override
	public CompoundNBT write(final CompoundNBT compound) {
		super.write(compound);
		compound.put(INVENTORY_TAG, this.inventory.serializeNBT());
		compound.putShort(SMELT_TIME_LEFT_TAG, this.smeltTimeLeft);
		compound.putShort(MAX_SMELT_TIME_TAG, this.maxSmeltTime);
		compound.putShort(FUEL_BURN_TIME_LEFT_TAG, this.fuelBurnTimeLeft);
		compound.putShort(MAX_FUEL_BURN_TIME_TAG, this.maxFuelBurnTime);
		return compound;
	}

	@Nonnull
	public CompoundNBT getUpdateTag() {
		return this.write(new CompoundNBT());
	}

	/**
	 * Invalidates our tile entity
	 */
	@Override
	public void remove() {
		super.remove();
		// We need to invalidate our capability references so that any cached references (by other mods) don't
		// continue to reference our capabilities and try to use them and/or prevent them from being garbage collected
		inventoryCapabilityExternal.invalidate();
	}

	@Nonnull
	@Override
	public ITextComponent getDisplayName() {
		return new TranslationTextComponent(ModBlocks.MOD_FURNACE.getTranslationKey());
	}


	@Nonnull
	@Override
	public Container createMenu(final int windowId, final PlayerInventory inventory, final PlayerEntity player) {
		return new ModFurnaceContainer(windowId, inventory, this);
	}

}
