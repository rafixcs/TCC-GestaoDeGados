package com.udemy.cursoandroid.gestaogados.Model.AnimalInfo;

public enum InfoTypeEnum
{
    RACE_TYPE, SEX_TYPE, ANIMAL_TYPE, LIFE_PHASE;

    public static final String SEX_TYPE_TABLE = "app_animal_sex_type";
    public static final String ANIMAL_TYPE_TABLE = "app_animal_type";
    public static final String LIFE_PHASE_TABLE = "app_animal_life_phase";
    public static final String RACE_TABLE = "app_animal_race";


    public String getTableName()
    {
        switch (this)
        {
            case SEX_TYPE:
                return SEX_TYPE_TABLE;
            case ANIMAL_TYPE:
                return ANIMAL_TYPE_TABLE;
            case LIFE_PHASE:
                return LIFE_PHASE_TABLE;
            case RACE_TYPE:
                return RACE_TABLE;
            default:
                return "";
        }
    }

    public IInfoCommon getInfoObject()
    {
        switch (this)
        {
            case SEX_TYPE:
                return new SexType();
            case ANIMAL_TYPE:
                return new AnimalType();
            case LIFE_PHASE:
                return new LifePhase();
            case RACE_TYPE:
                return new Race();
            default:
                return null;
        }
    }
}
