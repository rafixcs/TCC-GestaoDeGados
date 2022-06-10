package com.udemy.cursoandroid.gestaogados.Model.User;


import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.udemy.cursoandroid.gestaogados.Controller.login.ILoginAccountController;
import com.udemy.cursoandroid.gestaogados.Controller.login.IRegisterAccountController;

public class UserDAO implements IUserDAO
{
    private SQLiteDatabase database;
    private final String USER_TABLE = "app_producer";
    private ILoginAccountController loginController;
    private IRegisterAccountController registerAccountController;


    public UserDAO(SQLiteDatabase database, ILoginAccountController loginController) {
        this.loginController = loginController;
        this.database = database;
    }

    public UserDAO(SQLiteDatabase database, IRegisterAccountController registerAccountController) {
        this.registerAccountController = registerAccountController;
        this.database = database;
    }

    @Override
    public boolean save(User user)
    {
        if (database == null || registerAccountController == null)
        {
            Log.e("Database", "Insertion database or controller is null");
            return false;
        }

        if (database.isOpen() && !database.isReadOnly() && user != null)
        {
            if(!checkIfExists(user))
            {
                try
                {
                    ContentValues cv = new ContentValues();
                    cv.put("id_producer", user.getId());
                    cv.put("name", user.getName());
                    cv.put("email", user.getEmail());
                    cv.put("password", user.getPassword());
                    database.insert(USER_TABLE, null, cv);
                    Log.d("DatabaseInsert", "Created a new user: " + user.getEmail());
                    registerAccountController.resultOnCreateAccount(true);
                    return true;
                }
                catch (Exception e)
                {
                    Log.e("DatabaseInsert", "Failed to create a new user: " + user.getEmail());
                }
            }
            else
            {
                Log.e("DatabaseInsert", "Email: " + user.getEmail() + " already registered");
            }
        }

        registerAccountController.resultOnCreateAccount(false);
        return false;
    }

    @Override
    public User getAccount(String email, String password)
    {
        if (database == null || loginController == null)
        {
            Log.e("Database", "Insertion database or controller is null");
            return null;
        }

        if (database.isOpen())
        {
            String query = "SELECT * FROM " + USER_TABLE +" WHERE email=? AND password=?";
            String[] args = new String[]{email, password};

            Cursor cursor = database.rawQuery(query, args);

            cursor.moveToFirst();

            int idIndex = cursor.getColumnIndex("id_producer");
            int nameIndex = cursor.getColumnIndex("name");
            int emailIndex = cursor.getColumnIndex("email");
            int passwordIndex = cursor.getColumnIndex("password");

            try
            {
                User user = new User();
                user.setId(cursor.getString(idIndex));
                user.setName(cursor.getString(nameIndex));
                user.setEmail(cursor.getString(emailIndex));
                user.setPassword(cursor.getString(passwordIndex));
                loginController.loginResult(user);

                return user;
            }
            catch (Exception e)
            {
                loginController.loginResult(null);
                return null;
            }
        }

        loginController.loginResult(null);
        return null;
    }

    private boolean checkIfExists(User user)
    {
        String query = "SELECT * FROM " + USER_TABLE + " WHERE email=?";

        String[] args = new String[]{user.getEmail()};

        Cursor cursor= database.rawQuery(query, args);

        if (cursor.getCount() > 0)
        {
            return true;
        }

        return false;
    }
}
