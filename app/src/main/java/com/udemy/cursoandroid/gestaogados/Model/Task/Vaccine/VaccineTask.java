package com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine;

public class VaccineTask {

    private int id;
    private String name;
    private String date;
    private String oDescription;

    public VaccineTask(String name, String date, String oDescription) {
        this.name = name;
        this.date = date;
        this.oDescription = oDescription;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getoDescription() {
        return oDescription;
    }

    public void setoDescription(String oDescription) {
        this.oDescription = oDescription;
    }
}
