package com.ssaxalin.globalrpg;

import com.ssaxalin.globalrpg.blocks.*;
import com.ssaxalin.globalrpg.items.magnetiteingot;
import com.ssaxalin.globalrpg.items.titaningot;
import com.ssaxalin.globalrpg.setup.ClientProxy;
import com.ssaxalin.globalrpg.setup.IProxy;
import com.ssaxalin.globalrpg.setup.ModSetup;
import com.ssaxalin.globalrpg.setup.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod("globalrpg")
public class MainGlobalRpg {
    public static IProxy proxy = DistExecutor.runForDist(() -> ClientProxy::new, () -> ServerProxy::new);
   public static ModSetup setup = new ModSetup();
    private static final Logger LOGGER = LogManager.getLogger();

    public MainGlobalRpg()
    {
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
    }

    private void setup(final FMLCommonSetupEvent event)
    {
    setup.init();
    proxy.init();
    }
    @Mod.EventBusSubscriber(bus=Mod.EventBusSubscriber.Bus.MOD)
    public  static class  RegistryEvents
    {
        @SubscribeEvent
        public static void onBlocksRegistry(final RegistryEvent.Register<Block> blockRegistryEvent)
        {
            //new blocks
            blockRegistryEvent.getRegistry().register(new MagnetiteBlock());
            blockRegistryEvent.getRegistry().register(new titan());
            blockRegistryEvent.getRegistry().register(new separator());
        }
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> blockRegistryEvent)
        {
            //регестрация предметов
            Item.Properties properties = new Item.Properties()
                    .group(setup.itemGroup);
            //ore
            blockRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.Magnetite, properties).setRegistryName("magnetite"));
            blockRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.titan,properties).setRegistryName("titan"));

            //furnace for all items
            blockRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.separator,properties).setRegistryName("separator"));

            //ingot
            blockRegistryEvent.getRegistry().register(new magnetiteingot());
            blockRegistryEvent.getRegistry().register(new titaningot());

        }
        @SubscribeEvent
        public static void onTileEntityRegistry(final RegistryEvent.Register<TileEntityType<?>> event)
        {
            event.getRegistry().register(TileEntityType.Builder.create(Tile::new, ModBlocks.separator).build(null).setRegistryName("separator"));
        }
    }
}
