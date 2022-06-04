package com.udemy.cursoandroid.gestaogados.View.task;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.udemy.cursoandroid.gestaogados.Controller.task.IVaccineTaskController;
import com.udemy.cursoandroid.gestaogados.Controller.task.VaccineTaskController;
import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;
import com.udemy.cursoandroid.gestaogados.R;

public class VaccineTaskView implements ITaskView, View.OnClickListener{

    private Activity parentActivity;

    private EditText mVaccineName;
    private EditText mDate;
    private EditText mDescription;
    private Button mSaveButton;
    private String animalRegisterId;

    public VaccineTaskView(Activity parentActivity, String animalRegisterId)
    {
        this.parentActivity = parentActivity;
        this.animalRegisterId = animalRegisterId;
        buildView();
    }

    private void buildView()
    {
        mVaccineName = parentActivity.findViewById(R.id.nameVaccineTask);
        mDate = parentActivity.findViewById(R.id.dateVaccineTask);
        mDescription = parentActivity.findViewById(R.id.descriptionVaccineTask);
        mSaveButton = parentActivity.findViewById(R.id.saveVaccineTask);
        mSaveButton.setOnClickListener(this);
    }

    @Override
    public void setSaveRegisterResult(boolean result)
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

    @Override
    public void onClick(View view)
    {
        String name = mVaccineName.getText().toString();
        String date = mDate.getText().toString();
        String description = mDescription.getText().toString();

        IVaccineTaskController controller = new VaccineTaskController(this, parentActivity.getApplicationContext());
        VaccineTask vaccineTask = new VaccineTask(name, date, description);
        AnimalRegister animalRegister = new AnimalRegister();
        animalRegister.setId(animalRegisterId);
        controller.saveVaccineTask(animalRegister, vaccineTask);
    }
}
