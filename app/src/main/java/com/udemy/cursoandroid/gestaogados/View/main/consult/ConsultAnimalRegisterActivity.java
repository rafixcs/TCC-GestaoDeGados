package com.udemy.cursoandroid.gestaogados.View.main.consult;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.ConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.IConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.info.AnimalInfoController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.info.IAnimalInfoController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.register.IRegisterAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.register.RegisterAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.FarmController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;
import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoCommonCollection;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoTypeEnum;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegisterStatusEnum;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmCollection;
import com.udemy.cursoandroid.gestaogados.Model.Farm.LootCollection;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.View.task.ConsultRegisterTaskActivity;
import com.udemy.cursoandroid.gestaogados.View.task.RegisterTaskTypeEnum;

import java.util.ArrayList;
import java.util.List;

public class ConsultAnimalRegisterActivity extends AppCompatActivity implements IConsultAnimalRegisterView {

    private ImageView mImageView;
    private EditText mName;
    private EditText mDate;
    private Spinner mSpinnerRace;
    private Spinner mSpinnerType;
    private Spinner mSpinnerSex;
    private Spinner mSpinnerLifePhase;
    private Spinner mSpinnerFarm;
    private Spinner mSpinnerLoot;
    private Spinner mSpinnerStatus;
    private Button mButtonUpdate;

    private String tagKey;
    private AnimalRegister animalRegister;

    private IConsultAnimalController consultAnimalController;
    private IRegisterAnimalController registerAnimalController;

    private IFarmController farmController;
    private IAnimalInfoController animalInfoController;

    private FarmCollection mFarmCollection;
    private LootCollection mLootCollection;

