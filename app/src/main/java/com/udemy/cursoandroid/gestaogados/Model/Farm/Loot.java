package com.udemy.cursoandroid.gestaogados.Model.Farm;

public class Loot
{
    int id;
    String name;

    public Loot(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Loot()
    {
        this.id = 0;
        this.name = "";
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
