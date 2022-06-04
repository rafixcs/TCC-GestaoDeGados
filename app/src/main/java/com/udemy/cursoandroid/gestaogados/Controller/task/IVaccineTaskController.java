package com.udemy.cursoandroid.gestaogados.Controller.task;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;

public interface IVaccineTaskController extends ITaskCommonController
{
    public void saveVaccineTask(AnimalRegister animalRegister, VaccineTask vaccineTask);
    public void getVaccineRegister(String id);
}
