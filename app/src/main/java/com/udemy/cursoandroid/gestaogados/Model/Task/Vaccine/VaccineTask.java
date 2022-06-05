package com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine;

public class VaccineTask {

    private int id;
    private String name;
    private String date;
    private String oDescription;
    private boolean done;

    public VaccineTask(String name, String date, String oDescription) {
        this.name = name;
        this.date = date;
        this.oDescription = oDescription;
    }

    public VaccineTask() {
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

    public String getDescription() {
        return oDescription;
    }

    public void setDescription(String oDescription) {
        this.oDescription = oDescription;
    }

    public boolean getIsDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }
}
