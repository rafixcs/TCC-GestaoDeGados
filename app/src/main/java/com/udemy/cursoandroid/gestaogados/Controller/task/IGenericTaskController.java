package com.udemy.cursoandroid.gestaogados.Controller.task;

import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.GenericTask;

import java.util.List;

public interface IGenericTaskController extends ITaskCommonController
{
    public void saveGenericTask(String name, String description, String linkId, boolean isFarm);
    public List<GenericTask> getTaskByRelation(String linkId, boolean isFarm);
    public GenericTask getTask(int id);
    public void updateTask(GenericTask genericTask);
}
