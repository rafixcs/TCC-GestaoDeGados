package com.udemy.cursoandroid.gestaogados.View.farm;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.udemy.cursoandroid.gestaogados.Controller.task.GenericTaskController;
import com.udemy.cursoandroid.gestaogados.Controller.task.IGenericTaskController;
import com.udemy.cursoandroid.gestaogados.Helper.RecyclerItemClickListener;
import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.GenericTask;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.View.task.ConsultRegisterTaskActivity;
import com.udemy.cursoandroid.gestaogados.View.task.GenericTaskRegisterAdapter;
import com.udemy.cursoandroid.gestaogados.View.task.ITaskView;
import com.udemy.cursoandroid.gestaogados.View.task.RegisterTaskTypeEnum;

import java.util.List;

public class ConsultFarmTaskActivity extends AppCompatActivity
        implements View.OnClickListener, RecyclerItemClickListener.OnItemClickListener,
        ITaskView
{

    private RecyclerView recyclerView;
    private FloatingActionButton fab;
    private TextView emptyTasksListText;

    private int mFarmId;

    private IGenericTaskController genericTaskController;

    private List<GenericTask> genericTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_farm_task);

        emptyTasksListText = findViewById(R.id.emptyTaskListTextFarm);
        fab = findViewById(R.id.registerFarmTaskFab);
        fab.setOnClickListener(this);
        recyclerView = findViewById(R.id.recyclerViewTaskFarm);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
                this
        ));

        mFarmId = getIntent().getExtras().getInt("farmId");

        genericTaskController = new GenericTaskController(this, getApplicationContext());
    }

    private void reloadRecyclerView()
    {
        // Configure generic tasks recycler view
        genericTaskList = genericTaskController.getTaskByRelation(Integer.toString(mFarmId), true);
        if (genericTaskList.isEmpty())
        {
            emptyTasksListText.setVisibility(View.VISIBLE);
        }
        else
        {
            emptyTasksListText.setVisibility(View.GONE);

            // Configure adapter
            GenericTaskRegisterAdapter genericTaskRegisterAdapter = new GenericTaskRegisterAdapter(genericTaskList);

            // Configure recycler view
            RecyclerView.LayoutManager layoutManagerGenericTasks = new LinearLayoutManager(getApplicationContext());
            recyclerView.setLayoutManager(layoutManagerGenericTasks);
            recyclerView.setHasFixedSize(true);
            recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            recyclerView.setAdapter(genericTaskRegisterAdapter);
        }
    }

    @Override
    public void onClick(View view)
    {
        Intent intent = new Intent(getApplicationContext(), ConsultRegisterTaskActivity.class);
        intent.putExtra("taskType", RegisterTaskTypeEnum.GENERIC_TASK.ordinal());
        intent.putExtra("taskId", -1);
        intent.putExtra("linkId", Integer.toString(mFarmId));
        intent.putExtra("isForFarm", true);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadRecyclerView();
    }

    @Override
    public void onItemClick(View view, int position)
    {
        Intent intent = new Intent(getApplicationContext(), ConsultRegisterTaskActivity.class);
        intent.putExtra("taskType", RegisterTaskTypeEnum.GENERIC_TASK.ordinal());
        intent.putExtra("taskId", genericTaskList.get(position).getId());
        intent.putExtra("linkId", Integer.toString(mFarmId));
        intent.putExtra("isForFarm", true);
        startActivity(intent);
    }

    @Override
    public void onLongItemClick(View view, int position) {

    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

    }

    @Override
    public void setSaveResult(boolean result)
    {

    }
}