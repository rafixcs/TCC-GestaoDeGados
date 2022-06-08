package com.udemy.cursoandroid.gestaogados.Model.AnimalRegister;

public enum AnimalRegisterStatusEnum
{
    ACTIVE("ACTIVE"), 
    INACTIVE("INACTIVE");

    private final String name;

    AnimalRegisterStatusEnum(String inactive)
    {
        name = inactive;
    }

    public String toString()
    {
        return name;
    }

    static public AnimalRegisterStatusEnum fromString(String status)
    {
        if (status.equals("ACTIVE"))
        {
            return ACTIVE;
        }
        else if (status.equals("INACTIVE"))
        {
            return INACTIVE;
        }

        return null;
    }
}
