package com.udemy.cursoandroid.gestaogados.Controller.farm;

import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmLootMockRegister;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmsMockRecord;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;
import com.udemy.cursoandroid.gestaogados.View.ICommonView;
import com.udemy.cursoandroid.gestaogados.View.farm.IRegisterFarmView;
import com.udemy.cursoandroid.gestaogados.View.main.register.IRegisterBovineView;

import java.util.List;

public class FarmController implements IFarmController{

    ICommonView mView;
    FarmsMockRecord mockRecord;

    public FarmController(ICommonView mView) {
        this.mView = mView;
        mockRecord = FarmsMockRecord.getInstance(this);
    }

    @Override
    public void saveNewFarm(Farm farm)
    {
        farm.setId(mockRecord.generateNextId());
        mockRecord.addFarm(farm);
    }

    @Override
    public void saveNewLootFarm(Farm farm, Loot loot) {
        mockRecord.saveNewFarmLoot(farm.getId(), loot);
    }

    @Override
    public void saveNewLootFarm(Farm farm, List<Loot> lootList) {
        for (int i=0; i<lootList.size(); i++)
        {
            mockRecord.saveNewFarmLoot(farm.getId(), lootList.get(i));
        }
    }

    @Override
    public void saveResult(boolean result) {
        mView.setSaveResult(result);
    }

    @Override
    public List<String> getFarmsNames() {
        return mockRecord.getFarmsNames();
    }

    @Override
    public List<String> getFarmLootsNames(int id) {

        return mockRecord.getFarmLootsForView(id);
    }
}
