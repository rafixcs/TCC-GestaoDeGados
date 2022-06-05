package com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.udemy.cursoandroid.gestaogados.Controller.task.ITaskCommonController;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;

import java.util.ArrayList;
import java.util.List;

public class VaccineDAO implements IVaccineDAO
{
    private final String VACCINE_TABLE = "app_vaccine";
    private final String LINK_VACCINE_ANIMAL_TABLE = "app_link_animal_vaccine";


    private SQLiteDatabase database;
    private ITaskCommonController taskController;

    public VaccineDAO(SQLiteDatabase database, ITaskCommonController taskController)
    {
        this.database = database;
        this.taskController = taskController;
    }

    @Override
    public void save(AnimalRegister animalRegister, VaccineTask vaccineTask)
    {
        if (database == null || taskController == null)
        {
            Log.e("Database", "Insertion database or controller is null");
            return;
        }

        if (database.isOpen() && !database.isReadOnly() && vaccineTask != null && animalRegister != null)
        {
            try
            {
                vaccineTask.setId(getNextId());
                saveVaccine(animalRegister, vaccineTask);

                return;
            }
            catch (Exception e)
            {
                Log.e("DatabaseInsert", "Failed to create a new user: " + vaccineTask.getName());
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
            String args[] = { Integer.toString(id) };
            database.delete(VACCINE_TABLE, "id_loot=?", args);

            Log.i("DatabaseDelete", "Vaccine deleted");
        }
        catch (Exception e)
        {
            Log.e("DatabaseDelete", "Failed to delete Vaccine");
        }
    }

    @Override
    public void update(VaccineTask vaccineTask)
    {
        try
        {

            String args[] = { Integer.toString(vaccineTask.getId()) };
            ContentValues cv = new ContentValues();
            cv.put("name", vaccineTask.getName());
            cv.put("date", vaccineTask.getDate());
            cv.put("description", vaccineTask.getDescription());
            int isDone = vaccineTask.getIsDone() ? 1 : 0;
            cv.put("done", isDone);
            database.update(VACCINE_TABLE, cv, "id_vaccine=?", args);
            Log.i("INFO", "Tarefa salva com sucesso");
            taskController.setSavedTaskResult(true);
        }
        catch (Exception e)
        {
            Log.i("INFO", "Erro ao salvar a tarefa");
            taskController.setSavedTaskResult(false);
        }

    }

    @Override
    public VaccineTask get(int id)
    {
        String query = "SELECT * FROM " + VACCINE_TABLE + " AS vaccine" +
                " WHERE vaccine.id_vaccine=?";

        String[] args = new String[]{Integer.toString(id)};

        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex("id_vaccine");
        int nameIndex = cursor.getColumnIndex("name");
        int dateIndex = cursor.getColumnIndex("date");
        int descriptionIndex = cursor.getColumnIndex("description");
        int doneIndex = cursor.getColumnIndex("done");

        VaccineTask vaccineTask = new VaccineTask();
        vaccineTask.setId(cursor.getInt(idIndex));
        vaccineTask.setName(cursor.getString(nameIndex));
        vaccineTask.setDate(cursor.getString(dateIndex));
        vaccineTask.setDescription(cursor.getString(descriptionIndex));
        boolean isDone = cursor.getInt(doneIndex) == 1;
        vaccineTask.setDone(isDone);

        return vaccineTask;
    }

    @Override
    public List<VaccineTask> getAnimalVaccines(String animalId)
    {
        String query = "SELECT * FROM " + VACCINE_TABLE +
                " AS vaccine INNER JOIN " + LINK_VACCINE_ANIMAL_TABLE + " AS linkt " +
                " ON linkt.id_vaccine=vaccine.id_vaccine WHERE linkt.id_animal=?";

        String[] args = new String[]{animalId};

        Cursor cursor= database.rawQuery(query, args);
        cursor.moveToFirst();

        int idIndex = cursor.getColumnIndex("id_vaccine");
        int nameIndex = cursor.getColumnIndex("name");
        int dateIndex = cursor.getColumnIndex("date");
        int descriptionIndex = cursor.getColumnIndex("description");
        int doneIndex = cursor.getColumnIndex("done");

        List<VaccineTask> vaccineTaskList = new ArrayList<>();

        if (cursor.getCount() > 0)
        {
            do
            {
                VaccineTask vaccineTask = new VaccineTask();
                vaccineTask.setId(cursor.getInt(idIndex));
                vaccineTask.setName(cursor.getString(nameIndex));
                vaccineTask.setDate(cursor.getString(dateIndex));
                vaccineTask.setDescription(cursor.getString(descriptionIndex));
                boolean isDone = cursor.getInt(doneIndex) == 1;
                vaccineTask.setDone(isDone);

                vaccineTaskList.add(vaccineTask);

            } while (cursor.moveToNext());
        }

        return vaccineTaskList;
    }

    private void saveVaccine(AnimalRegister animalRegister, VaccineTask vaccineTask)
    {
        ContentValues cv = new ContentValues();
        cv.put("id_vaccine", vaccineTask.getId());
        cv.put("date", vaccineTask.getDate());
        cv.put("name", vaccineTask.getName());
        cv.put("description", vaccineTask.getDescription());

        database.insert(VACCINE_TABLE, null, cv);

        Log.d("DatabaseInsert", "Created a new vaccine: " + vaccineTask.getName());

        createLinkAnimalVaccine(animalRegister, vaccineTask);

        taskController.setSavedTaskResult(true);
    }

    private void createLinkAnimalVaccine(AnimalRegister animalRegister, VaccineTask vaccineTask)
    {
        try
        {
            ContentValues cv = new ContentValues();

            cv.put("id_animal", animalRegister.getId());
            cv.put("id_vaccine", vaccineTask.getId());

            database.insert(LINK_VACCINE_ANIMAL_TABLE, null, cv);
            Log.i("DatabaseInsert", "Created animal-vaccine link");
        }
        catch (Exception e)
        {
            Log.e("DatabaseInsert", "Failed to create animal-vaccine link");
            delete(vaccineTask.getId());
        }
    }

    private int getNextId()
    {
        String query = "SELECT * FROM " + VACCINE_TABLE + " ORDER BY id_vaccine DESC LIMIT 1";
        String[] args = new String[]{};
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
            return 0;
        }

        return value + 1;
    }
}
