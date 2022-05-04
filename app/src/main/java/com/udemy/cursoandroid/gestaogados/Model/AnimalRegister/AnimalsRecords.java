package com.udemy.cursoandroid.gestaogados.Model.AnimalRegister;

import java.util.ArrayList;
import java.util.List;

public class AnimalsRecords {
    List<AnimalRegister> animalsList = new ArrayList<>();

    public static AnimalsRecords sInstance;

    public static AnimalsRecords getsInstance()
    {
        if (sInstance == null)
        {
            sInstance = new AnimalsRecords();
        }
        return sInstance;
    }

    public void addAnimalRecord(AnimalRegister animal)
    {
        animalsList.add(animal);
    }

    public List<AnimalRegister> getAnimalsList()
    {
        return animalsList;
    }

    public AnimalRegister getAnimalRegister(String key)
    {
        for (int i=0; i < animalsList.size(); i++)
        {
            if (animalsList.get(i).getKey().equals(key))
            {
                return animalsList.get(i);
            }
        }

        return null;
    }
}
