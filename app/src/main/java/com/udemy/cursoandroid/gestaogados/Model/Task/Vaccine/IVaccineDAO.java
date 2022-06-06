package com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmCollection;
import com.udemy.cursoandroid.gestaogados.Model.User.User;

import java.util.List;

public interface IVaccineDAO
{
    public void save(AnimalRegister animalRegister, VaccineTask vaccineTask);
    public void delete(int id);
    public void update(VaccineTask vaccineTask);
    public VaccineTask get(int id);
    public List<VaccineTask> getAnimalVaccines(String id);
    public List<VaccineTask> getLatestVaccines(FarmCollection farmCollection, int maxQuantity);

}
