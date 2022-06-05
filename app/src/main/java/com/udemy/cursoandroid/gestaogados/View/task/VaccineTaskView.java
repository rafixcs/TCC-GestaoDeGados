package com.udemy.cursoandroid.gestaogados.View.task;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import com.udemy.cursoandroid.gestaogados.Controller.task.IVaccineTaskController;
import com.udemy.cursoandroid.gestaogados.Controller.task.VaccineTaskController;
import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;
import com.udemy.cursoandroid.gestaogados.R;

public class VaccineTaskView implements ITaskView, View.OnClickListener{

    private Activity parentActivity;

    private TextView mTitle;
    private EditText mVaccineName;
    private EditText mDate;
    private EditText mDescription;
    private Button mSaveButton;
    private CheckBox mCheckBoxDone;

    private String animalRegisterId;

    private Integer taskId;
    private boolean isConsult;

    private IVaccineTaskController controller;
    private VaccineTask vaccineTask;

    public VaccineTaskView(Activity parentActivity, String animalRegisterId, Integer taskId)
    {
        this.parentActivity = parentActivity;
        this.animalRegisterId = animalRegisterId;
        this.taskId = taskId;
        this.isConsult = taskId >= 0;

        controller =
                new VaccineTaskController(this, parentActivity.getApplicationContext());

        buildView();
    }

    private void buildView()
    {
        mTitle = parentActivity.findViewById(R.id.registerConsultVaccineTitle);
        mVaccineName = parentActivity.findViewById(R.id.nameVaccineTask);
        mDate = parentActivity.findViewById(R.id.dateVaccineTask);
        mDescription = parentActivity.findViewById(R.id.descriptionVaccineTask);
        mCheckBoxDone = parentActivity.findViewById(R.id.checkBoxDoneVaccine);
        mSaveButton = parentActivity.findViewById(R.id.saveVaccineTask);
        mSaveButton.setOnClickListener(this);

        if (isConsult)
        {
            mSaveButton.setText(R.string.update);
            mCheckBoxDone.setVisibility(View.VISIBLE);
            mTitle.setText(R.string.vaccine_title_consult);

            vaccineTask = controller.getVaccineRegister(taskId.shortValue());

            if (vaccineTask == null)
            {
                ToastMessageHelper.SetToastMessageAndShow(
                        "Failed to recover vaccine",
                        parentActivity.getApplicationContext()
                );
                return;
            }

            mVaccineName.setText(vaccineTask.getName());
            mDate.setText(vaccineTask.getDate());
            mDescription.setText(vaccineTask.getDescription());
            mCheckBoxDone.setChecked(vaccineTask.getIsDone());
        }
        else
        {
            mCheckBoxDone.setVisibility(View.GONE);
        }

    }

    @Override
    public void onClick(View view)
    {
        String name = mVaccineName.getText().toString();
        String date = mDate.getText().toString();
        String description = mDescription.getText().toString();



        if (!isConsult)
        {
            vaccineTask = new VaccineTask(name, date, description);
            AnimalRegister animalRegister = new AnimalRegister();
            animalRegister.setId(animalRegisterId);

            vaccineTask.setDone(false);
            controller.saveVaccineTask(animalRegister, vaccineTask);
        }
        else
        {
            vaccineTask.setName(name);
            vaccineTask.setDate(date);
            vaccineTask.setDescription(description);
            vaccineTask.setDone(mCheckBoxDone.isChecked());
            controller.updateVaccine(vaccineTask);
        }
    }

    @Override
    public void setSaveResult(boolean result)
    {
        if (result)
        {
            parentActivity.finish();
        }
        else
        {
            ToastMessageHelper.SetToastMessageAndShow("Failed", parentActivity.getApplicationContext());
        }
    }
}
