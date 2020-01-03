package com.oceangrave.fallendawn.block;

import com.oceangrave.fallendawn.init.ModTileEntityTypes;
import net.minecraft.block.BlockState;
import net.minecraft.block.HorizontalBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockReader;

import javax.annotation.Nullable;

public class SeparatorBlock extends HorizontalBlock {
    public SeparatorBlock(Properties properties) {
        super(properties);

    }

    @Override
    public boolean hasTileEntity(final BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(final BlockState state, final IBlockReader world) {
        return ModTileEntityTypes.separator.create();
    }

}
