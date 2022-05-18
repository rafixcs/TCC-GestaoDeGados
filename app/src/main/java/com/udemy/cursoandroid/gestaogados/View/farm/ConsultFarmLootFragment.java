package com.udemy.cursoandroid.gestaogados.View.farm;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.udemy.cursoandroid.gestaogados.Controller.farm.FarmController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.View.ICommonView;
import com.udemy.cursoandroid.gestaogados.databinding.FragmentConsultFarmLootBinding;

import java.util.List;


public class ConsultFarmLootFragment extends Fragment implements ICommonView {

    private FragmentConsultFarmLootBinding binding;
    private View viewRoot;

    private TextView mFarmQuantityView;
    private TextView mLootQuantityView;
    private Spinner mSpinnerFarmSelection;
    private Button mBtnConsultFarm;
    private Button mBtnRegisterNewFarm;

    private String mFarmQuantity;
    private String mLootQuantity;

    private IFarmController farmController;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentConsultFarmLootBinding.inflate(inflater, container, false);
        viewRoot = binding.getRoot();


        mFarmQuantityView = viewRoot.findViewById(R.id.farmQuantityConsultFarmLoot);
        mLootQuantityView = viewRoot.findViewById(R.id.lootQuantityConsultFarmLoot);
        mSpinnerFarmSelection = viewRoot.findViewById(R.id.farnsSpinnerConsultFarmLoot);
        mBtnConsultFarm = viewRoot.findViewById(R.id.consultFarmConsultFarmLoot);
        mBtnRegisterNewFarm = viewRoot.findViewById(R.id.newFarmBtnConsultFarmLoot);

        mBtnConsultFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegisterFarmActivity.class);
                intent.putExtra("firstAccess",false);
                intent.putExtra("consultFarm", mSpinnerFarmSelection.getSelectedItem().toString());
                startActivity(intent);
            }
        });

        mBtnRegisterNewFarm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), RegisterFarmActivity.class);
                intent.putExtra("firstAccess",false);
                startActivity(intent);
            }
        });

        farmController = new FarmController(this);

        reloadView();

        return viewRoot;
    }

    private void reloadView()
    {
        mFarmQuantity = Integer.toString(farmController.getFarmsQuantity());
        mLootQuantity = Integer.toString(farmController.getLootsTotalQuantity());

        mLootQuantityView.setText(mLootQuantity);
        mFarmQuantityView.setText(mFarmQuantity);

        ArrayAdapter<String> adapterFarm = new ArrayAdapter<String>(
                getContext(),
                androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,
                farmController.getFarmsNames()
        );

        mSpinnerFarmSelection.setAdapter(adapterFarm);

    }

    @Override
    public void setSaveResult(boolean result) {

    }

    @Override
    public void onResume() {
        super.onResume();
        reloadView();
    }
}