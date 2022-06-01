package com.udemy.cursoandroid.gestaogados.Model.AnimalInfo;

public class LifePhase implements IInfoCommon
{
    private int id;
    private String phaseName;

    @Override
    public String getNameType() {
        return phaseName;
    }

    @Override
    public void setNameType(String nameType) {
        this.phaseName = nameType;
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
        return InfoTypeEnum.LIFE_PHASE;
    }

    @Override
    public String getNameTypeColumnName() {
        return "phase_name";
    }

    @Override
    public String getIdColumnName() {
        return "id_life_phase";
    }
}
