package com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine;

import com.udemy.cursoandroid.gestaogados.Controller.task.ITaskCommonController;

import java.util.ArrayList;
import java.util.List;

public class VaccineMockRegister {

    static private VaccineMockRegister sInstance;

    private List<VaccineTask> vaccineTasks = null;
    private ITaskCommonController taskController;

    private VaccineMockRegister()
    {
        vaccineTasks = new ArrayList<>();
    }

    private void setTaskController(ITaskCommonController taskController) {
        this.taskController = taskController;
    }

    static public VaccineMockRegister getsInstance(ITaskCommonController controller)
    {
        if (sInstance == null)
        {
            sInstance = new VaccineMockRegister();
        }

        sInstance.setTaskController(controller);
        return sInstance;
    }

    public void saveVaccineTask(VaccineTask vaccineTask)
    {
        boolean result = vaccineTasks.add(vaccineTask);
        taskController.setSavedTaskResult(result);
    }


}
