package com.udemy.cursoandroid.gestaogados.View.task;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;

import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.R;

public class ConsultRegisterTaskActivity extends AppCompatActivity {

    private ViewStub stubLayout;
    private View layoutView;

    private VaccineTaskView vaccineTaskView;
    private GenericTaskView genericTaskView;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_task);

        int taskType = getIntent().getExtras().getInt("taskType");
        Integer idTask = getIntent().getExtras().getInt("taskId");

        stubLayout = findViewById(R.id.layout_register_task_stub);

        if (taskType == RegisterTaskTypeEnum.GENERIC_TASK.ordinal())
        {
            boolean isForFarm = getIntent().getExtras().getBoolean("isForFarm");
            String linkId = getIntent().getExtras().getString("linkId");

            stubLayout.setLayoutResource(R.layout.register_generic_task_layout);
            layoutView = stubLayout.inflate();
            genericTaskView = new GenericTaskView(this, linkId, isForFarm, idTask);
        }
        else if (taskType == RegisterTaskTypeEnum.VACCINE_TASK.ordinal())
        {
            stubLayout.setLayoutResource(R.layout.register_vaccine_task_layout);
            layoutView = stubLayout.inflate();
            String animalRegisterId = getIntent().getExtras().getString("animalRegister");
            vaccineTaskView = new VaccineTaskView(this, animalRegisterId, idTask);
        }
    }
}