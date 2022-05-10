package com.udemy.cursoandroid.gestaogados.View.main.register;

import static com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper.SetToastMessageAndShow;

import android.app.PendingIntent;
import android.content.Intent;
import android.nfc.NfcAdapter;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.udemy.cursoandroid.gestaogados.Controller.animals.register.IRegisterAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.register.RegisterAnimalController;
import com.udemy.cursoandroid.gestaogados.Helper.NfcHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.R;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import com.udemy.cursoandroid.gestaogados.databinding.FragmentRegisterBovineBinding;


//TODO: implement activity to register animal attributes and save to the database

public class RegisterBovineFragment extends Fragment  implements  IRegisterBovineView{

    private FragmentRegisterBovineBinding binding;

    private EditText mName;
    private Spinner mSpinnerRace;
    private Spinner mSpinnerType;
    private Spinner mSpinnerSex;
    private Spinner mSpinnerLifePhase;
    private EditText mAge;
    private EditText mDate;
    private EditText mStatus;
    private Button mButtonRegister;

    private boolean mSaveTagEnabled;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentRegisterBovineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

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
        mSpinnerRace = root.findViewById(R.id.spinnerRaceAnmimalRegister);
        mSpinnerType = root.findViewById(R.id.spinnerTypeAnmimalRegister);
        mSpinnerSex = root.findViewById(R.id.spinnerSexAnmimalRegister);
        mSpinnerLifePhase = root.findViewById(R.id.spinnerLifePhaseAnmimalRegister);
        mAge = root.findViewById(R.id.editAgeAnmimalRegister);
        mDate = root.findViewById(R.id.editDateAnmimalRegister);
        mStatus = root.findViewById(R.id.editStatusAnmimalRegister);
        mButtonRegister = root.findViewById(R.id.btnRegisterAnimalRegister);

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
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listRaces
        );

        ArrayAdapter<String> adapterType = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listTypes
        );

        ArrayAdapter<String> adapterSexTypes = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listSexTypes
        );

        ArrayAdapter<String> adapterLifePhase = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                listLifePhase
        );

        mSpinnerRace.setAdapter(adapterRaces);
        mSpinnerSex.setAdapter(adapterSexTypes);
        mSpinnerType.setAdapter(adapterType);
        mSpinnerLifePhase.setAdapter(adapterLifePhase);

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
                    //TODO: save contents to the database
                    String name = mName.getText().toString();
                    String sex = mSpinnerSex.getSelectedItem().toString();
                    String type = mSpinnerType.getSelectedItem().toString();
                    String race = mSpinnerRace.getSelectedItem().toString();
                    String status = mStatus.getText().toString();
                    String age = mAge.getText().toString();
                    String birthdate = mDate.getText().toString();

                    AnimalRegister animal = new AnimalRegister(name, sex, type, status, race, age, birthdate);
                    animal.setKey(keyRegister);

                    IRegisterAnimalController controller = (IRegisterAnimalController) new RegisterAnimalController(this);
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
    public void onSaveRegisterResult(boolean result)
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