package com.udemy.cursoandroid.gestaogados.View.main.consult;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.ConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.IConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.R;

import java.util.ArrayList;
import java.util.List;

public class ConsultAnimalRegisterActivity extends AppCompatActivity implements IConsultAnimalRegisterView {

    private EditText mName;
    private Spinner mSpinnerRace;
    private Spinner mSpinnerType;
    private Spinner mSpinnerSex;
    private Spinner mSpinnerLifePhase;
    private EditText mAge;
    private EditText mDate;
    private EditText mStatus;
    private Button mButtonUpdate;

    private String tagKey;
    private AnimalRegister animalRegister;

    private IConsultAnimalController consultAnimalController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_animal_register);

        tagKey = getIntent().getExtras().getString("tagKey");

        initializeObjectsView();

        mButtonUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRegister();
            }
        });

        consultAnimalController = new ConsultAnimalController(this);
        consultAnimalController.ConsultAnimal(tagKey);
    }

    private void updateRegister()
    {
        String name = mName.getText().toString();
        String sex = mSpinnerSex.getSelectedItem().toString();
        String type = mSpinnerType.getSelectedItem().toString();
        String race = mSpinnerRace.getSelectedItem().toString();
        String status = mStatus.getText().toString();
        String age = mAge.getText().toString();
        String birthdate = mDate.getText().toString();

        AnimalRegister animal = new AnimalRegister(name, sex, type, status, race, age, birthdate);
        animal.setKey(tagKey);
        consultAnimalController.updateAnimal(animal);
    }


    private void initializeObjectsView()
    {
        mName = findViewById(R.id.editNameAnmimalConsult);
        mSpinnerRace = findViewById(R.id.spinnerRaceAnmimalConsult);
        mSpinnerType = findViewById(R.id.spinnerTypeAnmimalConsult);
        mSpinnerSex = findViewById(R.id.spinnerSexAnmimalConsult);
        mSpinnerLifePhase = findViewById(R.id.spinnerLifePhaseAnmimalConsult);
        mAge = findViewById(R.id.editAgeAnmimalConsult);
        mDate = findViewById(R.id.editDateAnmimalConsult);
        mStatus = findViewById(R.id.editStatusAnmimalConsult);
        mButtonUpdate = findViewById(R.id.btnUpdateAnimalConsult);

        initializeSpinners();
    }

    private void initializeSpinners()
    {
        List<String> listRaces = new ArrayList<>();
        listRaces.add("ANGUS");
        listRaces.add("HEREFORD");
        listRaces.add("LIMOUSIM");
        listRaces.add("BELGA");
        listRaces.add("BRAFORD");

        List<String> listTypes = new ArrayList<>();
        listTypes.add("GADO DE CORTE");
        listTypes.add("GADO LEITEIRO");

        List<String> listSexTypes = new ArrayList<>();
        listSexTypes.add("MASCULINO");
        listSexTypes.add("FEMININO");

        List<String> listLifePhase = new ArrayList<>();
        listLifePhase.add("CRIA");
        listLifePhase.add("RECRIA (DESENVOLVIMENTO)");
        listLifePhase.add("ENGORDA (TERMINAÇÃO)");

        ArrayAdapter<String> adapterRaces = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listRaces
        );

        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listTypes
        );

        ArrayAdapter<String> adapterSexTypes = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listSexTypes
        );

        ArrayAdapter<String> adapterLifePhase = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listLifePhase
        );

        mSpinnerRace.setAdapter(adapterRaces);
        mSpinnerSex.setAdapter(adapterSexTypes);
        mSpinnerType.setAdapter(adapterType);
        mSpinnerLifePhase.setAdapter(adapterLifePhase);
    }

    @Override
    public void setAnimalRegister(AnimalRegister animal)
    {
        animalRegister = animal;
        configureObjectsViewValues();
    }

    @Override
    public void onFailedConsultRegister()
    {
        ToastMessageHelper.SetToastMessageAndShow("Failed to consult animal register", this);
    }

    private void configureObjectsViewValues()
    {
        mName.setText(animalRegister.getName());
        mSpinnerRace.setSelection(1);
        mSpinnerType.setSelection(1);
        mSpinnerSex.setSelection(1);
        mSpinnerLifePhase.setSelection(1);
        mAge.setText(animalRegister.getAge());
        mDate.setText(animalRegister.getBirthdate());
        mStatus.setText(animalRegister.getStatus());
    }
}