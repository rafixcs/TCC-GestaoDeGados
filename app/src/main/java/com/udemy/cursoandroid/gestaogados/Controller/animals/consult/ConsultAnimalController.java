package com.udemy.cursoandroid.gestaogados.Controller.animals.consult;

import android.content.Context;

import com.udemy.cursoandroid.gestaogados.Controller.MainController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.IAnimalController;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegisterDAO;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalsRecords;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.IAnimalRegisterDAO;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmDAO;
import com.udemy.cursoandroid.gestaogados.Model.Farm.IFarmDAO;
import com.udemy.cursoandroid.gestaogados.Model.Farm.ILootDAO;
import com.udemy.cursoandroid.gestaogados.Model.Farm.LootCollection;
import com.udemy.cursoandroid.gestaogados.Model.Farm.LootDAO;
import com.udemy.cursoandroid.gestaogados.Model.User.User;
import com.udemy.cursoandroid.gestaogados.View.main.consult.IConsultAnimalRegisterView;

import java.util.ArrayList;
import java.util.List;

public class ConsultAnimalController implements IConsultAnimalController
{
    private IConsultAnimalRegisterView consultAnimalRegisterView;
    private Context context;

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
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        AnimalRegisterDAO animalDAO = new AnimalRegisterDAO(databaseAccess.getDb(), this);
        animalDAO.update(animal);
    }

    @Override
    public List<AnimalRegister> getAnimalsRegisters() {

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        IAnimalRegisterDAO animalDAO = new AnimalRegisterDAO(databaseAccess.getDb(), this);

        MainController mainController = MainController.getInstance();
        User user = mainController.getCurrentUser();

        IFarmDAO farmDAO = new FarmDAO(databaseAccess.getDb());
        List<Farm> farmList = farmDAO.getFarms(user);

        if (farmList.isEmpty())
        {
            return new ArrayList<>();
        }

        ILootDAO lootDAO = new LootDAO(databaseAccess.getDb());
        List<Integer> lootIdList = new ArrayList<>();

        for(Farm farm: farmList)
        {
            LootCollection lootCollection = lootDAO.getLoots(farm.getId());
            List<Integer> listId = lootCollection.getIdList();
            for(Integer i: listId)
            {
                lootIdList.add(i);
            }
        }

        return animalDAO.getAllAnimalsRegisters(lootIdList);
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

    @Override
    public void setSaveResult(boolean result)
    {
        consultAnimalRegisterView.setSaveResult(result);
    }
}
