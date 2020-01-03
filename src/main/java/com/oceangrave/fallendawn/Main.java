package com.oceangrave.fallendawn;

import com.oceangrave.fallendawn.init.ModBlock;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraftforge.fml.common.Mod;
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
    }
    private void setup(final FMLCommonSetupEvent event)
    {
        //fill ~ ~ ~ ~10 ~-30 ~10 air
        ModOreGen.generateOre(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlock.magnetite_ore.getDefaultState(),4,8,0,0,32);
        ModOreGen.generateOre(OreFeatureConfig.FillerBlockType.NATURAL_STONE, ModBlock.titan_ore.getDefaultState(),2,4,0,0,20);
    }
}
