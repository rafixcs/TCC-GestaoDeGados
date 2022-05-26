package com.udemy.cursoandroid.gestaogados.Model.Farm;

import java.util.ArrayList;
import java.util.List;

public class LootCollection
{
    private List<Loot> mLootCollection;

    public LootCollection(List<Loot> lootCollection)
    {
        this.mLootCollection = lootCollection;
    }

    public List<String> getLootsNames()
    {
        List<String> names = new ArrayList<>();

        for (int i = 0; i< mLootCollection.size(); i++)
        {
            names.add(mLootCollection.get(i).getName());
        }

        return names;
    }

    public int size()
    {
        return mLootCollection.size();
    }

    public List<Loot> getCollection()
    {
        return this.mLootCollection;
    }
}
