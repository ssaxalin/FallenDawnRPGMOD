package com.oceangrave.fallendawn.tileentity;

import com.oceangrave.fallendawn.init.ModTileEntityTypes;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.math.AxisAlignedBB;

public class SeparatorTileEntity extends TileEntity {


    public SeparatorTileEntity(final TileEntityType<?> type) {
        super(type);
    }

    public SeparatorTileEntity() {
        this(ModTileEntityTypes.separator);
    }
    @Override
    public AxisAlignedBB getRenderBoundingBox() {
        return INFINITE_EXTENT_AABB;
    }

}
