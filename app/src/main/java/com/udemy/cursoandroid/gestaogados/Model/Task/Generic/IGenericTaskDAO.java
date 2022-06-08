package com.udemy.cursoandroid.gestaogados.Model.Task.Generic;

import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;

import java.util.List;

public interface IGenericTaskDAO
{
    public void save(GenericTask genericTask, String relationId, boolean isFarmLink);
    public void delete(int id);
    public void update(GenericTask genericTask);
    public GenericTask get(int id);
    public List<GenericTask> getAnimalTasks(String id);
    public List<GenericTask> getFarmTasks(String id);
}
