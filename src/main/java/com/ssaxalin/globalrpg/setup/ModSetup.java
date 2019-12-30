package com.ssaxalin.globalrpg.setup;

import com.ssaxalin.globalrpg.blocks.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class ModSetup {
    public ItemGroup itemGroup = new ItemGroup("globalrpg") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModBlocks.Magnetite);
        }
    };
public static void init(){}
}
