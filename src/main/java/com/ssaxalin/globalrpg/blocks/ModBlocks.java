package com.ssaxalin.globalrpg.blocks;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ModBlocks {
    @ObjectHolder("globalrpg:magnetite")
    public static MagnetiteBlock Magnetite;
    @ObjectHolder("globalrpg:titan")
    public static titan titan;

    @ObjectHolder("globalrpg:separator")
    public static separator separator;

    @ObjectHolder("globalrpg:separator")
    public static TileEntityType<Tile> SEPARATOR_TILE;
}
