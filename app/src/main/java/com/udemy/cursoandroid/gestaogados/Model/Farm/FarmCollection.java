package com.udemy.cursoandroid.gestaogados.Model.Farm;

import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;

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

    public int idToCollectionIndex(int id)
    {
        for(int i=0; i<farmCollection.size(); i++)
        {
            if (farmCollection.get(i).getId() == id)
            {
                return i;
            }
        }

        return -1;
    }

    public void setLootCollectionToFarm(IFarmController farmController)
    {
        for(Farm farm: farmCollection)
        {
            farm.setFarmLoots(farmController.getFarmsLoots(farm.getId()));
        }

    }

}
