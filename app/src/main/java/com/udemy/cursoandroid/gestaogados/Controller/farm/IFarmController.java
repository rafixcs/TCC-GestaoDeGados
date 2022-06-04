package com.udemy.cursoandroid.gestaogados.Controller.farm;

import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmCollection;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;
import com.udemy.cursoandroid.gestaogados.Model.Farm.LootCollection;

import java.util.List;

public interface IFarmController
{
    public void saveNewFarm(Farm farm);
    public void saveNewLootFarm(Farm farm, Loot loot);
    public void saveNewLootFarm(Farm farm, List<Loot> loot);
    public void saveResult(boolean result);
    public int getLootsTotalQuantity(FarmCollection farmCollection);
    public LootCollection getFarmsLoots(int id);
    public List<Farm> getFarms();
    public Farm getFarmByName(String name);
    public Farm getFarmById(int id);
    public Loot getLootById(int id);
}
