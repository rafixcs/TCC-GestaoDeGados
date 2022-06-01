package com.udemy.cursoandroid.gestaogados.Controller.animals.register;

import android.content.Context;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegisterDAO;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalsRecords;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.View.main.register.IRegisterBovineView;

public class RegisterAnimalController implements IRegisterAnimalController {

    private IRegisterBovineView registerBovineView;
    private Context context;

    public RegisterAnimalController(IRegisterBovineView registerBovineView, Context context) {
        this.registerBovineView = registerBovineView;
        this.context = context;
    }

    @Override
    public void Result(AnimalRegister animal) {
        if (animal != null)
        {
            registerBovineView.setSaveResult(true);
        }
        else
        {
            registerBovineView.setSaveResult(false);
        }
    }

    @Override
    public boolean CheckIfExists(String id)
    {
        return false;
    }

    @Override
    public void addNewRegister(AnimalRegister animal)
    {
        //AnimalsRecords records = AnimalsRecords.getsInstance(this);
        //records.addAnimalRecord(animal);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        AnimalRegisterDAO animalDAO = new AnimalRegisterDAO(databaseAccess.getDb(), this);
        animalDAO.save(animal);
    }
}
