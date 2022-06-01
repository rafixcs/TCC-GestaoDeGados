package com.udemy.cursoandroid.gestaogados.Model.Database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseAccess
{
    private SQLiteOpenHelper openHelper;
    private SQLiteDatabase db;
    private static DatabaseAccess instance;

    private Context context;

    private DatabaseAccess(Context context) {
        this.context = context;
        this.openHelper = new DBMain(context);
        this.open();
    }

    public static DatabaseAccess getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseAccess(context);
        }

        return instance;
    }

    public void open()
    {
        open(true);
    }

    public void open(boolean writerMode)
    {
        if (writerMode)
        {
            db = openHelper.getWritableDatabase();
        }
        else
        {
            db = openHelper.getReadableDatabase();
        }

    }

    public void close() {
        if (db != null) {
            db.close();
        }
    }

    public SQLiteDatabase getDb()
    {
        return db;
    }
}
