package com.udemy.cursoandroid.gestaogados.View.task;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.udemy.cursoandroid.gestaogados.Controller.task.GenericTaskController;
import com.udemy.cursoandroid.gestaogados.Controller.task.IGenericTaskController;
import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.R;

public class GenericTaskView implements ITaskView, View.OnClickListener
{

    private Activity parentActivity;

    private EditText mName;
    private EditText mDescription;
    private Button mSaveButton;

    public GenericTaskView(Activity parentActivity) {
        this.parentActivity = parentActivity;
        buildView();
    }

    private void buildView()
    {
        mName = parentActivity.findViewById(R.id.nameGenericTaskRegister);
        mDescription = parentActivity.findViewById(R.id.descriptionGenericTaskRegister);
        mSaveButton = parentActivity.findViewById(R.id.saveGenericTaskRegister);
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
        String name = mName.getText().toString();
        String description = mDescription.getText().toString();

        IGenericTaskController controller = new GenericTaskController(this);
        controller.saveGenericTask(name, description);
    }
}
