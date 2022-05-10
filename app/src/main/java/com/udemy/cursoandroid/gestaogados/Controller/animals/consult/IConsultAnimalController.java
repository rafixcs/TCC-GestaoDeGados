package com.udemy.cursoandroid.gestaogados.Controller.animals.consult;

import com.udemy.cursoandroid.gestaogados.Controller.animals.IAnimalController;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;

public interface IConsultAnimalController extends IAnimalController
{
    public void ConsultAnimal(String id);
    public void updateAnimal(AnimalRegister animal);
}
