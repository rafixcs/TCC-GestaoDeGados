package com.udemy.cursoandroid.gestaogados.Controller.animals.register;

import com.udemy.cursoandroid.gestaogados.Controller.animals.IAnimalController;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;

public interface IRegisterAnimalController extends IAnimalController {
    public void addNewRegister(AnimalRegister animal);
    public void updateAnimal(AnimalRegister animal);
}
