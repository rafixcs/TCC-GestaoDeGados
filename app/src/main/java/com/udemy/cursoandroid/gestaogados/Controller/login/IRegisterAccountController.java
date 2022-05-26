package com.udemy.cursoandroid.gestaogados.Controller.login;

import android.content.Context;

public interface IRegisterAccountController
{
    public void createNewAccount(Context context, String name, String email, String password);
    public void resultOnCreateAccount(boolean result);
}
