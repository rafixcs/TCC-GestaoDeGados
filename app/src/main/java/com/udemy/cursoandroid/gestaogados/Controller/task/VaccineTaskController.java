package com.udemy.cursoandroid.gestaogados.Controller.task;

import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineMockRegister;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;
import com.udemy.cursoandroid.gestaogados.View.task.ITaskView;

public class VaccineTaskController implements IVaccineTaskController{

    ITaskView vaccineTaskView;

    public VaccineTaskController(ITaskView vaccineTaskView) {
        this.vaccineTaskView = vaccineTaskView;
    }

    @Override
    public void saveVaccineTask(String vaccineName, String date, String o_description) {
        VaccineMockRegister mockRegister = VaccineMockRegister.getsInstance(this);
        VaccineTask vaccine = new VaccineTask(vaccineName, date, o_description);
        mockRegister.saveVaccineTask(vaccine);
    }

    @Override
    public void getVaccineRegister(String id) {

    }

    @Override
    public void setSavedTaskResult(boolean result) {
        vaccineTaskView.setSaveRegisterResult(result);
    }
}
