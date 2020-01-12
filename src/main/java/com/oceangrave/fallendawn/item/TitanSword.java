package com.oceangrave.fallendawn.item;

import net.minecraft.item.IItemTier;
import net.minecraft.item.SwordItem;

public class TitanSword extends SwordItem {
    public TitanSword(IItemTier tier, int damage, int attackDamage, float attackSpeedIn, Properties builder) {
        super(tier, attackDamage, attackSpeedIn, builder);
    }
}
