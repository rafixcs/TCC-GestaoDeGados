package com.udemy.cursoandroid.gestaogados.Model.AnimalInfo;

public interface IInfoCommon
{
    public String getNameType();
    public void setNameType(String nameType);
    public int getId();
    public void setId(int id);
    public InfoTypeEnum getTypeEnum();
    public String getNameTypeColumnName();
    public String getIdColumnName();
}
