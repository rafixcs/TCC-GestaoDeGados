package com.udemy.cursoandroid.gestaogados.View.main.consult;

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

import com.udemy.cursoandroid.gestaogados.Controller.task.VaccineTaskController;
import com.udemy.cursoandroid.gestaogados.Helper.RecyclerItemClickListener;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.View.task.ConsultRegisterTaskActivity;
import com.udemy.cursoandroid.gestaogados.View.task.ITaskView;
import com.udemy.cursoandroid.gestaogados.View.task.RegisterTaskTypeEnum;

import java.util.List;

public class ConsultAnimalVaccineActivity extends AppCompatActivity implements ITaskView {

    private RecyclerView recyclerView;
    private TextView emptyListText;

    private VaccineTaskController vaccineTaskController;

    private String animalRegisterId;
    List<VaccineTask> vaccineTaskList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_animal_vaccine);

        emptyListText = findViewById(R.id.emptyVaccineListText);
        animalRegisterId = getIntent().getExtras().getString("animalRegisterId");
        recyclerView = findViewById(R.id.recyclerViewConsultVaccine);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                recyclerView,
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
        ) {
        });

        vaccineTaskController = new VaccineTaskController(this, getApplicationContext());
    }

    private void reloadRecyclerView()
    {

        vaccineTaskList = vaccineTaskController.getAnimalVaccines(animalRegisterId);

        if (vaccineTaskList.isEmpty())
        {
            emptyListText.setVisibility(View.VISIBLE);
            return;
        }

        // Configure adapter
        VaccineRegisterAdapter vaccineRegisterAdapter = new VaccineRegisterAdapter(vaccineTaskList);

        // Configure recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(vaccineRegisterAdapter);
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
}