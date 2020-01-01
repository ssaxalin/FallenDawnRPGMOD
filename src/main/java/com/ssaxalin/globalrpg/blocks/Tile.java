package com.ssaxalin.globalrpg.blocks;


import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class Tile extends TileEntity implements ITickableTileEntity {

    public Tile() {
        super(ModBlocks.SEPARATOR_TILE);
    }

    @Override
    public void tick() {
        if (world.isRemote)
        {
            System.out.println("Tile.tick");
        }
    }


}
