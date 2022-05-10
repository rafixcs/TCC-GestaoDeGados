package com.udemy.cursoandroid.gestaogados.Controller.animals.consult;

import com.udemy.cursoandroid.gestaogados.Controller.animals.IAnimalController;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalsRecords;
import com.udemy.cursoandroid.gestaogados.View.main.consult.IConsultAnimalRegisterView;

public class ConsultAnimalController implements IConsultAnimalController
{

    IConsultAnimalRegisterView consultAnimalRegisterView;
    AnimalsRecords records;

    public ConsultAnimalController(IConsultAnimalRegisterView consultAnimalRegisterView)
    {
        this.consultAnimalRegisterView = consultAnimalRegisterView;
        records = AnimalsRecords.getsInstance(this);
    }

    @Override
    public void ConsultAnimal(String id)
    {
        records.getAnimalRegister(id);
    }

    @Override
    public void updateAnimal(AnimalRegister animal) {
        records.updateAnimal(animal);
    }

    @Override
    public boolean CheckIfExists(String id)
    {
        return records.animalRecordExists(id);
    }

    @Override
    public void Result(AnimalRegister animal)
    {
        if (animal != null)
        {
            consultAnimalRegisterView.setAnimalRegister(animal);
        }
        else
        {
            consultAnimalRegisterView.onFailedConsultRegister();
        }

    }
}
