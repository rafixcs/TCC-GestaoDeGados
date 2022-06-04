package com.udemy.cursoandroid.gestaogados.Model.Farm;

import java.util.ArrayList;
import java.util.List;

public class LootCollection
{
    private List<Loot> mLootCollection;

    public LootCollection()
    {
        this.mLootCollection = new ArrayList<>();
    }

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

    public void add(Loot loot)
    {
        mLootCollection.add(loot);
    }

    public Loot get(int i)
    {
        return mLootCollection.get(i);
    }

    public int idToCollectionIndex(int id)
    {
        for(int i=0; i<mLootCollection.size(); i++)
        {
            if (mLootCollection.get(i).getId() == id)
            {
                return i;
            }
        }

        return -1;
    }

    public List<Integer> getIdList()
    {
        List<Integer> ids = new ArrayList<>();

        for (int i = 0; i< mLootCollection.size(); i++)
        {
            ids.add(mLootCollection.get(i).getId());
        }

        return ids;
    }
}
