package com.udemy.cursoandroid.gestaogados.Model.User;

import com.udemy.cursoandroid.gestaogados.Controller.login.ILoginAccountController;
import com.udemy.cursoandroid.gestaogados.Controller.login.IRegisterAccountController;

import java.util.ArrayList;
import java.util.List;

public class UsersMockRecord {
    private List<User> listUsers = new ArrayList<>();
    private ILoginAccountController loginController;
    private IRegisterAccountController registerController;

    private static UsersMockRecord instance;

    public void setLoginController(ILoginAccountController loginController) {
        this.loginController = loginController;
    }

    public void setRegisterController(IRegisterAccountController registerController) {
        this.registerController = registerController;
    }

    public static UsersMockRecord getInstance(ILoginAccountController loginController) {
        if (instance == null) {
            instance = new UsersMockRecord();
        }
        instance.setLoginController(loginController);
        return instance;
    }

    public static UsersMockRecord getInstance(IRegisterAccountController registerController) {
        if (instance == null) {
            instance = new UsersMockRecord();
        }
        instance.setRegisterController(registerController);
        return instance;
    }

    public void addUser(User user) {

        //TODO: refactor adding a new user
        for (int i=0; i<this.listUsers.size(); i++)
        {
            if (this.listUsers.get(i).equals(user))
                return;
        }

        this.listUsers.add(user);
        if (this.registerController != null)
        {
            this.registerController.resultOnCreateAccount(true);
        }
    }

    public void loginUser(String email, String password) {

        if (this.listUsers.isEmpty())
            loginController.loginResult(null);;

        User userLogin = null;
        for (int i=0; i<this.listUsers.size(); i++) {
            User userTest = this.listUsers.get(i);
            if (userTest.getEmail().equals(email) && userTest.getPassword().equals(password)) {
                userLogin = userTest;
                break;
            }
        }

        if (this.loginController != null) {
            loginController.loginResult(userLogin);
        }
    }
}
