package com.udemy.cursoandroid.gestaogados.Model.AnimalRegister;

import com.udemy.cursoandroid.gestaogados.Controller.animals.IAnimalController;

import java.util.ArrayList;
import java.util.List;

public class AnimalsRecords {
    private List<AnimalRegister> animalsList = new ArrayList<>();
    private IAnimalController animalController;

    public static AnimalsRecords sInstance;

    public static AnimalsRecords getsInstance(IAnimalController animalController)
    {
        if (sInstance == null)
        {
            sInstance = new AnimalsRecords();
        }

        sInstance.animalController = animalController;
        return sInstance;
    }


    public void addAnimalRecord(AnimalRegister animal)
    {
        if (animalsList.add(animal))
        {
            animalController.Result(animal);
        }
        else
        {
            animalController.Result(null);
        }
    }

    public void updateAnimal(AnimalRegister animal)
    {
        for (int i=0; i < animalsList.size(); i++)
        {
            if (animalsList.get(i).getKey().equals(animal.getKey()))
            {
                animalsList.set(i, animal);

            }
        }
    }

    public List<AnimalRegister> getAnimalsList()
    {
        return animalsList;
    }

    public void getAnimalRegister(String key)
    {
        AnimalRegister animalResult = null;
        for (int i=0; i < animalsList.size(); i++)
        {
            if (animalsList.get(i).getKey().equals(key))
            {
                   animalResult = animalsList.get(i);

            }
        }

        animalController.Result(animalResult);
    }

    public boolean animalRecordExists(String id)
    {
        for (int i=0; i < animalsList.size(); i++)
        {
            if (animalsList.get(i).getKey().equals(id))
            {
                return true;
            }
        }
        return false;
    }
}
