package com.udemy.cursoandroid.gestaogados.Controller.task;

public interface IVaccineTaskController extends ITaskCommonController
{
    public void saveVaccineTask(String vaccineName, String date, String o_description);
    public void getVaccineRegister(String id);
}
