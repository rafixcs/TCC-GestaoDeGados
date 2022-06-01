package com.udemy.cursoandroid.gestaogados.Model.AnimalRegister;

public class AnimalRegister {
    private String id;
    private String name;
    private String age;
    private String birthdate;
    private String status;
    private String imgSource;
    private int sequenceNumber;
    private int sex;
    private int type;
    private int race;
    private int lifePhase;
    private int farmId;
    private int lootId;

    public AnimalRegister(String name, String birthdate,
                          int sex, int type, int race, int lifePhase,int farm, int loot)
    {
        this.name = name;
        this.birthdate = birthdate;
        this.sex = sex;
        this.type = type;
        this.race = race;
        this.lifePhase = lifePhase;
        this.farmId = farm;
        this.lootId = loot;
    }

    public AnimalRegister()
    {
    }

    public String getId() {
        return id;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getSequenceNumber() {
        return sequenceNumber;
    }

    public void setSequenceNumber(int sequenceNumber) {
        this.sequenceNumber = sequenceNumber;
    }

    public String getImgSource() {
        return imgSource;
    }

    public void setImgSource(String imgSource) {
        this.imgSource = imgSource;
    }

    public void setId(String id) {
        this.id = id;
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

    public int getFarmId() {
        return farmId;
    }

    public void setFarmId(int farmId) {
        this.farmId = farmId;
    }

    public int getLootId() {
        return lootId;
    }

    public void setLootId(int lootId) {
        this.lootId = lootId;
    }
}
