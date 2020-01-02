package com.oceangrave.fallendawn.init;

import com.oceangrave.fallendawn.Main;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

import java.util.function.Supplier;

public class ModItemGroup extends ItemGroup {
    private final Supplier<ItemStack> iconSupplier;
    public static final  ItemGroup MOD_ITEM_GROUP = new ModItemGroup(Main.MODID, ()-> new ItemStack(ModItems.titan_ingot));
    public ModItemGroup(final String name, final Supplier<ItemStack> iconSupplier)
    {
        super(name);
        this.iconSupplier = iconSupplier;
    }
    @Override
    public ItemStack createIcon() {
        return iconSupplier.get();
    }

}
