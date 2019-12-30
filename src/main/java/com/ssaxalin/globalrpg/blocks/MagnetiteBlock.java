package com.ssaxalin.globalrpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class MagnetiteBlock extends Block {

    public MagnetiteBlock() {
        super(Properties.create(Material.IRON)
        .sound(SoundType.METAL)
        .hardnessAndResistance(3.0f)
        .lightValue(14)
        );
        setRegistryName("magnetite");
    }
}
