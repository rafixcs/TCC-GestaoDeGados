package com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;

import java.util.List;

public interface IVaccineDAO
{
    public void save(AnimalRegister animalRegister, VaccineTask vaccineTask);
    public void delete(int id);
    public void update(VaccineTask vaccineTask);
    public VaccineTask get(int id);
    public List<VaccineTask> getAnimalVaccines(String id);
}
