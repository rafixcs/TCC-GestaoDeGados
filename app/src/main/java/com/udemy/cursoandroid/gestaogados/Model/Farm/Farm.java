package com.udemy.cursoandroid.gestaogados.Model.Farm;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private int id;
    private String name;
    private List<String> farmLoots;

    public Farm(int id, String name) {
        this.id = id;
        this.name = name;
        farmLoots = new ArrayList<>();
    }

    public Farm(int id, String name, List<String> farmLoots) {
        this.id = id;
        this.name = name;
        this.farmLoots = farmLoots;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getFarmLoots() {
        return farmLoots;
    }

    public void setFarmLoots(List<String> farmLoots) {
        this.farmLoots = farmLoots;
    }
}
