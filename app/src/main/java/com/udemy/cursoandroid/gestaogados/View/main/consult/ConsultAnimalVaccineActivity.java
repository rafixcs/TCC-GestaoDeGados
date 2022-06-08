package com.udemy.cursoandroid.gestaogados.View.main.consult;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.udemy.cursoandroid.gestaogados.Controller.task.GenericTaskController;
import com.udemy.cursoandroid.gestaogados.Controller.task.IGenericTaskController;
import com.udemy.cursoandroid.gestaogados.Controller.task.IVaccineTaskController;
import com.udemy.cursoandroid.gestaogados.Controller.task.VaccineTaskController;
import com.udemy.cursoandroid.gestaogados.Helper.RecyclerItemClickListener;
import com.udemy.cursoandroid.gestaogados.Model.Task.Generic.GenericTask;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.View.task.ConsultRegisterTaskActivity;
import com.udemy.cursoandroid.gestaogados.View.task.GenericTaskRegisterAdapter;
import com.udemy.cursoandroid.gestaogados.View.task.ITaskView;
import com.udemy.cursoandroid.gestaogados.View.task.RegisterTaskTypeEnum;
import com.udemy.cursoandroid.gestaogados.View.task.VaccineRegisterAdapter;

import java.util.ArrayList;
import java.util.List;

public class ConsultAnimalVaccineActivity extends AppCompatActivity implements ITaskView {

    private FloatingActionButton fabOpenRegisterTaskPopup;
    private RecyclerView recyclerViewVaccine;
    private RecyclerView recyclerViewTasks;
    private TextView emptyVaccineListText;
    private TextView emptyTasksListText;

    private AlertDialog.Builder registerTaskPopupBuilder;
    private AlertDialog registerTaskPopup;
    private Button popupBtnRegisterTask;
    private Button popupBtnRegisterVaccine;

    private IVaccineTaskController vaccineTaskController;
    private IGenericTaskController genericTaskController;

    private String animalRegisterId;
    private List<VaccineTask> vaccineTaskList;
    private List<GenericTask> genericTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_animal_vaccine);

        emptyVaccineListText = findViewById(R.id.emptyVaccineListText);
        emptyTasksListText = findViewById(R.id.emptyTaskListText);
        animalRegisterId = getIntent().getExtras().getString("animalRegisterId");
        fabOpenRegisterTaskPopup = findViewById(R.id.openPopupActionButton);
        fabOpenRegisterTaskPopup.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                showRegisterTaskPopup();
            }
        });

        vaccineTaskController = new VaccineTaskController(this, getApplicationContext());
        genericTaskController = new GenericTaskController(this, getApplicationContext());
        buildRecyclerViews();
    }


    private void buildRecyclerViews()
    {
        recyclerViewVaccine = findViewById(R.id.recyclerViewConsultVaccine);
        recyclerViewVaccine.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerViewVaccine,
                new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        Intent intent = new Intent(getApplicationContext(), ConsultRegisterTaskActivity.class);
                        intent.putExtra("taskType", RegisterTaskTypeEnum.VACCINE_TASK.ordinal());
                        intent.putExtra("taskId", vaccineTaskList.get(position).getId());
                        intent.putExtra("animalRegister", animalRegisterId);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position)
                    {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

        recyclerViewTasks = findViewById(R.id.recyclerViewConsultTask);
        recyclerViewTasks.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerViewTasks,
                new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        Intent intent = new Intent(getApplicationContext(), ConsultRegisterTaskActivity.class);
                        intent.putExtra("taskType", RegisterTaskTypeEnum.GENERIC_TASK.ordinal());
                        intent.putExtra("taskId", genericTaskList.get(position).getId());
                        intent.putExtra("linkId", animalRegisterId);
                        startActivity(intent);
                    }

                    @Override
                    public void onLongItemClick(View view, int position)
                    {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));
    }

    private void reloadRecyclerView()
    {
        // Configure vaccine recycler view
        vaccineTaskList = vaccineTaskController.getAnimalVaccines(animalRegisterId);
        if (vaccineTaskList.isEmpty())
        {
            emptyVaccineListText.setVisibility(View.VISIBLE);
        }
        else
        {
            emptyVaccineListText.setVisibility(View.GONE);

            // Configure adapter
            VaccineRegisterAdapter vaccineRegisterAdapter = new VaccineRegisterAdapter(vaccineTaskList);

            // Configure recycler view
            RecyclerView.LayoutManager layoutManagerVaccines = new LinearLayoutManager(getApplicationContext());
            recyclerViewVaccine.setLayoutManager(layoutManagerVaccines);
            recyclerViewVaccine.setHasFixedSize(true);
            recyclerViewVaccine.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            recyclerViewVaccine.setAdapter(vaccineRegisterAdapter);

        }

        // Configure generic tasks recycler view
        genericTaskList = genericTaskController.getTaskByRelation(animalRegisterId, false);
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
            recyclerViewTasks.setLayoutManager(layoutManagerGenericTasks);
            recyclerViewTasks.setHasFixedSize(true);
            recyclerViewTasks.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
            recyclerViewTasks.setAdapter(genericTaskRegisterAdapter);
        }
    }

    @Override
    public void setSaveResult(boolean result)
    {

    }

    @Override
    protected void onResume() {
        super.onResume();
        reloadRecyclerView();
    }

    private void showRegisterTaskPopup()
    {
        registerTaskPopupBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup_add_new_task, null);

        popupBtnRegisterVaccine = popupView.findViewById(R.id.popupRegisterVaccine);
        popupBtnRegisterTask = popupView.findViewById(R.id.popupRegisterTask);

        popupBtnRegisterVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ConsultRegisterTaskActivity.class);
                intent.putExtra("taskType", RegisterTaskTypeEnum.VACCINE_TASK.ordinal());
                intent.putExtra("taskId", -1);
                intent.putExtra("animalRegister", animalRegisterId);
                startActivity(intent);
            }
        });

        popupBtnRegisterTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view)
            {
                Intent intent = new Intent(getApplicationContext(), ConsultRegisterTaskActivity.class);
                intent.putExtra("taskType", RegisterTaskTypeEnum.GENERIC_TASK.ordinal());
                intent.putExtra("taskId", -1);
                intent.putExtra("linkId", animalRegisterId);
                intent.putExtra("isForFarm", false);
                startActivity(intent);
            }
        });

        registerTaskPopupBuilder.setView(popupView);
        registerTaskPopup = registerTaskPopupBuilder.create();
        registerTaskPopup.show();
    }
}