package com.oceangrave.fallendawn;

import com.oceangrave.fallendawn.config.ConfigHolder;
import com.oceangrave.fallendawn.init.ModBlocks;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * @author Oceangrave (ssaxalin) ru community
 */
@Mod(Main.MODID)
public final class Main {
    public static final String MODID = "main";
    public static final Logger LOGGER = LogManager.getLogger(MODID);
    public Main() {
        LOGGER.debug("Hello from Example Mod!");
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        final ModLoadingContext modLoadingContext = ModLoadingContext.get();
        // Register Configs
        modLoadingContext.registerConfig(ModConfig.Type.CLIENT, ConfigHolder.CLIENT_SPEC);
        modLoadingContext.registerConfig(ModConfig.Type.SERVER, ConfigHolder.SERVER_SPEC);
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        //fill ~ ~ ~ ~10 ~-30 ~10 air
        ModOreGen.generateOre(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.magnetite_ore.getDefaultState(),4,8,0,0,32);
        ModOreGen.generateOre(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlocks.titan_ore.getDefaultState(),2,4,0,0,18);
    }
}
