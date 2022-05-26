package com.udemy.cursoandroid.gestaogados.Model.Farm;

import java.util.ArrayList;
import java.util.List;

public class FarmCollection
{
    List<Farm> farmCollection;

    public FarmCollection(List<Farm> farmCollection) {
        this.farmCollection = farmCollection;
    }

    public List<String> getFarmsNames()
    {
        List<String> names = new ArrayList<>();

        for (int i=0; i<farmCollection.size(); i++)
        {
            names.add(farmCollection.get(i).getName());
        }

        return names;
    }

    public int size()
    {
        return farmCollection.size();
    }

    public Farm get(int i)
    {
        return farmCollection.get(i);
    }

    public List<Farm> getFarmCollection()
    {
        return farmCollection;
    }

}
