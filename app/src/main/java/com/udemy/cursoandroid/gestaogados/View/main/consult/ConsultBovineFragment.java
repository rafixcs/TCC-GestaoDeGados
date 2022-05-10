package com.udemy.cursoandroid.gestaogados.View.main.consult;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.ConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.IConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Helper.NfcHelper;
import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.databinding.FragmentConsultBovineBinding;


//TODO: add a list view for consulting without the tag

public class ConsultBovineFragment extends Fragment implements IConsultAnimalRegisterView {

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

        IConsultAnimalController controller = new ConsultAnimalController(this);

        if (controller.CheckIfExists(uuid))
        {
            Intent intentConsult = new Intent(getContext(), ConsultAnimalRegisterActivity.class);
            intentConsult.putExtra("tagKey", uuid);
            startActivity(intentConsult);
        }
        else
        {
            ToastMessageHelper.SetToastMessageAndShow("Failed to consult animal", getContext());
        }

    }

    @Override
    public void setAnimalRegister(AnimalRegister animal) {

    }

    @Override
    public void onFailedConsultRegister() {

    }
}