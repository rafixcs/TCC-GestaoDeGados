package com.udemy.cursoandroid.gestaogados.Controller.login;

import android.content.Context;

import com.udemy.cursoandroid.gestaogados.Controller.MainController;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.Model.User.IUserDAO;
import com.udemy.cursoandroid.gestaogados.Model.User.User;
import com.udemy.cursoandroid.gestaogados.Model.User.UserDAO;
import com.udemy.cursoandroid.gestaogados.Model.User.UsersMockRecord;
import com.udemy.cursoandroid.gestaogados.View.login.IRegisterView;

import java.util.UUID;

public class RegisterAccountController implements IRegisterAccountController
{

    IRegisterView registerView;

    public RegisterAccountController(IRegisterView registerView)
    {
        this.registerView = registerView;
    }

    @Override
    public void createNewAccount(Context context, String name, String email, String password)
    {
        User user = new User();
        user.setId(UUID.randomUUID().toString());
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        databaseAccess.open();

        IUserDAO userDAO = new UserDAO(databaseAccess.getDb(), this);
        if(userDAO.save(user))
        {
            MainController mainController = MainController.getInstance();
            mainController.setCurrentUser(user);
        }
    }

    @Override
    public void resultOnCreateAccount(boolean result)
    {
        registerView.onRegisterNewAccountResult(result);
    }
}
