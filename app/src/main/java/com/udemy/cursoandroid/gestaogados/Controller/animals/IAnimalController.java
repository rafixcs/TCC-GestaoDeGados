package com.udemy.cursoandroid.gestaogados.Controller.animals;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.View.ICommonView;

public interface IAnimalController extends ICommonView
{
    public void Result(AnimalRegister animal);
    public boolean CheckIfExists(String id);
}
