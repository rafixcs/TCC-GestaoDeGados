package com.udemy.cursoandroid.gestaogados.Controller.login;

import android.content.Context;

import com.udemy.cursoandroid.gestaogados.Controller.MainController;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.Model.User.IUserDAO;
import com.udemy.cursoandroid.gestaogados.Model.User.User;
import com.udemy.cursoandroid.gestaogados.Model.User.UserDAO;
import com.udemy.cursoandroid.gestaogados.Model.User.UsersMockRecord;
import com.udemy.cursoandroid.gestaogados.View.login.ILoginView;

//TODO: refactoring needed when database is implemented

public class LoginAccountController implements ILoginAccountController
{

    private ILoginView loginView;
    private Context context;

    public LoginAccountController(Context context, ILoginView loginView)
    {
        this.context = context;
        this.loginView = loginView;
    }

    @Override
    public void validateLogin(String email, String password)
    {

        /**UsersMockRecord mockRecord = UsersMockRecord.getInstance(this);
        mockRecord.loginUser(email, password);*/

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        IUserDAO userDAO = new UserDAO(databaseAccess.getDb(), this);
        userDAO.getAccount(email, password);

        //databaseAccess.close();


    }

    @Override
    public void loginResult(User user)
    {
        if (user != null)
        {
            MainController mainController = MainController.getInstance();
            mainController.setCurrentUser(user);
        }
        loginView.onLoginAccount();
    }
}
