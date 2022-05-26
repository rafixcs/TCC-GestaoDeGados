package com.udemy.cursoandroid.gestaogados.Controller.farm;

import android.content.Context;

import com.udemy.cursoandroid.gestaogados.Controller.MainController;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmCollection;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmDAO;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmLootMockRegister;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmsMockRecord;
import com.udemy.cursoandroid.gestaogados.Model.Farm.IFarmDAO;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;
import com.udemy.cursoandroid.gestaogados.Model.Farm.LootCollection;
import com.udemy.cursoandroid.gestaogados.Model.Farm.LootDAO;
import com.udemy.cursoandroid.gestaogados.View.ICommonView;
import com.udemy.cursoandroid.gestaogados.View.farm.IRegisterFarmView;
import com.udemy.cursoandroid.gestaogados.View.main.register.IRegisterBovineView;

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
        databaseAccess.open();
        farmDAO.save(farm);
        databaseAccess.close();
    }

    @Override
    public void saveNewLootFarm(Farm farm, Loot loot) {
        //mockRecord.saveNewFarmLoot(farm.getId(), loot);
    }

    @Override
    public void saveNewLootFarm(Farm farm, List<Loot> lootList) {
        for (int i=0; i<lootList.size(); i++)
        {
            //mockRecord.saveNewFarmLoot(farm.getId(), lootList.get(i));
        }
    }

    @Override
    public void saveResult(boolean result)
    {
        mView.setSaveResult(result);
    }

    @Override
    public int getLootsTotalQuantity(FarmCollection farmCollection) {
        databaseAccess.open();

        LootDAO lootDao = new LootDAO(databaseAccess.getDb(), this);
        int total = lootDao.getTotalLootsQuantity(farmCollection);

        databaseAccess.close();
        return total;
    }


    @Override
    public LootCollection getFarmsLoots(int id)
    {
        return null;
    }

    @Override
    public List<Farm> getFarms() {
        databaseAccess.open();

        MainController mainController = MainController.getInstance();
        List<Farm> farmList = farmDAO.getFarms(mainController.getCurrentUser());

        databaseAccess.close();

        return farmList;
    }

    @Override
    public Farm getFarmByName(String name) {
        databaseAccess.open();

        farmDAO.getFarmByName(name);

        databaseAccess.close();
        return null;
    }
}
