package com.udemy.cursoandroid.gestaogados.Model.Farm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteAbortException;
import android.database.sqlite.SQLiteConstraintException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.udemy.cursoandroid.gestaogados.Controller.MainController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.User.User;

import java.util.ArrayList;
import java.util.List;

public class FarmDAO implements IFarmDAO
{
    private SQLiteDatabase database;
    private final String FARM_TABLE = "app_farm";
    private final String LINK_FARM_TABLE = "app_link_producer_farm";
    private IFarmController controller;

    public FarmDAO(SQLiteDatabase database, IFarmController controller)
    {
        this.database = database;
        this.controller = controller;
    }

    public FarmDAO(SQLiteDatabase database) {
        this.database = database;
    }

    @Override
    public void save(Farm farm)
    {
        if(database.isOpen() && !database.isReadOnly() && farm != null)
        {
            if(!checkIfFarmAlreadyExists(farm))
            {
                try
                {
                    farm.setId(getNextId());
                    saveFarm(farm);
                    controller.saveResult(true);
                    return;
                }
                catch (Exception e)
                {
                    Log.e("DatabaseInsert", "Failed to create a new farm: " + farm.getName());
                }
            }
            else
            {
                Log.e("DatabaseInsert", "Farm: " + farm.getName() + " already registered");
            }
        }
        else
        {
            Log.e("DatabaseInsert", "Error with database or farm is null");
        }

        controller.saveResult(false);

    }

    @Override
    public void delete(Farm farm)
    {
        try
        {
            String args[] = { Integer.toString(farm.getId()) };
            database.delete(FARM_TABLE, "id_farm=?", args);

            Log.i("DatabaseDelete", "Farm deleted");
        }
        catch (Exception e)
        {
            Log.e("DatabaseDelete", "Failed to delete farm");
        }
    }

    @Override
    public List<Farm> getFarms(User user) {

        String query = "SELECT * FROM " + LINK_FARM_TABLE +" WHERE id_producer=?";
        String[] args = new String[]{user.getId()};

        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int userIdIndex = cursor.getColumnIndex("id_producer");
        int farmIdIndex = cursor.getColumnIndex("id_farm");

        List<Farm> farmList = new ArrayList<>();
        if (cursor.getCount() > 0)
        {
            do
            {
                int farmId = cursor.getInt(farmIdIndex);
                farmList.add(queryFarmFromId(farmId));
            } while (cursor.moveToNext());
        }

        return farmList;
    }

    @Override
    public Farm getFarmByName(String name) {

        String query = "SELECT * FROM " + FARM_TABLE +" WHERE name=? AND name IS NOT NULL";

        String[] args = new String[]{name};
        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex("id_farm");
        int nameIndex = cursor.getColumnIndex("name");
        int locationIndex = cursor.getColumnIndex("location");

        Farm farm = new Farm();
        farm.setId(cursor.getInt(idIndex));
        farm.setName(cursor.getString(nameIndex));
        farm.setLocation(cursor.getString(locationIndex));

        return farm;
    }

    @Override
    public Farm getFarmById(int id) {
        String query = "SELECT * FROM " + FARM_TABLE +" WHERE name=?";

        String[] args = new String[]{Integer.toString(id)};
        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex("id_farm");
        int nameIndex = cursor.getColumnIndex("name");
        int locationIndex = cursor.getColumnIndex("location");

        Farm farm = new Farm();
        farm.setId(cursor.getInt(idIndex));
        farm.setName(cursor.getString(nameIndex));
        farm.setLocation(cursor.getString(locationIndex));

        return farm;
    }

    private int getNextId()
    {
        String query = "SELECT * FROM " + FARM_TABLE + " ORDER BY id_farm DESC LIMIT 1";
        String[] args = new String[]{};
        int value = 0;

        try
        {
            Cursor cursor = database.rawQuery(query, new String[]{});
            cursor.moveToFirst();

            do
            {
                //Log.i("FarmQuery", Integer.toString(cursor.getInt(0)) + "|" + cursor.getString(1) + "|" + cursor.getString(2));
                value = cursor.getInt(0);

            } while (cursor.moveToNext());


        }
        catch (Exception e)
        {
            return 0;
        }

        return value + 1;
    }

    public boolean checkIfFarmAlreadyExists(Farm farm)
    {
        String query = "SELECT * FROM " + FARM_TABLE +" WHERE name=?";

        String[] args = new String[]{farm.getName()};

        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        if (cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }

    private void saveFarm(Farm farm)
    {
        ContentValues cv = new ContentValues();

        cv.put("id_farm", farm.getId());
        cv.put("name", farm.getName());
        cv.put("location", farm.getLocation());

        try
        {
            database.insert(FARM_TABLE, null, cv);
            Log.i("DatabaseInsert", "Created new farm: " + farm.getName());
            createFarmLink(farm);
        }
        catch (SQLiteAbortException e)
        {
            Log.e("DatabaseInsert", "Failed to create new farm: " + farm.getName());
            throw e;
        }
        catch (SQLiteConstraintException e)
        {
            Log.e("DatabaseInsert", "Failed to create new farm: " + farm.getName());
            throw e;
        }
    }

    private void createFarmLink(Farm farm)
    {
        MainController mainController = MainController.getInstance();
        User user = mainController.getCurrentUser();

        try
        {
            ContentValues cv = new ContentValues();

            cv.put("id_producer", user.getId());
            cv.put("id_farm", farm.getId());

            database.insert(LINK_FARM_TABLE, null, cv);
            Log.i("DatabaseInsert", "Created producer-farm link");
        }
        catch (Exception e)
        {
            Log.e("DatabaseInsert", "Failed to create producer-farm link");
            delete(farm);
        }
    }

    private Farm queryFarmFromId(int id)
    {
        String query = "SELECT * FROM " + FARM_TABLE + " WHERE id_farm=?";
        String[] args = new String[]{Integer.toString(id)};

        Cursor cursor = database.rawQuery(query, args);
        cursor.moveToFirst();

        int nameIndex = cursor.getColumnIndex("name");
        int locationIndex = cursor.getColumnIndex("location");

        Farm farm = new Farm();
        farm.setId(id);
        farm.setName(cursor.getString(nameIndex));
        farm.setLocation(cursor.getString(locationIndex));

        return farm;
    }

}
