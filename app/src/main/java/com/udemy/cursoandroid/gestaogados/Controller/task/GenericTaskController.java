package com.udemy.cursoandroid.gestaogados.Controller.task;

import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.GenericTask;
import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.GenericTaskMockRegister;
import com.udemy.cursoandroid.gestaogados.View.task.ITaskView;

public class GenericTaskController implements IGenericTaskController
{

    private ITaskView taskView;

    public GenericTaskController(ITaskView taskView) {
        this.taskView = taskView;
    }

    @Override
    public void saveGenericTask(String name, String description) {
        GenericTask genericTask = new GenericTask(name, description);
        GenericTaskMockRegister mockRegister = GenericTaskMockRegister.getInstance(this);
        mockRegister.saveGenericTask(genericTask);
    }

    @Override
    public void setSavedTaskResult(boolean result) {
        taskView.setSaveResult(result);
    }
}
