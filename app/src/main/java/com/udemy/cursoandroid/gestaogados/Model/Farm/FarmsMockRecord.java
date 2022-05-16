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
        return listFarms.get(index).getFarmLootsNames();
    }

    public void saveNewFarmLoot(int id, Loot loot)
    {
        Farm farm = this.getFarmFromId(id);
        boolean result = farm.addLoot(loot);

        controller.saveResult(result);
    }

    public int generateNextId() {

        int id = listFarms.get(listFarms.size()-1).getId();

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
}
