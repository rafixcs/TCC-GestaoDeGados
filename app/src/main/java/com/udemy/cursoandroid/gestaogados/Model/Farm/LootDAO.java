package com.udemy.cursoandroid.gestaogados.Model.Farm;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.udemy.cursoandroid.gestaogados.Controller.MainController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;
import com.udemy.cursoandroid.gestaogados.Model.User.User;

import java.util.ArrayList;
import java.util.List;

public class LootDAO implements ILootDAO
{
    private final String LOOT_TABLE = "app_loot";
    private final String FARM_TABLE = "app_farm";
    private final String LINK_LOOT_TABLE = "app_link_farm_loot";

    private SQLiteDatabase database;
    private IFarmController controller;

    public LootDAO(SQLiteDatabase database, IFarmController controller) {
        this.database = database;
        this.controller = controller;
    }

    @Override
    public void save(Farm farm, Loot loot)
    {
        if(database.isOpen() && !database.isReadOnly() && farm != null && loot != null)
        {
            if(!checkIfLootAlreadyExists(farm, loot))
            {
                try
                {
                    saveLoot(farm, loot);
                    controller.saveResult(true);
                    return;
                }
                catch (Exception e)
                {
                    Log.e("DatabaseInsert", "Failed to create a new loot: " + loot.getName());
                }
            }
            else
            {
                Log.e("DatabaseInsert", "loot: " + loot.getName() + " already registered");
            }
        }
        else
        {
            Log.e("DatabaseInsert", "Error with database or farm/loot is null");
        }

        controller.saveResult(false);
    }

    @Override
    public LootCollection getLoots(int farmId)
    {
        String query = "SELECT * FROM " + LOOT_TABLE + " AS loot INNER JOIN "
                + LINK_LOOT_TABLE + " AS linkt ON loot.id_loot=linkt.id_loot " +
                "WHERE linkt.id_farm=?";

        String[] args = new String[]{Integer.toString(farmId)};

        Cursor cursor= database.rawQuery(query, args);

        cursor.moveToFirst();
        int idIndex = cursor.getColumnIndex("id_loot");
        int nameIndex = cursor.getColumnIndex("name");

        List<Loot> lootList = new ArrayList<>();
        while (cursor.moveToNext())
        {
            Loot loot = new Loot();
            loot.setId(cursor.getInt(idIndex));
            loot.setName(cursor.getString(nameIndex));
        }

        return new LootCollection(lootList);
    }

    @Override
    public void delete(Loot loot)
    {
        try
        {
            String args[] = { Integer.toString(loot.getId()) };
            database.delete(LOOT_TABLE, "id_loot=?", args);

            Log.i("DatabaseDelete", "Loot deleted");
        }
        catch (Exception e)
        {
            Log.e("DatabaseDelete", "Failed to delete loot");
        }
    }

    @Override
    public int getTotalLootsQuantity(FarmCollection farmCollection) {

        List<Farm> farmList = farmCollection.getFarmCollection();

        int totalQuantity = 0;

        for (Farm farm:farmList)
        {
            totalQuantity += this.getLoots(farm.getId()).size();
        }

        return totalQuantity;
    }

    private void saveLoot(Farm farm, Loot loot)
    {
        ContentValues cv = new ContentValues();

        cv.put("id_loot", loot.getId());
        cv.put("name", farm.getName());

        database.insert(LOOT_TABLE, null, cv);

        Log.i("DatabaseInsert", "Created new loot: " + loot.getName());


        createFarmLootLink(farm, loot);
    }

    private void createFarmLootLink(Farm farm, Loot loot)
    {
        try
        {
            ContentValues cv = new ContentValues();

            cv.put("id_farm", farm.getId());
            cv.put("id_loot", loot.getId());

            database.insert(LINK_LOOT_TABLE, null, cv);
            Log.i("DatabaseInsert", "Created producer-farm link");
        }
        catch (Exception e)
        {
            Log.e("DatabaseInsert", "Failed to create producer-farm link");
            delete(loot);
        }

    }

    public boolean checkIfLootAlreadyExists(Farm farm, Loot loot)
    {
        String query = "SELECT * FROM " + LOOT_TABLE + " AS loot INNER JOIN "
                + LINK_LOOT_TABLE + " AS linkt ON loot.id_loot=linkt.id_loot " +
                "WHERE linkt.id_farm=? AND loot.name=?";

        String[] args = new String[]{Integer.toString(farm.getId()), loot.getName()};

        Cursor cursor= database.rawQuery(query, args);

        if (cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }
}
