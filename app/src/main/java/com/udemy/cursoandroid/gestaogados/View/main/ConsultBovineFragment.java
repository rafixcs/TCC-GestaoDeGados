package com.udemy.cursoandroid.gestaogados.View.main;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.udemy.cursoandroid.gestaogados.Helper.NfcHelper;
import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalsRecords;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.View.main.consult.ConsultAnimalRegisterActivity;
import com.udemy.cursoandroid.gestaogados.databinding.FragmentConsultBovineBinding;

//TODO: add a list view for consulting without the tag

public class ConsultBovineFragment extends Fragment {

    private FragmentConsultBovineBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentConsultBovineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        return root;
    }

    public void getFromTag(Intent intent, NfcHelper nfc) {
        String uuid = nfc.ReadFromIntent(intent);

        AnimalsRecords records = AnimalsRecords.getsInstance();
        AnimalRegister animal = records.getAnimalRegister(uuid);

        if (animal != null)
        {
            Intent intentConsult = new Intent(getContext(), ConsultAnimalRegisterActivity.class);
            startActivity(intentConsult);
        }
        else
        {
            ToastMessageHelper.SetToastMessageAndShow("Failled to consult animal", getContext());
        }

    }
}