package com.udemy.cursoandroid.gestaogados.View.task;

import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.udemy.cursoandroid.gestaogados.Controller.task.GenericTaskController;
import com.udemy.cursoandroid.gestaogados.Controller.task.IGenericTaskController;
import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.GenericTask;
import com.udemy.cursoandroid.gestaogados.R;

public class GenericTaskView implements ITaskView, View.OnClickListener
{

    private Activity parentActivity;

    private EditText mName;
    private EditText mDescription;
    private CheckBox mCheckBoxDone;
    private Button mSaveButton;

    private boolean mIsForFarm;
    private String mLinkId;

    private boolean mIsConsult;
    private int mIdTaskConsult;

    private IGenericTaskController mGenericTaskController;
    private GenericTask mConsultGenericTask;

    public GenericTaskView(Activity parentActivity, String linkId,boolean isForFarm, int idTaskConsult)
    {
        this.parentActivity = parentActivity;
        this.mIsForFarm = isForFarm;
        this.mLinkId = linkId;
        this.mIdTaskConsult = idTaskConsult;
        this.mIsConsult = mIdTaskConsult >= 0;

        mGenericTaskController =
                new GenericTaskController(this, parentActivity.getApplicationContext());

        buildView();
    }

    private void buildView()
    {
        mCheckBoxDone = parentActivity.findViewById(R.id.checkBoxTaskDone);
        mName = parentActivity.findViewById(R.id.nameGenericTaskRegister);
        mDescription = parentActivity.findViewById(R.id.descriptionGenericTaskRegister);
        mSaveButton = parentActivity.findViewById(R.id.saveGenericTaskRegister);
        mSaveButton.setOnClickListener(this);

        if (this.mIsConsult)
        {
            mCheckBoxDone.setVisibility(View.VISIBLE);
            mSaveButton.setText(R.string.update);


            mConsultGenericTask = mGenericTaskController.getTask(mIdTaskConsult);
            mName.setText(mConsultGenericTask.getName());
            mDescription.setText(mConsultGenericTask.getDescription());
            mCheckBoxDone.setChecked(mConsultGenericTask.isDone());
        }
        else
        {
            mCheckBoxDone.setVisibility(View.GONE);
        }
    }

    @Override
    public void onClick(View view)
    {
        String name = mName.getText().toString();
        String description = mDescription.getText().toString();

        if (!mIsConsult)
        {
            mGenericTaskController.saveGenericTask(name, description, mLinkId, mIsForFarm);
        }
        else
        {
            mConsultGenericTask.setDone(mCheckBoxDone.isChecked());
            mConsultGenericTask.setName(name);
            mConsultGenericTask.setDescription(description);
            mGenericTaskController.updateTask(mConsultGenericTask);
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
