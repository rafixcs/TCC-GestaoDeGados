package com.udemy.cursoandroid.gestaogados.Model.AnimalRegister;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.udemy.cursoandroid.gestaogados.Controller.MainController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.IAnimalController;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;
import com.udemy.cursoandroid.gestaogados.Model.User.User;

import java.util.ArrayList;
import java.util.List;

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
    public void save(AnimalRegister animal)
    {
        if(database.isOpen() && !database.isReadOnly() && animal != null)
        {
            if(!checkIfAnimalAlreadyExists(animal))
            {
                try
                {
                    animal.setSequenceNumber(getNextSequenceNumber(animal));
                    saveAnimal(animal);
                    controller.Result(animal);
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
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex("id_animal");
        int sequenceNumberIndex = cursor.getColumnIndex("sequence_number");
        int nameIndex = cursor.getColumnIndex("name");
        int statusIndex = cursor.getColumnIndex("status");
        int ageIndex = cursor.getColumnIndex("age");
        int birthDateIndex = cursor.getColumnIndex("birth_date");
        int imgSrcIndex = cursor.getColumnIndex("img_src");

        int fkRaceIndex = cursor.getColumnIndex("FK_id_race");
        int fkAnimalTypeIndex = cursor.getColumnIndex("FK_id_type");
        int fkLifePhaseIndex = cursor.getColumnIndex("FK_id_life_phase");
        int fkSexTypeIndex = cursor.getColumnIndex("FK_id_sex_type");

        AnimalRegister animal = new AnimalRegister();
        animal.setId(cursor.getString(idIndex));
        animal.setSequenceNumber(cursor.getInt(sequenceNumberIndex));
        animal.setName(cursor.getString(nameIndex));
        animal.setStatus(cursor.getString(statusIndex));
        animal.setAge(cursor.getString(ageIndex));
        animal.setBirthdate(cursor.getString(birthDateIndex));
        animal.setImgSource(cursor.getString(imgSrcIndex));

        animal.setType(cursor.getInt(fkAnimalTypeIndex));
        animal.setSex(cursor.getInt(fkSexTypeIndex));
        animal.setLifePhase(cursor.getInt(fkLifePhaseIndex));
        animal.setRace(cursor.getInt(fkRaceIndex));

        setLootAndFarmQuery(animal);

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

    @Override
    public boolean checkIfAnimalExists(String id)
    {
        String query = "SELECT * FROM " + ANIMAL_TABLE + " WHERE id_animal=?";

        String[] args = new String[]{id};

        Cursor cursor= database.rawQuery(query, args);

        if (cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }

    @Override
    public List<AnimalRegister> getAllAnimalsRegisters(List<Integer> idList) {
        List<AnimalRegister> animalRegisterList = new ArrayList<>();

        for(Integer id: idList)
        {

            String query = "SELECT * FROM " + ANIMAL_TABLE + " AS animal INNER JOIN "
                    + LINK_ANIMAL_TABLE + " AS linkt ON animal.id_animal=linkt.id_animal " +
                    "WHERE linkt.id_loot=?";

            String[] args = new String[]{id.toString()};
            Cursor cursor= database.rawQuery(query, args);
            cursor.moveToFirst();

            int idIndex = cursor.getColumnIndex("id_animal");
            int sequenceNumberIndex = cursor.getColumnIndex("sequence_number");
            int nameIndex = cursor.getColumnIndex("name");
            int statusIndex = cursor.getColumnIndex("status");
            int ageIndex = cursor.getColumnIndex("age");
            int birthDateIndex = cursor.getColumnIndex("birth_date");
            int imgSrcIndex = cursor.getColumnIndex("img_src");

            int fkRaceIndex = cursor.getColumnIndex("FK_id_race");
            int fkAnimalTypeIndex = cursor.getColumnIndex("FK_id_type");
            int fkLifePhaseIndex = cursor.getColumnIndex("FK_id_life_phase");
            int fkSexTypeIndex = cursor.getColumnIndex("FK_id_sex_type");

            if (cursor.getCount() > 0)
            {
                do {
                    AnimalRegister animal = new AnimalRegister();
                    animal.setId(cursor.getString(idIndex));
                    animal.setSequenceNumber(cursor.getInt(sequenceNumberIndex));
                    animal.setName(cursor.getString(nameIndex));
                    animal.setStatus(cursor.getString(statusIndex));
                    animal.setAge(cursor.getString(ageIndex));
                    animal.setBirthdate(cursor.getString(birthDateIndex));
                    animal.setImgSource(cursor.getString(imgSrcIndex));

                    animal.setType(cursor.getInt(fkAnimalTypeIndex));
                    animal.setSex(cursor.getInt(fkSexTypeIndex));
                    animal.setLifePhase(cursor.getInt(fkLifePhaseIndex));
                    animal.setRace(cursor.getInt(fkRaceIndex));

                    setLootAndFarmQuery(animal);
                    animalRegisterList.add(animal);

                } while (cursor.moveToNext());
            }
        }

        return animalRegisterList;
    }

    private void saveAnimal(AnimalRegister animal)
    {
        ContentValues cv = new ContentValues();

        cv.put("id_animal", animal.getId());
        cv.put("sequence_number", animal.getSequenceNumber());
        cv.put("name", animal.getName());
        cv.put("status", animal.getName());
        cv.put("age", animal.getAge());
        cv.put("birth_date", animal.getBirthdate());
        cv.put("img_src", "generic");
        cv.put("FK_id_race", animal.getRace());
        cv.put("FK_id_type", animal.getType());
        cv.put("FK_id_life_phase", animal.getLifePhase());
        cv.put("FK_id_sex_type", animal.getSex());

        database.insert(ANIMAL_TABLE, null, cv);

        Log.i("DatabaseInsert", "Created new animal: " + animal.getName());

        createLinkLootAnimal(animal);
    }

    private void createLinkLootAnimal(AnimalRegister animal)
    {
        try
        {
            ContentValues cv = new ContentValues();

            cv.put("id_animal", animal.getId());
            cv.put("id_loot", animal.getLootId());

            database.insert(LINK_ANIMAL_TABLE, null, cv);
            Log.i("DatabaseInsert", "Created loot-animal link");
        }
        catch (Exception e)
        {
            Log.e("DatabaseInsert", "Failed to create loot-animal link");
            delete(animal);
        }
    }

    private boolean checkIfAnimalAlreadyExists(AnimalRegister animal)
    {
        String query = "SELECT * FROM " + ANIMAL_TABLE + " AS animal INNER JOIN "
                + LINK_ANIMAL_TABLE + " AS linkt ON animal.id_animal=linkt.id_animal " +
                "WHERE linkt.id_loot=? AND animal.name=? AND animal.name IS NOT NULL";

        String[] args = new String[]{Integer.toString(animal.getLootId()), animal.getName()};

        Cursor cursor= database.rawQuery(query, args);

        if (cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }

    private void setLootAndFarmQuery(AnimalRegister animalRegister)
    {
        String query = "SELECT * FROM " + LINK_ANIMAL_TABLE + " WHERE id_animal=?";

        String[] args = new String[]{animalRegister.getId()};

        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int idLootIndex = cursor.getColumnIndex("id_loot");
        int idLoot = cursor.getInt(idLootIndex);

        query = "SELECT * FROM " + LINK_FARM_LOOT_TABLE + " AS loot WHERE id_loot=?";
        args = new String[]{Integer.toString(idLoot)};

        cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int idFarmIndex = cursor.getColumnIndex("id_farm");
        int farmId = cursor.getInt(idFarmIndex);

        animalRegister.setFarmId(farmId);
        animalRegister.setLootId(idLoot);
    }

    private int getNextSequenceNumber(AnimalRegister animalRegister)
    {
        String query = "SELECT animal.sequence_number FROM " + ANIMAL_TABLE + " AS animal INNER JOIN "
                + LINK_ANIMAL_TABLE + " AS linkt ON animal.id_animal=linkt.id_animal " +
                "WHERE linkt.id_loot IN " +
                "(SELECT lfarm.id_loot FROM " + LINK_FARM_LOOT_TABLE +
                " AS lfarm WHERE lfarm.id_farm = ?) " +
                "ORDER BY animal.sequence_number DESC LIMIT 1";

        String[] args = new String[]{Integer.toString(animalRegister.getFarmId())};

        int value = 0;

        try
        {
            Cursor cursor = database.rawQuery(query, args);
            cursor.moveToFirst();

            do
            {
                value = cursor.getInt(0);

            } while (cursor.moveToNext());


        }
        catch (Exception e)
        {
            return 0;
        }

        return value + 1;
    }
}
