package com.oceangrave.fallendawn;

import com.google.common.base.Preconditions;
import com.oceangrave.fallendawn.block.ModFurnaceBlock;
import com.oceangrave.fallendawn.container.ModFurnaceContainer;
import com.oceangrave.fallendawn.init.ModBlocks;
import com.oceangrave.fallendawn.init.ModItemGroup;
import com.oceangrave.fallendawn.init.ToolMaterialList;
import com.oceangrave.fallendawn.item.TitanAxe;
import com.oceangrave.fallendawn.item.TitanSword;
import com.oceangrave.fallendawn.tileentity.ModFurnaceTileEntity;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.inventory.container.ContainerType;
import net.minecraft.item.*;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.extensions.IForgeContainerType;
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
                setup(new Block(Block.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.PLANT)), "fear_grass"),
                setup(new Block(Block.Properties.create(Material.ORGANIC).tickRandomly().hardnessAndResistance(0.6F).sound(SoundType.PLANT)), "fear_dirt"),
                setup(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F, 4.0F)), "magnetite_ore"),
                setup(new Block(Block.Properties.create(Material.IRON).hardnessAndResistance(5.0F, 4.0F)), "titan_ore"),
                setup(new ModFurnaceBlock(Block.Properties.create(Material.ROCK).hardnessAndResistance(3.5F).lightValue(13)), "mod_furnace"));
    }

    //All Items
    @SubscribeEvent
    public static void onRegisterItems(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        event.getRegistry().registerAll(
                setup(new Item(new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP)), "magnetite_ingot"),
                setup(new Item(new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP)), "titan_ingot"),
                setup(new Item(new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP)), "mantid_ingot"),
                setup(new TitanAxe(ToolMaterialList.titanmaterialaxe, -4.0F, -2.5F,  new TitanAxe.Properties().group(ModItemGroup.MOD_ITEM_GROUP)),"titan_axe"),
                setup(new TitanSword(ToolMaterialList.titanmaterialsword, 3, 3, -2.0F,  new TitanSword.Properties().group(ModItemGroup.MOD_ITEM_GROUP)),"titan_sword")
        );
        ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> block.getRegistryName().getNamespace().equals(Main.MODID))
                .forEach(block -> {
                    final Item.Properties properties = new Item.Properties().group(ModItemGroup.MOD_ITEM_GROUP);
                    final BlockItem blockItem = new BlockItem(block, properties);
                    registry.register(setup(blockItem, block.getRegistryName()));
                });
        LOGGER.debug("Registered Items");
    }
    @SubscribeEvent
    public static void onRegisterTileEntityTypes(@Nonnull final RegistryEvent.Register<TileEntityType<?>> event) {
        event.getRegistry().registerAll(
                setup(TileEntityType.Builder.create(ModFurnaceTileEntity::new, ModBlocks.MOD_FURNACE).build(null), "mod_furnace")
        );
        LOGGER.debug("Registered TileEntityTypes");
    }



    //For TileEntity
    @SubscribeEvent
    public static void onRegisterContainerTypes(@Nonnull final RegistryEvent.Register<ContainerType<?>> event) {
        event.getRegistry().registerAll(
                setup(IForgeContainerType.create(ModFurnaceContainer::new), "mod_furnace")
        );
        LOGGER.debug("Registered ContainerTypes");
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