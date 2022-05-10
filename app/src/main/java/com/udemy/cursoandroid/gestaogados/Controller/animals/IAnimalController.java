package com.udemy.cursoandroid.gestaogados.Controller.animals;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;

public interface IAnimalController
{
    public void Result(AnimalRegister animal);
    public boolean CheckIfExists(String id);
}
