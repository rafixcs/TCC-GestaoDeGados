package com.udemy.cursoandroid.gestaogados.Controller.task;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmCollection;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;
import com.udemy.cursoandroid.gestaogados.View.task.VaccineTaskView;

import java.util.List;

public interface IVaccineTaskController extends ITaskCommonController
{
    public void saveVaccineTask(AnimalRegister animalRegister, VaccineTask vaccineTask);
    public VaccineTask getVaccineRegister(int id);
    public List<VaccineTask> getAnimalVaccines(String animalId);
    public List<VaccineTask> getLatestVaccines(FarmCollection farmCollection, int maxItemsQuantity);
    public void updateVaccine(VaccineTask vaccineTask);
}
