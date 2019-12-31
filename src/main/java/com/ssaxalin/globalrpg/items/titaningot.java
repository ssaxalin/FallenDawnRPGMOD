package com.ssaxalin.globalrpg.items;

import com.ssaxalin.globalrpg.MainGlobalRpg;
import net.minecraft.item.Item;

public class titaningot extends Item {
    public titaningot()
    {
        super(new Item.Properties()
                .maxStackSize(64)
                .group(MainGlobalRpg.setup.itemGroup));
        setRegistryName("titaningot");

    }
}
