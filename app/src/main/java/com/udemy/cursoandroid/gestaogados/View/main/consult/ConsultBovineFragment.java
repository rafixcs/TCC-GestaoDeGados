package com.udemy.cursoandroid.gestaogados.View.main.consult;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.ConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.IConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.info.AnimalInfoController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.info.IAnimalInfoController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.FarmController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;
import com.udemy.cursoandroid.gestaogados.Helper.NfcHelper;
import com.udemy.cursoandroid.gestaogados.Helper.RecyclerItemClickListener;
import com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper;
import com.udemy.cursoandroid.gestaogados.Model.AnimalInfo.IInfoCommon;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.databinding.FragmentConsultBovineBinding;

import java.util.List;

public class ConsultBovineFragment extends Fragment implements IConsultAnimalRegisterView {

    private FragmentConsultBovineBinding binding;
    private IAnimalInfoController animalInfoController;
    private IConsultAnimalController consultAnimalController;
    private RecyclerView recyclerView;
    private List<AnimalRegister> animalRegisterList;

    private TextView emptyListText;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        binding = FragmentConsultBovineBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        emptyListText = root.findViewById(R.id.emptyAnimalsListText);
        recyclerView = root.findViewById(R.id.recyclerViewConsultAnimalFragment);
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getContext(),
                recyclerView,
                new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {
                        consultAnimal(animalRegisterList.get(position).getId());
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


        animalInfoController = new AnimalInfoController(this, getContext());
        consultAnimalController = new ConsultAnimalController(this, getContext());
        animalRegisterList = consultAnimalController.getAnimalsRegisters();

        return root;
    }

    private void reloadAnimalsRegisterList()
    {
        if (animalRegisterList.isEmpty())
        {
            emptyListText.setVisibility(View.VISIBLE);
        }
        else
        {
            emptyListText.setVisibility(View.GONE);
        }

        // Configure adapter
        AnimalRegisterAdapter animalRegisterAdapter =
                new AnimalRegisterAdapter(animalRegisterList, animalInfoController);

        // Configure recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        recyclerView.setAdapter(animalRegisterAdapter);
    }

    public void getFromTag(Intent intent, NfcHelper nfc)
    {
        String uuid = nfc.ReadFromIntent(intent);
        consultAnimal(uuid);
    }

    @Override
    public void setAnimalRegister(AnimalRegister animal) {

    }

    @Override
    public void onFailedConsultRegister() {

    }

    @Override
    public void setSaveResult(boolean result) {
        
    }

    @Override
    public void onResume() {
        super.onResume();
        reloadAnimalsRegisterList();
    }

    private void consultAnimal(String id)
    {
        IConsultAnimalController controller = new ConsultAnimalController(this, getContext());

        if (controller.CheckIfExists(id))
        {
            Intent intentConsult = new Intent(getContext(), ConsultAnimalRegisterActivity.class);
            intentConsult.putExtra("tagKey", id);
            startActivity(intentConsult);
        }
        else
        {
            ToastMessageHelper.SetToastMessageAndShow("Failed to consult animal", getContext());
        }
    }
}