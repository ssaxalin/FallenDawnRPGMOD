package com.oceangrave.fallendawn.client;

import com.oceangrave.fallendawn.Main;
import com.oceangrave.fallendawn.client.gui.ModFurnaceScreen;
import com.oceangrave.fallendawn.init.ModContainerTypes;
import net.minecraft.client.gui.ScreenManager;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Subscribe to events from the MOD EventBus that should be handled on the PHYSICAL CLIENT side in this class
 *
 * @author Cadiboo
 */
@EventBusSubscriber(modid = Main.MODID, bus = EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public final class ClientModEventSubscriber {

	private static final Logger LOGGER = LogManager.getLogger(Main.MODID + " Client Mod Event Subscriber");

	/**
	 * We need to register our renderers on the client because rendering code does not exist on the server
	 * and trying to use it on a dedicated server will crash the game.
	 * <p>
	 * This method will be called by Forge when it is time for the mod to do its client-side setup
	 * This method will always be called after the Registry events.
	 * This means that all Blocks, Items, TileEntityTypes, etc. will all have been registered already
	 */
	@SubscribeEvent
	public static void onFMLClientSetupEvent(final FMLClientSetupEvent event) {

		// Register TileEntity Renderers

		//ClientRegistry.bindTileEntityRenderer(ModTileEntityTypes.ELECTRIC_FURNACE, new ElectricFurnaceTileEntityRenderer(TileEntityRendererDispatcher.instance));
		LOGGER.debug("Registered TileEntity Renderers");

		// Register Entity Renderers
//		RenderingRegistry.registerEntityRenderingHandler(ModEntityTypes.YOUR_ENTITY_TYPE, YourEntityRenderer::new);
//		LOGGER.debug("Registered Entity Renderers");

		// Register ContainerType Screens
		ScreenManager.registerFactory(ModContainerTypes.MOD_FURNACE, ModFurnaceScreen::new);
		LOGGER.debug("Registered ContainerType Screens");

	}

}
