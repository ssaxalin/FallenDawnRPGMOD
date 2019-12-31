package com.ssaxalin.globalrpg.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
public class titan extends Block {
    public titan()
    {
        super(Properties.create(Material.IRON)
                .sound(SoundType.METAL)
                .hardnessAndResistance(3.0f)
                .lightValue(14)
        );
        setRegistryName("titan");
    }
}
