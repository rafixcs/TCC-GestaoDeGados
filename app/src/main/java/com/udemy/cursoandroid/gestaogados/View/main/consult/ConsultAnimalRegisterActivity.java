package com.udemy.cursoandroid.gestaogados.View.main.consult;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.ConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.IConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmsMockRecord;
import com.udemy.cursoandroid.gestaogados.R;

import java.util.ArrayList;
import java.util.List;

public class ConsultAnimalRegisterActivity extends AppCompatActivity implements IConsultAnimalRegisterView {

    private EditText mName;
    private EditText mDate;
    private Spinner mSpinnerRace;
    private Spinner mSpinnerType;
    private Spinner mSpinnerSex;
    private Spinner mSpinnerLifePhase;
    private Spinner mSpinnerFarm;
    private Spinner mSpinnerLoot;
    private Button mButtonUpdate;

    private String tagKey;
    private AnimalRegister animalRegister;

    private IConsultAnimalController consultAnimalController;

    private FloatingActionButton fabOpenRegisterTaskPopup;
    private AlertDialog.Builder registerTaskPopupBuilder;
    private AlertDialog registerTaskPopup;
    private Button popupBtnRegisterTask;
    private Button popupBtnRegisterVaccine;

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

    private void initializeObjectsView()
    {
        mName = findViewById(R.id.editNameAnimalConsult);
        mDate = findViewById(R.id.editDateAnimalConsult);
        mSpinnerRace = findViewById(R.id.spinnerRaceAnimalConsult);
        mSpinnerType = findViewById(R.id.spinnerTypeAnimalConsult);
        mSpinnerSex = findViewById(R.id.spinnerSexAnimalConsult);
        mSpinnerLifePhase = findViewById(R.id.spinnerLifePhaseAnimalConsult);
        mSpinnerFarm = findViewById(R.id.spinnerFarmAnimalConsult);
        mSpinnerLoot = findViewById(R.id.spinnerLootAnimalConsult);
        mButtonUpdate = findViewById(R.id.btnRegisterAnimalConsult);
        fabOpenRegisterTaskPopup = findViewById(R.id.openPopupActionButton);

        fabOpenRegisterTaskPopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRegisterTaskPopup();
            }
        });


        initializeSpinners();
    }

    private void updateRegister()
    {
        String name = mName.getText().toString();
        String birthdate = mDate.getText().toString();
        int sex = (int) mSpinnerSex.getSelectedItemId();
        int type = (int) mSpinnerType.getSelectedItemId();
        int race = (int) mSpinnerRace.getSelectedItemId();
        int lifePhase = (int) mSpinnerLifePhase.getSelectedItemId();
        int farm = (int) mSpinnerFarm.getSelectedItemId();
        int loot = (int) mSpinnerLoot.getSelectedItemId();

        AnimalRegister animal = new AnimalRegister(name, birthdate,sex, type, race, lifePhase,farm, loot);
        animal.setKey(tagKey);
        consultAnimalController.updateAnimal(animal);
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

        FarmsMockRecord farmsMockRecord = new FarmsMockRecord();


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

        ArrayAdapter<String> adapterFarm = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                farmsMockRecord.getFarmsNames()
        );


        mSpinnerRace.setAdapter(adapterRaces);
        mSpinnerSex.setAdapter(adapterSexTypes);
        mSpinnerType.setAdapter(adapterType);
        mSpinnerLifePhase.setAdapter(adapterLifePhase);
        mSpinnerFarm.setAdapter(adapterFarm);

        mSpinnerFarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                ArrayAdapter<String> adapterLoot = new ArrayAdapter<String>(
                        getApplicationContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        farmsMockRecord.getFarmLoots(position)
                );
                mSpinnerLoot.setAdapter(adapterLoot);
                mSpinnerLoot.setSelection(animalRegister.getLoot());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
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
        mDate.setText(animalRegister.getBirthdate());
        mSpinnerRace.setSelection(animalRegister.getRace());
        mSpinnerType.setSelection(animalRegister.getType());
        mSpinnerSex.setSelection(animalRegister.getSex());
        mSpinnerLifePhase.setSelection(animalRegister.getLifePhase());
        mSpinnerFarm.setSelection(animalRegister.getFarm());
    }

    private void showRegisterTaskPopup()
    {
        registerTaskPopupBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup_add_new_task, null);

        popupBtnRegisterVaccine = popupView.findViewById(R.id.popupRegisterVaccine);
        popupBtnRegisterTask = popupView.findViewById(R.id.popupRegisterTask);

        popupBtnRegisterVaccine.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        popupBtnRegisterTask.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        registerTaskPopupBuilder.setView(popupView);
        registerTaskPopup = registerTaskPopupBuilder.create();
        registerTaskPopup.show();
    }
}