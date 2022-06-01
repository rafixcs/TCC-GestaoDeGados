package com.udemy.cursoandroid.gestaogados.View.main.register;

import static com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper.SetToastMessageAndShow;

import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.udemy.cursoandroid.gestaogados.Controller.animals.info.AnimalInfoController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.info.IAnimalInfoController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.register.IRegisterAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.register.RegisterAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.FarmController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;
import com.udemy.cursoandroid.gestaogados.Helper.NfcHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoCommonCollection;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.InfoTypeEnum;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmCollection;
import com.udemy.cursoandroid.gestaogados.Model.Farm.LootCollection;
import com.udemy.cursoandroid.gestaogados.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import com.udemy.cursoandroid.gestaogados.databinding.FragmentRegisterBovineBinding;


//TODO: implement activity to register animal attributes and save to the database

public class RegisterBovineFragment extends Fragment  implements  IRegisterBovineView
{

    private FragmentRegisterBovineBinding binding;

    private EditText mName;
    private EditText mDate;
    private Spinner mSpinnerRace;
    private Spinner mSpinnerType;
    private Spinner mSpinnerSex;
    private Spinner mSpinnerLifePhase;
    private Spinner mSpinnerFarm;
    private Spinner mSpinnerLoot;
    private Button mButtonRegister;

    private IFarmController farmController;
    private IAnimalInfoController animalInfoController;

    private boolean mSaveTagEnabled;

    private FarmCollection mFarmCollection;
    private LootCollection mLootCollection;

    private InfoCommonCollection raceCollection;
    private InfoCommonCollection sexTypeCollection;
    private InfoCommonCollection lifePhaseCollection;
    private InfoCommonCollection animalTypeCollection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentRegisterBovineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        farmController = new FarmController(this, getContext());
        animalInfoController = new AnimalInfoController(this, getContext());

        initializeObjectsView(root);

        mSaveTagEnabled = false;
        mButtonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSaveTagEnabled = true;
            }
        });


        return root;
    }

    private void initializeObjectsView(View root)
    {
        mName = root.findViewById(R.id.editNameAnmimalRegister);
        mDate = root.findViewById(R.id.editDateAnmimalRegister);
        mSpinnerRace = root.findViewById(R.id.spinnerRaceAnmimalRegister);
        mSpinnerType = root.findViewById(R.id.spinnerTypeAnmimalRegister);
        mSpinnerSex = root.findViewById(R.id.spinnerSexAnmimalRegister);
        mSpinnerLifePhase = root.findViewById(R.id.spinnerLifePhaseAnmimalRegister);
        mSpinnerFarm = root.findViewById(R.id.spinnerFarmAnmimalRegister);
        mSpinnerLoot = root.findViewById(R.id.spinnerLootAnmimalRegister);
        mButtonRegister = root.findViewById(R.id.btnRegisterAnimalRegister);

        initializeSpinners();
    }

    private void initializeSpinners()
    {

        raceCollection = animalInfoController.getInfoList(InfoTypeEnum.RACE_TYPE);
        sexTypeCollection = animalInfoController.getInfoList(InfoTypeEnum.SEX_TYPE);
        lifePhaseCollection = animalInfoController.getInfoList(InfoTypeEnum.LIFE_PHASE);
        animalTypeCollection = animalInfoController.getInfoList(InfoTypeEnum.ANIMAL_TYPE);

        ArrayAdapter<String> adapterRaces = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                raceCollection.getInfoNames()
        );

        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                animalTypeCollection.getInfoNames()
        );

        ArrayAdapter<String> adapterSexTypes = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                sexTypeCollection.getInfoNames()
        );

        ArrayAdapter<String> adapterLifePhase = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                lifePhaseCollection.getInfoNames()
        );

        mFarmCollection = new FarmCollection(farmController.getFarms());

        ArrayAdapter<String> adapterFarm = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                mFarmCollection.getFarmsNames()
        );

        mSpinnerRace.setAdapter(adapterRaces);
        mSpinnerSex.setAdapter(adapterSexTypes);
        mSpinnerType.setAdapter(adapterType);
        mSpinnerLifePhase.setAdapter(adapterLifePhase);
        mSpinnerFarm.setAdapter(adapterFarm);

        mSpinnerFarm.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                mLootCollection = farmController.getFarmsLoots(mFarmCollection.get(position).getId());
                ArrayAdapter<String> adapterLoot = new ArrayAdapter<String>(
                        getContext(),
                        androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                        mLootCollection.getLootsNames()
                );
                mSpinnerLoot.setAdapter(adapterLoot);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                return;
            }
        });

    }

    public void setNfcIntent(Intent intent, NfcHelper nfc)
    {
        if (mSaveTagEnabled)
        {
            if (NfcAdapter.ACTION_TAG_DISCOVERED.equals(intent.getAction()) ||
                    NfcAdapter.ACTION_NDEF_DISCOVERED.equals(intent.getAction()))
            {
                String keyRegister = UUID.randomUUID().toString();

                nfc.setMyTag(intent.getParcelableExtra(NfcAdapter.EXTRA_TAG));
                if (nfc.SaveTagContent(keyRegister))
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

                    AnimalRegister animal = new AnimalRegister(name, birthdate,sex, type, race, lifePhase, farmId, lootId);
                    animal.setId(keyRegister);

                    IRegisterAnimalController controller = new RegisterAnimalController(this, getContext());
                    controller.addNewRegister(animal);
                }
                else
                {
                    SetToastMessageAndShow( "Failed to save into the tag",getContext());
                }
            }
            mSaveTagEnabled = false;
        }
    }

    @Override
    public void setSaveResult(boolean result)
    {
        if (result)
        {
            SetToastMessageAndShow( "Saved successfully",getContext());
        }
        else
        {
            SetToastMessageAndShow( "Unable to save content",getContext());
        }
    }
}