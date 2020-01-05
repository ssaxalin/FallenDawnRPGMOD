package com.oceangrave.fallendawn.init;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.Maps;
import com.google.common.collect.Table;
import net.minecraft.item.ItemStack;

import java.util.Map;

public class ModFurnaceRecipes {
    private  static  final  ModFurnaceRecipes INSTANCE = new ModFurnaceRecipes();
    private final Table<ItemStack, ItemStack, ItemStack> smeltingList = HashBasedTable.<ItemStack, ItemStack, ItemStack>create();
    private final Map<ItemStack, Float> experienceList = Maps.<ItemStack, Float>newHashMap();

    public static ModFurnaceRecipes getInstance()
    {
        return INSTANCE;
    }

    private  ModFurnaceRecipes()
    {
            addModRecipe(new ItemStack(ModBlocks.titan_ore), new ItemStack(ModItems.titan_ingot), new ItemStack(ModItems.titan_ingot), 5.0F);
    }
    public void addModRecipe(ItemStack input1, ItemStack input2, ItemStack result, float experience)
    {
        if(getModResult(input1,input2) !=ItemStack.EMPTY) return;
        this.smeltingList.put(input1,input2,result);
        this.experienceList.put(result,Float.valueOf(experience));
    }
    public ItemStack getModResult(ItemStack input1, ItemStack input2)
    {
        for(Map.Entry<ItemStack, Map<ItemStack,ItemStack>> entry :this.smeltingList.columnMap().entrySet())
        {
            if(this.compareItemStacks(input1,(ItemStack)entry.getKey()))
                {
                    for(Map.Entry<ItemStack, ItemStack> ent: entry.getValue().entrySet())
                    {
                        if(this.compareItemStacks(input1,(ItemStack)entry.getKey()))
                        {
                            return (ItemStack)ent.getValue();
                        }
                    }
                }
        }
        return ItemStack.EMPTY;
    }
    private boolean compareItemStacks(ItemStack stack1, ItemStack stack2)
    {
        return  stack2.getItem() == stack1.getItem();

    }
    public  Table<ItemStack, ItemStack, ItemStack> getDualSmeltingList()
    {
        return  this.smeltingList;
    }
    public  float getModExpirience(ItemStack stack)
    {
        for (Map.Entry<ItemStack, Float> entry : this.experienceList.entrySet())
        {
            if(this.compareItemStacks(stack,(ItemStack)entry.getKey()))
            {
                return  ((Float)entry.getValue()).floatValue();
            }
        }
        return 0.0F;
    }
}
