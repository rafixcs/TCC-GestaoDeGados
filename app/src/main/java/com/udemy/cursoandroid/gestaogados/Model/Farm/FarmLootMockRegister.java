package com.udemy.cursoandroid.gestaogados.Model.Farm;

import java.util.ArrayList;
import java.util.List;

public class FarmLootMockRegister
{

    static private FarmLootMockRegister sInstance;

    private List<Loot> lootList;

    private FarmLootMockRegister() {
        lootList = new ArrayList<>();
    }

    static public FarmLootMockRegister getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new FarmLootMockRegister();
        }

        return sInstance;
    }

    public void addLoot(Loot lootRecord)
    {
        boolean result = lootList.add(lootRecord);
    }

    public List<Loot> getLootList()
    {
        return this.lootList;
    }
}
