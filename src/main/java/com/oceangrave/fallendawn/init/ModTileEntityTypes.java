package com.oceangrave.fallendawn.init;

import com.oceangrave.fallendawn.Main;
import com.oceangrave.fallendawn.ModUtil;
import com.oceangrave.fallendawn.tileentity.ModFurnaceTileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Main.MODID)
public class ModTileEntityTypes {

    public static final TileEntityType<ModFurnaceTileEntity> MOD_FURNACE = ModUtil._null() ;
}
