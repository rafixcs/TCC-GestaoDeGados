package com.udemy.cursoandroid.gestaogados.Model.AnimalRegister;

import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;

import java.util.List;

public interface IAnimalRegisterDAO
{
    public void save(AnimalRegister animal);
    public AnimalRegister get(String id);
    public void delete(AnimalRegister animal);
    public boolean checkIfAnimalExists(String id);
    public List<AnimalRegister> getAllAnimalsRegisters(List<Integer> listId);
}
