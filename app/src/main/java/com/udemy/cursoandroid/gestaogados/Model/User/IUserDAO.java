package com.udemy.cursoandroid.gestaogados.Model.User;

public interface IUserDAO
{
    public boolean save(User user);
    public User getAccount(String email, String password);
}
