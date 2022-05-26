package com.udemy.cursoandroid.gestaogados.Model.Farm;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private int id;
    private String name;
    private String location;
    private List<Loot> farmLoots;

    public Farm()
    {
        this.name = "";
        this.location = "";
        this.farmLoots = null;
    }

    public Farm(String name, String location) {
        this.name = name;
        this.location = location;
        this.farmLoots = new ArrayList<>();
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

    public void setLocation(String location) {
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    public boolean addLoot(Loot loot)
    {
        return farmLoots.add(loot);
    }

    public List<String> getFarmLootsNames() {

        List<String> listNameLoots = new ArrayList<>();

        for (int i = 0; i < farmLoots.size(); i++)
        {
            listNameLoots.add(farmLoots.get(i).getName());
        }

        return listNameLoots;
    }

    public int getLootsQuantity()
    {
        return farmLoots.size();
    }

    public List<Loot> getFarmLoots() {
        return farmLoots;
    }
}
