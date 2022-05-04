package com.udemy.cursoandroid.gestaogados.Controller.login;

import android.app.Activity;
import android.content.Context;

import com.udemy.cursoandroid.gestaogados.Model.User.User;

public interface ILoginAccountController
{
    public void validateLogin(String email, String password);
    public void loginResult(User user);
}
