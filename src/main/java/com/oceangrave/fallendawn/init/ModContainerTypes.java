package com.oceangrave.fallendawn.init;

import com.oceangrave.fallendawn.Main;
import com.oceangrave.fallendawn.ModUtil;
import com.oceangrave.fallendawn.container.ModFurnaceContainer;
import net.minecraft.inventory.container.ContainerType;
import net.minecraftforge.registries.ObjectHolder;

/**
 * @author Cadiboo
 */
@ObjectHolder(Main.MODID)
public final class ModContainerTypes {

	public static final ContainerType<ModFurnaceContainer> MOD_FURNACE = ModUtil._null();
}
