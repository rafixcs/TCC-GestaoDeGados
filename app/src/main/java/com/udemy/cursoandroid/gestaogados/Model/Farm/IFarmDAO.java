package com.udemy.cursoandroid.gestaogados.Model.Farm;

import com.udemy.cursoandroid.gestaogados.Model.User.User;

import java.util.List;

public interface IFarmDAO
{
    void save(Farm farm);
    void delete(Farm farm);
    List<Farm> getFarms(User suer);
    Farm getFarmByName(String name);
}
