package com.udemy.cursoandroid.gestaogados.Model.AnimalInfo;

public class Race implements IInfoCommon
{
    private int id;
    private String name;

    @Override
    public String getNameType() {
        return this.name;
    }

    @Override
    public void setNameType(String nameType) {
        this.name = nameType;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public InfoTypeEnum getTypeEnum() {
        return InfoTypeEnum.RACE_TYPE;
    }

    @Override
    public String getNameTypeColumnName() {
        return "race_name";
    }

    @Override
    public String getIdColumnName() {
        return "id_race";
    }
}
