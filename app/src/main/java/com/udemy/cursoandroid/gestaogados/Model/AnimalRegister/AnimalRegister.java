package com.udemy.cursoandroid.gestaogados.Model.AnimalRegister;

public class AnimalRegister {
    String key;
    String name;
    String birthdate;
    int sex;
    int type;
    int race;
    int lifePhase;
    int farm;
    int loot;

    public AnimalRegister(String name, String birthdate,
                          int sex, int type, int race, int lifePhase,int farm, int loot)
    {
        this.name = name;
        this.birthdate = birthdate;
        this.sex = sex;
        this.type = type;
        this.race = race;
        this.lifePhase = lifePhase;
        this.farm = farm;
        this.loot = loot;
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

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }


    public int getRace() {
        return race;
    }

    public void setRace(int race) {
        this.race = race;
    }


    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public int getLifePhase() {
        return lifePhase;
    }

    public void setLifePhase(int lifePhase) {
        this.lifePhase = lifePhase;
    }

    public int getFarm() {
        return farm;
    }

    public void setFarm(int farm) {
        this.farm = farm;
    }

    public int getLoot() {
        return loot;
    }

    public void setLoot(int loot) {
        this.loot = loot;
    }
}
