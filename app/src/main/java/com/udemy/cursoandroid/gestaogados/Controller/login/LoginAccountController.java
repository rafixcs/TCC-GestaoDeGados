package com.udemy.cursoandroid.gestaogados.Controller.login;

import com.udemy.cursoandroid.gestaogados.Model.User.User;
import com.udemy.cursoandroid.gestaogados.Model.User.UsersMockRecord;
import com.udemy.cursoandroid.gestaogados.View.login.ILoginView;

//TODO: refactoring needed when database is implemented

public class LoginAccountController implements ILoginAccountController
{

    ILoginView loginView;

    public LoginAccountController(ILoginView loginView)
    {
        this.loginView = loginView;
    }

    @Override
    public void validateLogin(String email, String password)
    {

        UsersMockRecord mockRecord = UsersMockRecord.getInstance(this);
        mockRecord.loginUser(email, password);
    }

    @Override
    public void loginResult(User user)
    {
        loginView.setUser(user);
        loginView.onLoginAccount();
    }
}
