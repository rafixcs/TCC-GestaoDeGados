package com.udemy.cursoandroid.gestaogados.Controller.task;

import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.GenericTask;

public interface IGenericTaskController extends ITaskCommonController
{
    public void saveGenericTask(String name, String description);
}
