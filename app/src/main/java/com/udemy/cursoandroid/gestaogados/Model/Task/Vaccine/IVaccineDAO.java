package com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;

public interface IVaccineDAO
{
    public void save(AnimalRegister animalRegister, VaccineTask vaccineTask);
    public void delete(int id);
}
