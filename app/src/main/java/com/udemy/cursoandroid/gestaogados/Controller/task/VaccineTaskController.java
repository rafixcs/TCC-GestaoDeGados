package com.udemy.cursoandroid.gestaogados.Controller.task;

import android.content.Context;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmCollection;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.IVaccineDAO;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineDAO;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;
import com.udemy.cursoandroid.gestaogados.View.task.ITaskView;

import java.util.List;

public class VaccineTaskController implements IVaccineTaskController
{

    private ITaskView vaccineTaskView;
    private Context context;
    private IVaccineDAO vaccineDAO;

    public VaccineTaskController(ITaskView vaccineTaskView, Context context)
    {
        this.vaccineTaskView = vaccineTaskView;
        this.context = context;
        DatabaseAccess databaseAccess = DatabaseAccess.getInstance(context);
        vaccineDAO = new VaccineDAO(databaseAccess.getDb(), this);
    }

    @Override
    public void saveVaccineTask(AnimalRegister animalRegister, VaccineTask vaccineTask)
    {
        vaccineDAO.save(animalRegister, vaccineTask);
    }

    @Override
    public VaccineTask getVaccineRegister(int id)
    {
        return vaccineDAO.get(id);
    }

    @Override
    public List<VaccineTask> getAnimalVaccines(String animalId)
    {
        return vaccineDAO.getAnimalVaccines(animalId);
    }

    @Override
    public List<VaccineTask> getLatestVaccines(FarmCollection farmCollection, int maxItemsQuantity)
    {
        return vaccineDAO.getLatestVaccines(farmCollection, maxItemsQuantity);
    }

    @Override
    public void updateVaccine(VaccineTask vaccineTask)
    {
        vaccineDAO.update(vaccineTask);
    }

    @Override
    public void setSavedTaskResult(boolean result)
    {
        vaccineTaskView.setSaveResult(result);
    }
}
