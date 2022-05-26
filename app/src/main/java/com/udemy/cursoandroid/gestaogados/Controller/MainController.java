package com.udemy.cursoandroid.gestaogados.Controller;

import android.util.Log;

import com.udemy.cursoandroid.gestaogados.Model.User.User;

public class MainController
{
    private static MainController sInstance;
    private User currentUser;

    private MainController()
    {
        currentUser = null;
    }

    public static MainController getInstance()
    {
        if (sInstance == null)
        {
            sInstance = new MainController();
        }

        return sInstance;
    }

    public void setCurrentUser(User user)
    {
        this.currentUser = user;
    }

    public User getCurrentUser()
    {
        return currentUser;
    }
}
