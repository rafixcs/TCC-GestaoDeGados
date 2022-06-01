package com.udemy.cursoandroid.gestaogados.Model.Farm;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private int id;
    private String name;
    private String location;
    private LootCollection farmLoots;

    public Farm()
    {
        this.name = "";
        this.location = "";
        this.farmLoots = null;
    }

    public Farm(String name, String location) {
        this.name = name;
        this.location = location;
        this.farmLoots = new LootCollection();
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


    public int getLootsQuantity()
    {
        return farmLoots.size();
    }

    public LootCollection getFarmLoots() {
        return farmLoots;
    }

    public void setFarmLoots(LootCollection farmLoots) {
        this.farmLoots = farmLoots;
    }
}
