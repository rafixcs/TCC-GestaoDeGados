package com.udemy.cursoandroid.gestaogados.Model.Farm;

import java.util.List;

public interface ILootDAO
{
    public void save(Farm farm, Loot loot);
    public LootCollection getLoots(int farmId);
    public void delete(Loot loot);
    public int getTotalLootsQuantity(FarmCollection farmCollection);
}
