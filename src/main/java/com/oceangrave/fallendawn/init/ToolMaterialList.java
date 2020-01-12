package com.oceangrave.fallendawn.init;

import net.minecraft.item.IItemTier;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

public enum ToolMaterialList  implements IItemTier {

    titanmaterialaxe(10.0F,10.0F,1600,3,30, ModItems.titan_axe),
    titanmaterialsword(5.0F,5.0F,1600,3,30, ModItems.titan_sword);

    private float attackDamage, efficiency;
    private int durability;
    private int harvestlevel;
    private int ecnhantability;
    private Item repairMaterial;

    private ToolMaterialList(float attackDamage, float efficiency, int durability, int harvestlevel, int ecnhantability, Item repairMaterial)
    {
        this.attackDamage = attackDamage;
        this.efficiency = efficiency;
        this.durability = durability;
        this.harvestlevel =   harvestlevel;
        this.ecnhantability = ecnhantability;
        this.repairMaterial = repairMaterial;
    }

    @Override
    public int getMaxUses() {
        return this.durability;
    }

    @Override
    public float getEfficiency() {
        return this.efficiency;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getHarvestLevel() {
        return this.harvestlevel;
    }

    @Override
    public int getEnchantability() {
        return this.ecnhantability;
    }

    @Override
    public Ingredient getRepairMaterial() {
        return Ingredient.fromItems(this.repairMaterial);
    }

}
