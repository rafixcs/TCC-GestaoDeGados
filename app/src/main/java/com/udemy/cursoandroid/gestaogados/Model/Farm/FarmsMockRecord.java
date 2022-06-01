package com.udemy.cursoandroid.gestaogados.Model.Farm;

import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;

import java.util.ArrayList;
import java.util.List;

public class FarmsMockRecord {

    private List<Farm> listFarms;
    private IFarmController controller;

    static private FarmsMockRecord sInstance;

    private FarmsMockRecord() {
        listFarms = new ArrayList<>();
    }

    static public FarmsMockRecord getInstance(IFarmController controller)
    {
        if (sInstance == null)
        {
            sInstance = new FarmsMockRecord();
        }

        sInstance.setController(controller);
        return sInstance;
    }

    public void setController(IFarmController controller) {
        this.controller = controller;
    }

    public void addFarm(Farm farm)
    {
        boolean result = listFarms.add(farm);
        controller.saveResult(result);
    }

    public List<String> getFarmsNames() {

        List<String> names = new ArrayList<>();
        for (int i=0; i<listFarms.size(); i++)
        {
            names.add(listFarms.get(i).getName());
        }

        return names;
    }

    public List<String> getFarmLootsForView(int index) {
        return null; //listFarms.get(index).getFarmLootsNames();
    }

    public int getLootsTotalQuantity()
    {

        int total = 0;
        for (int i=0; i<listFarms.size(); i++)
        {
            total += listFarms.get(i).getLootsQuantity();
        }

        return total;
    }

    public void saveNewFarmLoot(int id, Loot loot)
    {
        Farm farm = this.getFarmFromId(id);
        //boolean result = farm.addLoot(loot);

        //controller.saveResult(result);
    }

    public int generateNextId() {

        int id;
        int size = listFarms.size();

        if (size > 0)
        {
            id = listFarms.get(size-1).getId();
        }
        else
        {
            return 0;
        }

        return id + 1;
    }

    public Farm getFarmFromId(int id)
    {
        Farm farm = null;
        for (int i=0; i < listFarms.size(); i++)
        {

            if (listFarms.get(i).getId() == id)
            {
                farm = listFarms.get(i);
                break;
            }
        }

        return farm;
    }

    public Farm getFarmFromName(String name)
    {
        Farm farm = null;
        for (int i=0; i < listFarms.size(); i++)
        {

            if (listFarms.get(i).getName().equals(name))
            {
                farm = listFarms.get(i);
                break;
            }
        }

        return farm;
    }

    public List<Loot> getFarmsLoots(int id)
    {
        List<Loot> lootList = null;
        Farm farm = this.getFarmFromId(id);
        if (farm != null)
        {
            //lootList = farm.getFarmLoots();
        }
        return lootList;
    }
}
