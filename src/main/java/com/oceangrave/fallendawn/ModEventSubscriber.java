package com.oceangrave.fallendawn;

import com.google.common.base.Preconditions;
import com.oceangrave.fallendawn.init.ModItemGroup;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.annotation.Nonnull;

@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD)
public final class ModEventSubscriber {

    private static final Logger LOGGER = LogManager.getLogger(Main.MODID + " Mod Event Subscriber");

    //Blocks, ores
    @SubscribeEvent
    public static void onRegisterBlock(RegistryEvent.Register<Block> event)
    {
        event.getRegistry().registerAll(
                setup(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F, 4.0F)), "magnetite_ore")
        );
        event.getRegistry().registerAll(
                setup(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F, 4.0F)), "titan_ore")
        );
    }

    //All Items
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        event.getRegistry().registerAll(
                setup(new Item(new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP)), "magnetite_ingot")

        );
        event.getRegistry().registerAll(
                setup(new Item(new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP)), "titan_ingot")
        );
        event.getRegistry().registerAll(
                setup(new Item(new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP)), "mantid_ingot")
        );
        ForgeRegistries.BLOCKS.getValues().parallelStream()
                .filter(block -> block.getRegistryName().getNamespace().equals(Main.MODID))
                // Register the BlockItem for the block
                .forEach(block -> {
                    // Make the properties, and make it so that the item will be on our ItemGroup (CreativeTab)
                    final Item.Properties properties = new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP);
                    // Create the new BlockItem with the block and it's properties
                    final BlockItem blockItem = new BlockItem(block, properties);
                    // Setup the new BlockItem with the block's registry name and register it
                    registry.register(setup(blockItem, block.getRegistryName()));
                });
        LOGGER.debug("Registered Items");
    }

    //For TileEntity
    @SubscribeEvent
    public static void onRegisterTileEntityTypes(@Nonnull final RegistryEvent.Register<TileEntityType<?>> event) {
        // Register your TileEntityTypes here if you have them
        event.getRegistry().registerAll(
        );
        LOGGER.debug("Registered TileEntityTypes");
    }

    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final String name) {
        Preconditions.checkNotNull(name, "Name to assign to entry cannot be null!");
        return setup(entry, new ResourceLocation(Main.MODID, name));
    }


    @Nonnull
    private static <T extends IForgeRegistryEntry<T>> T setup(@Nonnull final T entry, @Nonnull final ResourceLocation registryName) {
        Preconditions.checkNotNull(entry, "Entry cannot be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign to entry cannot be null!");
        entry.setRegistryName(registryName);
        return entry;
    }
}