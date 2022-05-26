package com.udemy.cursoandroid.gestaogados.Model.Database;

import android.content.Context;

import com.readystatesoftware.sqliteasset.SQLiteAssetHelper;

public class DBMain extends SQLiteAssetHelper
{
    private final static String NAME_DB = "gestao_gados.db";


    public DBMain(Context context)
    {
        super(context, NAME_DB,null, 1);
    }
}
