package com.udemy.cursoandroid.gestaogados.View.farm;

import static com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper.SetToastMessageAndShow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.udemy.cursoandroid.gestaogados.Controller.farm.FarmController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;
import com.udemy.cursoandroid.gestaogados.Helper.RecyclerItemClickListener;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Farm;
import com.udemy.cursoandroid.gestaogados.Model.Farm.Loot;
import com.udemy.cursoandroid.gestaogados.Model.Farm.LootCollection;
import com.udemy.cursoandroid.gestaogados.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterFarmActivity extends AppCompatActivity implements IRegisterFarmView
{
    private RecyclerView lootListView;
    private LootListAdapter lootListAdapter;
    private LootCollection lootCollection;

    private EditText mName;
    private EditText mLocation;
    private Button mSaveButton;
    private FloatingActionButton mAddLoot;

    private AlertDialog.Builder addLootPopupBuilder;
    private AlertDialog addLootPopup;
    private EditText mLootName;
    private Button mBtnSaveLoot;

    private IFarmController farmController;

    private boolean isFirstAccess;
    private boolean isConsult;
    private String farmConsultName;
    private Farm consultFarm;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_farm);

        isFirstAccess = getIntent().getExtras().getBoolean("firstAccess");
        farmConsultName = getIntent().getExtras().getString("consultFarm");
        if (farmConsultName != null)
        {
            isConsult = true;
        }
        else
        {
            isConsult = false;
        }

        farmController = new FarmController(this, this);

        mName = findViewById(R.id.nameFarmRegister);
        mLocation = findViewById(R.id.locationFarmRegister);
        mSaveButton = findViewById(R.id.saveFarmRegister);
        mAddLoot = findViewById(R.id.addLootFarmRegister);
        lootListView = findViewById(R.id.lootsListFarmRegister);

        lootListView.addOnItemTouchListener(new RecyclerItemClickListener(
                getApplicationContext(),
                lootListView,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {

                    }

                    @Override
                    public void onLongItemClick(View view, int position) {

                    }

                    @Override
                    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                    }
                }
        ));

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (!isConsult) {
                    String name = mName.getText().toString();
                    String location = mLocation.getText().toString();
                    Farm farm = new Farm(name, location);
                    farmController.saveNewFarm(farm);
                    farmController.saveNewLootFarm(farm, lootCollection.getCollection());
                }
                else
                {
                    farmController.saveNewLootFarm(consultFarm, lootCollection.getCollection());
                }


            }
        });

        mAddLoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        if(!isConsult)
        {
            lootCollection = new LootCollection();
        }
        else
        {
            consultFarm = farmController.getFarmByName(farmConsultName);
            consultFarm.setFarmLoots(farmController.getFarmsLoots(consultFarm.getId()));
            lootCollection = consultFarm.getFarmLoots();
            mName.setText(consultFarm.getName());
            mLocation.setText(consultFarm.getLocation());
            mName.setFocusable(false);
            mLocation.setFocusable(false);
            mAddLoot.setEnabled(false);
        }


        loadLootListView();
    }

    private void loadLootListView()
    {
        lootListAdapter = new LootListAdapter(lootCollection.getCollection());

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        lootListView.setLayoutManager(layoutManager);
        lootListView.setHasFixedSize(true);
        lootListView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        lootListView.setAdapter(lootListAdapter);
    }

    @Override
    public void setSaveResult(boolean result)
    {
        if (result)
        {
            finish();
        }
        else
        {
            SetToastMessageAndShow( "Unable to save content",getApplicationContext());
        }
    }

    private void showPopup()
    {
        addLootPopupBuilder = new AlertDialog.Builder(this);
        final View popupView = getLayoutInflater().inflate(R.layout.popup_add_new_loot, null);

        mLootName = popupView.findViewById(R.id.nameLootPopup);
        mBtnSaveLoot = popupView.findViewById(R.id.saveLootPopup);
        mBtnSaveLoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addLootPopup.dismiss();
                saveNewLoot();
            }
        });

        addLootPopupBuilder.setView(popupView);
        addLootPopup = addLootPopupBuilder.create();
        addLootPopup.show();
    }

    public void saveNewLoot()
    {
        String name = mLootName.getText().toString();
        int id = lootCollection.size();
        Loot loot = new Loot(id, name);

        lootCollection.add(loot);
        loadLootListView();
    }

    @Override
    public void onBackPressed() {
        if (isFirstAccess)
        {
            moveTaskToBack(true);
        }
        else
        {
            super.onBackPressed();
        }
    }
}