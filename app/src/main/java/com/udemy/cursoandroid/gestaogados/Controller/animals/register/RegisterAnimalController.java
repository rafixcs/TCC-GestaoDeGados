package com.udemy.cursoandroid.gestaogados.Controller.animals.register;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalsRecords;
import com.udemy.cursoandroid.gestaogados.View.main.register.IRegisterBovineView;

public class RegisterAnimalController implements IRegisterAnimalController {

    private IRegisterBovineView registerBovineView;

    public RegisterAnimalController(IRegisterBovineView registerBovineView) {
        this.registerBovineView = registerBovineView;
    }

    @Override
    public void Result(AnimalRegister animal) {
        if (animal != null)
        {
            registerBovineView.onSaveRegisterResult(true);
        }
        else
        {
            registerBovineView.onSaveRegisterResult(false);
        }
    }

    @Override
    public boolean CheckIfExists(String id) {
        return false;
    }

    @Override
    public void addNewRegister(AnimalRegister animal) {
        AnimalsRecords records = AnimalsRecords.getsInstance(this);
        records.addAnimalRecord(animal);
    }
}
