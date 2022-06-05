package com.udemy.cursoandroid.gestaogados.Model.AnimalInfo;

public class SexType implements IInfoCommon
{
    private int id;
    private String sexType;

    @Override
    public String getNameType() {
        return this.sexType;
    }

    @Override
    public void setNameType(String nameType) {
        this.sexType = nameType;
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
        return InfoTypeEnum.SEX_TYPE;
    }

    @Override
    public String getNameTypeColumnName() {
        return "sex_type";
    }

    @Override
    public String getIdColumnName() {
        return "id_sex";
    }
}