    private InfoCommonCollection raceCollection;
    private InfoCommonCollection sexTypeCollection;
    private InfoCommonCollection lifePhaseCollection;
    private InfoCommonCollection animalTypeCollection;
    private List<String> statusList;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consult_animal_register);

        tagKey = getIntent().getExtras().getString("tagKey");

        farmController = new FarmController(this, this);
        animalInfoController = new AnimalInfoController(this, getApplicationContext());

        mFarmCollection = new FarmCollection(farmController.getFarms());
        mLootCollection = farmController.getFarmsLoots(mFarmCollection.get(0).getId());

        initializeObjectsView();

        mButtonUpdate.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                updateRegister();
            }
        });

        consultAnimalController = new ConsultAnimalController(this, getApplicationContext());
        consultAnimalController.ConsultAnimal(tagKey);

        registerAnimalController = new RegisterAnimalController(this, getApplicationContext());
    }

    private void initializeObjectsView()
    {
        mImageView = findViewById(R.id.imageBovineConsult);
        mName = findViewById(R.id.editNameAnimalConsult);
        mDate = findViewById(R.id.editDateAnimalConsult);
        mSpinnerRace = findViewById(R.id.spinnerRaceAnimalConsult);
        mSpinnerType = findViewById(R.id.spinnerTypeAnimalConsult);
        mSpinnerSex = findViewById(R.id.spinnerSexAnimalConsult);
        mSpinnerLifePhase = findViewById(R.id.spinnerLifePhaseAnimalConsult);
        mSpinnerFarm = findViewById(R.id.spinnerFarmAnimalConsult);
        mSpinnerLoot = findViewById(R.id.spinnerLootAnimalConsult);
        mSpinnerStatus = findViewById(R.id.spinnerStatusAnimalConsult);
        mButtonUpdate = findViewById(R.id.btnRegisterAnimalConsult);


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
        int farmIndexId = (int) mSpinnerFarm.getSelectedItemId();
        int lootIndexId = (int) mSpinnerLoot.getSelectedItemId();
        int farmId = mFarmCollection.get(farmIndexId).getId();
        int lootId = mLootCollection.get(lootIndexId).getId();
        AnimalRegisterStatusEnum statusEnum =
                AnimalRegisterStatusEnum.fromString(
                        statusList.get((int) mSpinnerStatus.getSelectedItemId()
                        )
                );

        AnimalRegister animal = new AnimalRegister(name, birthdate,sex, type, race, lifePhase,farmId, lootId);
        animal.setId(tagKey);
        animal.setStatus(statusEnum);
        registerAnimalController.updateAnimal(animal);
    }

    private void initializeSpinners()
    {
        raceCollection = animalInfoController.getInfoList(InfoTypeEnum.RACE_TYPE);
        sexTypeCollection = animalInfoController.getInfoList(InfoTypeEnum.SEX_TYPE);
        lifePhaseCollection = animalInfoController.getInfoList(InfoTypeEnum.LIFE_PHASE);
        animalTypeCollection = animalInfoController.getInfoList(InfoTypeEnum.ANIMAL_TYPE);

        statusList = new ArrayList<>();
        statusList.add(AnimalRegisterStatusEnum.ACTIVE.toString());
        statusList.add(AnimalRegisterStatusEnum.INACTIVE.toString());


        ArrayAdapter<String> adapterRaces = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                raceCollection.getInfoNames()
        );

        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                animalTypeCollection.getInfoNames()
        );

        ArrayAdapter<String> adapterSexTypes = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                sexTypeCollection.getInfoNames()
        );

        ArrayAdapter<String> adapterLifePhase = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                lifePhaseCollection.getInfoNames()
        );

        ArrayAdapter<String> adapterFarm = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                mFarmCollection.getFarmsNames()
        );

        ArrayAdapter<String> adapterStatus = new ArrayAdapter<String>(
                getApplicationContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                statusList
        );


        mSpinnerRace.setAdapter(adapterRaces);
        mSpinnerSex.setAdapter(adapterSexTypes);
        mSpinnerType.setAdapter(adapterType);
        mSpinnerLifePhase.setAdapter(adapterLifePhase);
        mSpinnerFarm.setAdapter(adapterFarm);
        mSpinnerStatus.setAdapter(adapterStatus);

        mSpinnerFarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mLootCollection = farmController.getFarmsLoots(mFarmCollection.get(position).getId());

                ArrayAdapter<String> adapterLoot = new ArrayAdapter<String>(
                        getApplicationContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        mLootCollection.getLootsNames()
                );
                mSpinnerLoot.setAdapter(adapterLoot);
                mSpinnerLoot.setSelection(mLootCollection.idToCollectionIndex(animalRegister.getLootId()));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });
    }

    private void loadAnimalImage()
    {
        if (animalRegister.getImgSource() == null)
            return;

        int resourceId = getResources()
                .getIdentifier(
                        animalRegister.getImgSource(),
                        "drawable",
                        getApplicationContext().getPackageName()
                );

        resourceId = resourceId == 0 ? R.drawable.common_animal_image_icon : resourceId;
        mImageView.setImageResource(resourceId);
    }

    @Override
    public void setAnimalRegister(AnimalRegister animal)
    {
        animalRegister = animal;
        configureObjectsViewValues();
        loadAnimalImage();
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
        mSpinnerFarm.setSelection(mFarmCollection.idToCollectionIndex(animalRegister.getFarmId()));
        mSpinnerStatus.setSelection(getStatusIndex(animalRegister.getStatus()));
    }

    private int getStatusIndex(AnimalRegisterStatusEnum statusEnum)
    {
        for(int i=0; i<statusList.size(); i++)
        {
            if (statusList.get(i).equals(statusEnum.toString()))
            {
                return i;
            }
        }

        return 0;
    }

    @Override
    public void setSaveResult(boolean result) {
        if (result)
        {
            ToastMessageHelper.SetToastMessageAndShow("Saved successful", this);
        }
        else
        {
            ToastMessageHelper.SetToastMessageAndShow("Update failed", this);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.consult_animal_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.vaccineConsultAction:
                onVaccineConsult();
                break;
            default: break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void onVaccineConsult()
    {
        Intent intent = new Intent(getApplicationContext(), ConsultAnimalVaccineActivity.class);
        intent.putExtra("animalRegisterId", animalRegister.getId());
        startActivity(intent);
    }
}