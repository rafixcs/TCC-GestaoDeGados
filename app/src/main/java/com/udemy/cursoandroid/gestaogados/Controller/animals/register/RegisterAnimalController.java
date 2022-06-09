package com.udemy.cursoandroid.gestaogados.Controller.animals.register;

import android.content.Context;

import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.IInfoCommon;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.IInfoDAO;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoDAO;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoTypeEnum;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegisterDAO;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalsRecords;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.View.main.consult.IConsultAnimalRegisterView;
import com.udemy.cursoandroid.gestaogados.View.main.register.IRegisterBovineView;

import java.util.Locale;

public class RegisterAnimalController implements IRegisterAnimalController {

    private IRegisterBovineView registerBovineView;
    private IConsultAnimalRegisterView consultAnimalRegisterView;
    private Context context;

    public RegisterAnimalController(IRegisterBovineView registerBovineView, Context context)
    {
        this.registerBovineView = registerBovineView;
        this.context = context;
    }

    public RegisterAnimalController(IConsultAnimalRegisterView consultAnimalRegisterView, Context context)
    {
        this.consultAnimalRegisterView = consultAnimalRegisterView;
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
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);

        IInfoDAO infoDAO = new InfoDAO(databaseAccess.getDb(), this);
        IInfoCommon raceInfo = infoDAO.getById(InfoTypeEnum.RACE_TYPE, animal.getRace());
        String raceName = raceInfo.getNameType();
        raceName = raceName.toLowerCase(Locale.ROOT).replaceAll("\\s","");
        animal.setImgSource(raceName);

        AnimalRegisterDAO animalDAO = new AnimalRegisterDAO(databaseAccess.getDb(), this);
        animalDAO.save(animal);
    }

    @Override
    public void updateAnimal(AnimalRegister animal)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);

        IInfoDAO infoDAO = new InfoDAO(databaseAccess.getDb(), this);
        IInfoCommon raceInfo = infoDAO.getById(InfoTypeEnum.RACE_TYPE, animal.getRace());
        String raceName = raceInfo.getNameType();
        raceName = raceName.toLowerCase(Locale.ROOT).replaceAll("\\s","");
        animal.setImgSource(raceName);

        AnimalRegisterDAO animalDAO = new AnimalRegisterDAO(databaseAccess.getDb(), this);
        animalDAO.update(animal);
    }

    @Override
    public void setSaveResult(boolean result)
    {
        if (consultAnimalRegisterView != null)
        {
            consultAnimalRegisterView.setSaveResult(result);
        }
    }
}
