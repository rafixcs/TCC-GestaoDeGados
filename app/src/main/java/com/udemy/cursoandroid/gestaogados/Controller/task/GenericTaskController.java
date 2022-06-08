package com.udemy.cursoandroid.gestaogados.Controller.task;

import android.content.Context;

import com.udemy.cursoandroid.gestaogados.Model.Database.DatabaseAccess;
import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.GenericTask;
import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.GenericTaskDAO;
import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.IGenericTaskDAO;
import com.udemy.cursoandroid.gestaogados.View.task.ITaskView;

import java.util.List;

public class GenericTaskController implements IGenericTaskController
{

    private DatabaseAccess databaseAccess;
    private ITaskView taskView;
    private IGenericTaskDAO mGenericTaskDAO;

    public GenericTaskController(ITaskView taskView, Context context)
    {
        this.taskView = taskView;
        databaseAccess = DatabaseAccess.getInstance(context);
        mGenericTaskDAO = new GenericTaskDAO(databaseAccess.getDb(), this);
    }

    @Override
    public void saveGenericTask(String name, String description, String linkId, boolean isFarm)
    {
        GenericTask genericTask = new GenericTask(name, description);

        IGenericTaskDAO genericTaskDAO = new GenericTaskDAO(databaseAccess.getDb(), this);
        genericTaskDAO.save(genericTask, linkId, isFarm);

    }

    @Override
    public List<GenericTask> getTaskByRelation(String linkId, boolean isFarm)
    {
        if (isFarm)
        {
            return mGenericTaskDAO.getFarmTasks(linkId);
        }
        else
        {
            return  mGenericTaskDAO.getAnimalTasks(linkId);
        }
    }

    @Override
    public GenericTask getTask(int id)
    {
        return mGenericTaskDAO.get(id);
    }

    @Override
    public void updateTask(GenericTask genericTask)
    {
        mGenericTaskDAO.update(genericTask);
    }

    @Override
    public void setSavedTaskResult(boolean result)
    {
        taskView.setSaveResult(result);
    }
}
