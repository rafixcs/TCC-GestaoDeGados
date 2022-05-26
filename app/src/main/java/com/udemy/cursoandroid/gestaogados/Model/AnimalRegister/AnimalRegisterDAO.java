package com.udemy.cursoandroid.gestaogados.Model.AnimalRegister;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.udemy.cursoandroid.gestaogados.Controller.animals.IAnimalController;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;

public class AnimalRegisterDAO implements IAnimalRegisterDAO
{
    private final String LOOT_TABLE = "app_loot";
    private final String FARM_TABLE = "app_farm";
    private final String ANIMAL_TABLE = "app_animals";
    private final String LINK_LOOT_TABLE = "app_link_farm_loot";
    private final String LINK_FARM_LOOT_TABLE = "app_link_farm_loot";
    private final String LINK_ANIMAL_TABLE = "app_link_loot_animals";

    private SQLiteDatabase database;
    private IAnimalController controller;

    public AnimalRegisterDAO(SQLiteDatabase database, IAnimalController animalController)
    {
        this.database = database;
        this.controller = animalController;
    }

    @Override
    public void save(AnimalRegister animal, Farm farm, Loot loot)
    {
        if(database.isOpen() && !database.isReadOnly() && farm != null && loot != null && animal != null)
        {
            if(!checkIfAnimalAlreadyExists(animal, farm, loot))
            {
                try
                {
                    saveAnimal(animal, farm, loot);
                    //controller.saveResult(true);
                    return;
                }
                catch (Exception e)
                {
                    Log.e("DatabaseInsert", "Failed to create a new animal: " + animal.getName());
                }
            }
            else
            {
                Log.e("DatabaseInsert", "animal: " + animal.getName() + " already registered");
            }
        }
        else
        {
            Log.e("DatabaseInsert", "Error with database or farm/loot/animal is null");
        }

        controller.Result(null);
    }

    @Override
    public AnimalRegister get(String id) {

        String query = "SELECT * FROM " + ANIMAL_TABLE +" WHERE id_animal=?";

        String[] args = new String[]{id};
        Cursor cursor= database.rawQuery(query, args);

        // TODO: set sex/life phase/race/type
        int idIndex = cursor.getColumnIndex("id_animal");
        int sequenceNumberIndex = cursor.getColumnIndex("sequence_number");
        int nameIndex = cursor.getColumnIndex("name");
        int statusIndex = cursor.getColumnIndex("status");
        int ageIndex = cursor.getColumnIndex("status");
        int birthDateIndex = cursor.getColumnIndex("birth_date");
        int imgSrcIndex = cursor.getColumnIndex("img_src");

        AnimalRegister animal = new AnimalRegister();
        animal.setId(cursor.getString(idIndex));
        animal.setSequenceNumber(cursor.getInt(sequenceNumberIndex));
        animal.setName(cursor.getString(nameIndex));
        animal.setStatus(cursor.getString(statusIndex));
        animal.setAge(cursor.getString(ageIndex));
        animal.setBirthdate(cursor.getString(birthDateIndex));
        animal.setImgSource(cursor.getString(imgSrcIndex));

        controller.Result(animal);
        return animal;
    }

    @Override
    public void delete(AnimalRegister animal)
    {
        try
        {
            String args[] = { animal.getId() };
            database.delete(ANIMAL_TABLE, "id_animal=?", args);

            Log.i("DatabaseDelete", "animal deleted");
        }
        catch (Exception e)
        {
            Log.e("DatabaseDelete", "Failed to delete animal");
        }
    }

    private void saveAnimal(AnimalRegister animal, Farm farm, Loot loot)
    {
        ContentValues cv = new ContentValues();

        cv.put("id_animal", animal.getId());
        cv.put("sequence_number", 0);
        cv.put("name", animal.getName());
        cv.put("status", animal.getName());
        cv.put("age", animal.getName());
        cv.put("data_nascimento", animal.getName());
        cv.put("img_src", "generic");


        database.insert(ANIMAL_TABLE, null, cv);

        Log.i("DatabaseInsert", "Created new loot: " + loot.getName());

        createLinkLootAnimal(animal, loot);
    }

    private void createLinkLootAnimal(AnimalRegister animal, Loot loot)
    {
        try
        {
            ContentValues cv = new ContentValues();

            cv.put("id_animal", animal.getId());
            cv.put("id_loot", loot.getId());

            database.insert(LINK_ANIMAL_TABLE, null, cv);
            Log.i("DatabaseInsert", "Created loot-animal link");
        }
        catch (Exception e)
        {
            Log.e("DatabaseInsert", "Failed to create loot-animal link");
            delete(animal);
        }
    }

    public boolean checkIfAnimalAlreadyExists(AnimalRegister animal, Farm farm, Loot loot)
    {
        String query = "SELECT * FROM " + ANIMAL_TABLE + " AS animal INNER JOIN "
                + LINK_ANIMAL_TABLE + " AS linkt ON animal.id_animal=linkt.id_animal " +
                "WHERE linkt.id_loot=? AND animal.name=? AND animal.name IS NOT NULL";

        String[] args = new String[]{Integer.toString(farm.getId()), loot.getName()};

        Cursor cursor= database.rawQuery(query, args);

        if (cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }
}
