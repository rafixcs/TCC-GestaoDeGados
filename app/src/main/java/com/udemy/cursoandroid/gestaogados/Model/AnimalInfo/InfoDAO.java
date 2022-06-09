package com.udemy.cursoandroid.gestaogados.Model.AnimalInfo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.udemy.cursoandroid.gestaogados.Controller.animals.info.IAnimalInfoController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.register.IRegisterAnimalController;

import java.util.ArrayList;
import java.util.List;

public class InfoDAO implements IInfoDAO
{
    private SQLiteDatabase database;
    private IAnimalInfoController animalInfoController;
    private IRegisterAnimalController registerAnimalController;

    public InfoDAO(SQLiteDatabase database, IAnimalInfoController animalInfoController) {
        this.database = database;
        this.animalInfoController = animalInfoController;
    }

    public InfoDAO(SQLiteDatabase database, IRegisterAnimalController registerAnimalController)
    {
        this.database = database;
        this.registerAnimalController = registerAnimalController;
    }

    @Override
    public void save(IInfoCommon info)
    {

        if (database == null)
        {
            Log.e("Database", "Insertion database or controller is null");
            return;
        }

        if (database.isOpen() && !database.isReadOnly() && info != null)
        {
            if(!checkIfInfoExists(info))
            {
                try
                {
                    ContentValues cv = new ContentValues();
                    cv.put(info.getIdColumnName(), info.getId());
                    cv.put(info.getNameTypeColumnName(), info.getNameType());
                    database.insert(info.getTypeEnum().getTableName(), null, cv);
                    Log.d("DatabaseInsert", "Created a new info: " + info.getNameType());
                    animalInfoController.result(true);
                    return;
                }
                catch (Exception e)
                {
                    Log.e("DatabaseInsert", "Failed to create a new info: " + info.getNameType());
                }
            }
            else
            {
                Log.e("DatabaseInsert", "Info: " + info.getNameType() + " already registered");
            }
        }

        animalInfoController.result(false);
    }

    @Override
    public List<IInfoCommon> getInfoTable(InfoTypeEnum infoType)
    {
        String tableName = infoType.getTableName();
        IInfoCommon info = infoType.getInfoObject();

        return getInfoList(info, tableName);
    }

    @Override
    public void delete(IInfoCommon info)
    {
        try
        {
            String args[] = { Integer.toString(info.getId()) };
            database.delete(
                    info.getTypeEnum().getTableName(),
                    info.getIdColumnName() + "=?",
                    args);

            Log.i("DatabaseDelete", "AnimalInfo deleted");
        }
        catch (Exception e)
        {
            Log.e("DatabaseDelete", "Failed to delete AnimalInfo");
        }
    }

    @Override
    public IInfoCommon getById(InfoTypeEnum infoTypeEnum, int id)
    {
        IInfoCommon info = infoTypeEnum.getInfoObject();

        String query = "SELECT * FROM " + infoTypeEnum.getTableName() +
                " AS info WHERE " + info.getIdColumnName() + "=?";

        String[] args = new String[]{Integer.toString(id)};

        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex(info.getIdColumnName());
        int nameTypeIndex = cursor.getColumnIndex(info.getNameTypeColumnName());

        info.setId(cursor.getInt(idIndex));
        info.setNameType(cursor.getString(nameTypeIndex));

        return info;
    }

    private List<IInfoCommon> getInfoList(IInfoCommon info, String table)
    {
        String query = "SELECT * FROM " + table;

        String[] args = new String[]{};

        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex(info.getIdColumnName());
        int nameTypeIndex = cursor.getColumnIndex(info.getNameTypeColumnName());

        List<IInfoCommon> infoList = new ArrayList<>();

        if (cursor.getCount() > 0)
        {
            do {
                IInfoCommon tempInfo = info.getTypeEnum().getInfoObject();
                tempInfo.setId(cursor.getInt(idIndex));
                tempInfo.setNameType(cursor.getString(nameTypeIndex));
                infoList.add(tempInfo);
            } while (cursor.moveToNext());
        }

        return infoList;
    }

    private boolean checkIfInfoExists(IInfoCommon info)
    {
        String query = "SELECT * FROM " +
                info.getTypeEnum().getTableName() +
                " WHERE " + info.getNameTypeColumnName() +"=?";

        String[] args = new String[]{};

        Cursor cursor= database.rawQuery(query, args);

        if (cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }
}
