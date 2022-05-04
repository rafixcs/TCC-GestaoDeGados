package com.udemy.cursoandroid.gestaogados.Controller.login;

import com.udemy.cursoandroid.gestaogados.Model.User.User;
import com.udemy.cursoandroid.gestaogados.Model.User.UsersMockRecord;
import com.udemy.cursoandroid.gestaogados.View.login.IRegisterView;

public class RegisterAccountController implements IRegisterAccountController
{

    IRegisterView registerView;

    public RegisterAccountController(IRegisterView registerView)
    {
        this.registerView = registerView;
    }

    @Override
    public void createNewAccount(String name, String email, String password)
    {
        User user = new User();
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        UsersMockRecord mockRecord = UsersMockRecord.getInstance(this);
        mockRecord.addUser(user);
    }

    @Override
    public void resultOnCreateAccount()
    {
        registerView.onRegisterNewAccountResult(true);
    }
}
