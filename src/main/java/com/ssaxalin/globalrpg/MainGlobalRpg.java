package com.ssaxalin.globalrpg;

import com.ssaxalin.globalrpg.blocks.MagnetiteBlock;
import com.ssaxalin.globalrpg.blocks.ModBlocks;
import com.ssaxalin.globalrpg.setup.ClientProxy;
import com.ssaxalin.globalrpg.setup.IProxy;
import com.ssaxalin.globalrpg.setup.ModSetup;
import com.ssaxalin.globalrpg.setup.ServerProxy;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
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
            // register a new block here
            blockRegistryEvent.getRegistry().register(new MagnetiteBlock());
        }
        @SubscribeEvent
        public static void onItemsRegistry(final RegistryEvent.Register<Item> blockRegistryEvent)
        {
            // register a new items here
            Item.Properties properties = new Item.Properties()
                    .group(setup.itemGroup);
            blockRegistryEvent.getRegistry().register(new BlockItem(ModBlocks.Magnetite, properties).setRegistryName("magnetite"));
        }
    }
}
