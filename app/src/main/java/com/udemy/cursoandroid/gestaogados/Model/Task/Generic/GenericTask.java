package com.udemy.cursoandroid.gestaogados.Model.Task.Generic;

public class GenericTask
{
    private int id;
    private String name;
    private String description;
    private boolean isDone;

    public GenericTask(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public GenericTask() {    }

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }
}
