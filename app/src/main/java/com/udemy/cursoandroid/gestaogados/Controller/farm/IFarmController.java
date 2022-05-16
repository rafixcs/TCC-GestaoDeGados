package com.udemy.cursoandroid.gestaogados.Controller.farm;

import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;

import java.util.List;

public interface IFarmController
{
    public void saveNewFarm(Farm farm);
    public void saveNewLootFarm(Farm farm, Loot loot);
    public void saveNewLootFarm(Farm farm, List<Loot> loot);
    public void saveResult(boolean result);
    public List<String> getFarmsNames();
    public List<String> getFarmLootsNames(int id);
}
