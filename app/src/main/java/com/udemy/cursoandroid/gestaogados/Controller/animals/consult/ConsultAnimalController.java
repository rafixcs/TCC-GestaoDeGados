package com.udemy.cursoandroid.gestaogados.Controller.animals.consult;

import android.content.Context;
import android.provider.ContactsContract;

import com.udemy.cursoandroid.gestaogados.Controller.animals.IAnimalController;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegisterDAO;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalsRecords;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.View.main.consult.IConsultAnimalRegisterView;

public class ConsultAnimalController implements IConsultAnimalController
{

    IConsultAnimalRegisterView consultAnimalRegisterView;
    Context context;

    public ConsultAnimalController(IConsultAnimalRegisterView consultAnimalRegisterView, Context context)
    {
        this.consultAnimalRegisterView = consultAnimalRegisterView;
        this.context = context;
    }

    @Override
    public void ConsultAnimal(String id)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        AnimalRegisterDAO animalDAO = new AnimalRegisterDAO(databaseAccess.getDb(), this);

        animalDAO.get(id);
    }

    @Override
    public void updateAnimal(AnimalRegister animal)
    {

    }

    @Override
    public boolean CheckIfExists(String id)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        AnimalRegisterDAO animalDAO = new AnimalRegisterDAO(databaseAccess.getDb(), this);

        return animalDAO.checkIfAnimalExists(id);
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
