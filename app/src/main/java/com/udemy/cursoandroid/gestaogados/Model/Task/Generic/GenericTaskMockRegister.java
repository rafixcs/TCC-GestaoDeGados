package com.udemy.cursoandroid.gestaogados.Model.Task.Generic;

import com.udemy.cursoandroid.gestaogados.Controller.task.ITaskCommonController;

import java.util.ArrayList;
import java.util.List;

public class GenericTaskMockRegister
{
    static private GenericTaskMockRegister sInstance;

    private List<GenericTask> genericTaskList;
    private ITaskCommonController controller;

    private GenericTaskMockRegister()
    {
        genericTaskList = new ArrayList<>();
    }

    public void setController(ITaskCommonController controller) {
        this.controller = controller;
    }

    static public GenericTaskMockRegister getInstance(ITaskCommonController controller)
    {
        if (sInstance == null)
        {
            sInstance = new GenericTaskMockRegister();
        }

        sInstance.setController(controller);
        return sInstance;
    }


    public void saveGenericTask(GenericTask task)
    {
        boolean result = genericTaskList.add(task);
        controller.setSavedTaskResult(result);
    }
}
