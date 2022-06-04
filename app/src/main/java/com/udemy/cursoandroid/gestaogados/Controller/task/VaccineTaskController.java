package com.udemy.cursoandroid.gestaogados.Controller.task;

import android.content.Context;
import android.provider.ContactsContract;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.IVaccineDAO;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineDAO;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineMockRegister;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;
import com.udemy.cursoandroid.gestaogados.View.task.ITaskView;

public class VaccineTaskController implements IVaccineTaskController{

    private ITaskView vaccineTaskView;
    private Context context;

    public VaccineTaskController(ITaskView vaccineTaskView, Context context) {
        this.vaccineTaskView = vaccineTaskView;
        this.context = context;
    }

    @Override
    public void saveVaccineTask(AnimalRegister animalRegister, VaccineTask vaccineTask)
    {
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        IVaccineDAO vaccineDAO = new VaccineDAO(databaseAccess.getDb(), this);
        vaccineDAO.save(animalRegister, vaccineTask);
    }

    @Override
    public void getVaccineRegister(String id)
    {

    }

    @Override
    public void setSavedTaskResult(boolean result)
    {
        vaccineTaskView.setSaveRegisterResult(result);
    }
}
