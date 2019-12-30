package com.ssaxalin.globalrpg.items;

import com.ssaxalin.globalrpg.MainGlobalRpg;
import net.minecraft.item.Item;

public class magnetiteingot extends Item {
    public magnetiteingot()
    {
        super(new Item.Properties()
                .maxStackSize(64)

        .group(MainGlobalRpg.setup.itemGroup));
        setRegistryName("magnetiteingot");

    }

}
