package com.udemy.cursoandroid.gestaogados.Controller.farm;

import android.content.Context;
import android.util.Log;

import com.udemy.cursoandroid.gestaogados.Controller.MainController;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmCollection;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmDAO;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmLootMockRegister;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmsMockRecord;
import com.udemy.cursoandroid.gestaogados.Model.Farm.IFarmDAO;
import com.udemy.cursoandroid.gestaogados.Model.Farm.ILootDAO;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;
import com.udemy.cursoandroid.gestaogados.Model.Farm.LootCollection;
import com.udemy.cursoandroid.gestaogados.Model.Farm.LootDAO;
import com.udemy.cursoandroid.gestaogados.View.ICommonView;
import com.udemy.cursoandroid.gestaogados.View.farm.IRegisterFarmView;
import com.udemy.cursoandroid.gestaogados.View.main.register.IRegisterBovineView;

import java.util.ArrayList;
import java.util.List;

public class FarmController implements IFarmController{

    ICommonView mView;
    DatabaseAccess databaseAccess;
    IFarmDAO farmDAO;

    public FarmController(ICommonView mView, Context context) {
        this.mView = mView;
        databaseAccess =  DatabaseAccess.getInstance(context);
        farmDAO = new FarmDAO(databaseAccess.getDb(), this);
    }

    @Override
    public void saveNewFarm(Farm farm)
    {
        farmDAO.save(farm);
    }

    @Override
    public void saveNewLootFarm(Farm farm, Loot loot) {
        LootDAO lootDao = new LootDAO(databaseAccess.getDb(), this);
        lootDao.save(farm, loot);
    }

    @Override
    public void saveNewLootFarm(Farm farm, List<Loot> lootList) {
        for (int i=0; i<lootList.size(); i++)
        {
            saveNewLootFarm(farm, lootList.get(i));
        }
    }

    @Override
    public void saveResult(boolean result)
    {
        mView.setSaveResult(result);
    }

    @Override
    public int getLootsTotalQuantity(FarmCollection farmCollection) {

        LootDAO lootDao = new LootDAO(databaseAccess.getDb(), this);
        int total = lootDao.getTotalLootsQuantity(farmCollection);

        return total;
    }


    @Override
    public LootCollection getFarmsLoots(int id)
    {
        ILootDAO lootDao = new LootDAO(databaseAccess.getDb(), this);
        return lootDao.getLoots(id);
    }

    @Override
    public List<Farm> getFarms() {
        MainController mainController = MainController.getInstance();
        List<Farm> farmList = new ArrayList<>();
        try
        {
            farmList = farmDAO.getFarms(mainController.getCurrentUser());
        }
        catch (Exception e)
        {
            Log.e("Database", "Failed to query getFarms");
        }


        return farmList;
    }

    @Override
    public Farm getFarmByName(String name) {
        Farm farm = farmDAO.getFarmByName(name);

        return farm;
    }
}
