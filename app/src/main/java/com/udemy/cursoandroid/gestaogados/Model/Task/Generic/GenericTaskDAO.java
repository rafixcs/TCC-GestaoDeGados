package com.udemy.cursoandroid.gestaogados.Model.Task.Generic;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.udemy.cursoandroid.gestaogados.Controller.task.ITaskCommonController;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;

import java.util.ArrayList;
import java.util.List;

public class GenericTaskDAO implements IGenericTaskDAO
{
    private final String GENERIC_TASK_TABLE = "app_tasks";
    private final String LINK_TASK_ANIMAL_TABLE = "app_link_animal_tasks";
    private final String LINK_TASK_FARM_TABLE = "app_link_farm_task";

    private SQLiteDatabase database;
    private ITaskCommonController taskController;

    public GenericTaskDAO(SQLiteDatabase database, ITaskCommonController taskController)
    {
        this.database = database;
        this.taskController = taskController;
    }

    @Override
    public void save(GenericTask genericTask, String relationId, boolean isFarmLink)
    {
        if (database == null || taskController == null)
        {
            Log.e("Database", "Insertion database or controller is null");
            return;
        }

        if (database.isOpen() && !database.isReadOnly() && genericTask != null && !relationId.isEmpty())
        {
            try
            {
                genericTask.setId(getNextId());
                saveTask(genericTask, relationId, isFarmLink);

                return;
            }
            catch (Exception e)
            {
                Log.e(
                        "DatabaseInsert",
                        "Failed to create a new generic task: " + genericTask.getName()
                );
            }
        }
        else
        {
            Log.e("DatabaseInsert", "Error with database or vaccine/animal is null");
        }
        taskController.setSavedTaskResult(false);
    }

    @Override
    public void delete(int id)
    {
        try
        {
            String args[] = {Integer.toString(id)};
            database.delete(GENERIC_TASK_TABLE, "id_task=?", args);

            Log.i("DatabaseDelete", "Task deleted");
        }
        catch (Exception e)
        {
            Log.e("DatabaseDelete", "Failed to delete Task");

        }
    }

    @Override
    public void update(GenericTask genericTask)
    {
        try
        {
            String args[] = { Integer.toString(genericTask.getId()) };
            ContentValues cv = new ContentValues();
            cv.put("task_name", genericTask.getName());
            cv.put("description", genericTask.getDescription());
            int isDone = genericTask.isDone() ? 1 : 0;
            cv.put("done", isDone);
            database.update(GENERIC_TASK_TABLE, cv, "id_task=?", args);
            Log.i("UpdateDatabase", "Updated generic task successful");
            taskController.setSavedTaskResult(true);
        }
        catch (Exception e)
        {
            Log.i("UpdateDatabase", "Failed to update generic task");
            taskController.setSavedTaskResult(false);
        }
    }

    // TODO: ...
    @Override
    public GenericTask get(int id)
    {
        String query = "SELECT * FROM " + GENERIC_TASK_TABLE + " AS task" +
                " WHERE task.id_task=?";

        String[] args = new String[]{Integer.toString(id)};

        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex("id_task");
        int nameIndex = cursor.getColumnIndex("task_name");
        int descriptionIndex = cursor.getColumnIndex("description");
        int doneIndex = cursor.getColumnIndex("done");

        GenericTask genericTask = new GenericTask();
        genericTask.setId(cursor.getInt(idIndex));
        genericTask.setName(cursor.getString(nameIndex));
        genericTask.setDescription(cursor.getString(descriptionIndex));
        genericTask.setDone(cursor.getInt(doneIndex) == 1);

        return genericTask;
    }

    @Override
    public List<GenericTask> getAnimalTasks(String id)
    {
        String query = "SELECT * FROM " + GENERIC_TASK_TABLE +
                " AS task INNER JOIN " + LINK_TASK_ANIMAL_TABLE + " AS linkt " +
                " ON linkt.id_task=task.id_task WHERE linkt.id_animal=?";

        String[] args = new String[]{id};

        return queryGetTasks(query, args);
    }

    @Override
    public List<GenericTask> getFarmTasks(String id)
    {
        String query = "SELECT * FROM " + GENERIC_TASK_TABLE +
                " AS task INNER JOIN " + LINK_TASK_FARM_TABLE + " AS linkt " +
                " ON linkt.id_task=task.id_task WHERE linkt.id_farm=?";

        String[] args = new String[]{id};

        return queryGetTasks(query, args);
    }

    private List<GenericTask> queryGetTasks(String query, String[] args)
    {
        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex("id_task");
        int nameIndex = cursor.getColumnIndex("task_name");
        int descriptionIndex = cursor.getColumnIndex("description");
        int doneIndex = cursor.getColumnIndex("done");

        List<GenericTask> genericTaskList = new ArrayList<>();

        if (cursor.getCount() > 0)
        {
            do
            {
                GenericTask genericTask = new GenericTask();

                genericTask.setId(cursor.getInt(idIndex));
                genericTask.setName(cursor.getString(nameIndex));
                genericTask.setDescription(cursor.getString(descriptionIndex));
                genericTask.setDone(cursor.getInt(doneIndex) == 1);

                genericTaskList.add(genericTask);

            } while (cursor.moveToNext());
        }

        return genericTaskList;
    }

    private void saveTask(GenericTask genericTask, String animalOrFarmId, boolean isFarmLink)
    {

        ContentValues cv = new ContentValues();
        cv.put("id_task", genericTask.getId());
        cv.put("task_name ", genericTask.getName());
        cv.put("description", genericTask.getDescription());

        database.insert(GENERIC_TASK_TABLE, null, cv);

        Log.d("DatabaseInsert", "Created a new task: " + genericTask.getName());

        if (isFarmLink)
        {
            createFarmGenericTaskLink(genericTask.getId(), animalOrFarmId);
        }
        else
        {
            createAnimalGenericTaskLink(genericTask.getId(), animalOrFarmId);
        }

        taskController.setSavedTaskResult(true);
    }

    private void createFarmGenericTaskLink(int taskId, String farmId)
    {
        try
        {
            ContentValues cv = new ContentValues();

            cv.put("id_farm", farmId);
            cv.put("id_task", taskId);

            database.insert(LINK_TASK_FARM_TABLE, null, cv);
            Log.i("DatabaseInsert", "Created farm-task link");
        }
        catch (Exception e)
        {
            Log.e("DatabaseInsert", "Failed to create farm-task link");
            delete(taskId);
        }
    }

    private void createAnimalGenericTaskLink(int taskId, String animalId)
    {
        try
        {
            ContentValues cv = new ContentValues();

            cv.put("id_animal", animalId);
            cv.put("id_task", taskId);

            database.insert(LINK_TASK_ANIMAL_TABLE, null, cv);
            Log.i("DatabaseInsert", "Created animal-task link");
        }
        catch (Exception e)
        {
            Log.e("DatabaseInsert", "Failed to create animal-task link");
            delete(taskId);
        }
    }

    private int getNextId()
    {
        String query = "SELECT * FROM " + GENERIC_TASK_TABLE + " ORDER BY id_task DESC LIMIT 1";
        int value = 0;

        try
        {
            Cursor cursor = database.rawQuery(query, new String[]{});
            cursor.moveToFirst();

            do
            {
                value = cursor.getInt(0);

            } while (cursor.moveToNext());


        }
        catch (Exception e)
        {
            return value;
        }

        return value + 1;
    }
}
