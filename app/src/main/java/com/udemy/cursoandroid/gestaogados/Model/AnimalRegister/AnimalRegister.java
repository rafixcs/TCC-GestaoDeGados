package com.udemy.cursoandroid.gestaogados.Model.AnimalRegister;

public class AnimalRegister {
    String name;
    String sex;
    String type;
    String status;
    String race;
    String age;
    String birthdate;
    String key;

    public AnimalRegister(String name, String sex, String type, String status, String race, String age, String birthdate) {
        this.name = name;
        this.sex = sex;
        this.type = type;
        this.status = status;
        this.race = race;
        this.age = age;
        this.birthdate = birthdate;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }
}
