package com.oceangrave.fallendawn;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Feature;
import net.minecraft.world.gen.feature.OreFeatureConfig;
import net.minecraft.world.gen.placement.CountRangeConfig;
import net.minecraft.world.gen.placement.Placement;
import net.minecraftforge.fml.common.registry.GameRegistry;
/**
 * @author Oceangrave (ssaxalin) ru community
 */

public class ModOreGen {
    public static void generate(GenerationStage.Decoration stage, ConfiguredFeature<?, ?> feature, Biome... biomes) {
        for(Biome biome : biomes) biome.addFeature(stage,feature);

    }
    public static void generateOre(OreFeatureConfig.FillerBlockType target, BlockState state, int vein_size, int chunk_amount, int min_height, int max_height_base, int maximum, Biome...biomes)
    {
        generate(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(target,state,vein_size)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(chunk_amount,min_height,max_height_base,maximum))),biomes);
    }
    public static void generateOre(OreFeatureConfig.FillerBlockType target, BlockState state, int vein_size, int chunk_amount, int min_height, int max_height_base, int maximum)
    {

        Biome[] biomes = {};
        generate(GenerationStage.Decoration.UNDERGROUND_ORES, Feature.ORE.func_225566_b_(new OreFeatureConfig(target,state,vein_size)).func_227228_a_(Placement.COUNT_RANGE.func_227446_a_(new CountRangeConfig(chunk_amount,min_height,max_height_base,maximum))), GameRegistry.findRegistry(Biome.class).getValues().toArray(biomes));
    }
}
