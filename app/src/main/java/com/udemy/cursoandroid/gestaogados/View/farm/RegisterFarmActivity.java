package com.udemy.cursoandroid.gestaogados.View.farm;

import static com.udemy.cursoandroid.gestaogados.Helper.ToastMessageHelper.SetToastMessageAndShow;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
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
import com.udemy.cursoandroid.gestaogados.R;

import java.util.ArrayList;
import java.util.List;

public class RegisterFarmActivity extends AppCompatActivity implements IRegisterFarmView
{
    private RecyclerView lootListView;
    private LootListAdapter lootListAdapter;
    private List<Loot> lootList;

    private EditText mName;
    private EditText mLocation;
    private Button mSaveButton;
    private FloatingActionButton mAddLoot;

    private AlertDialog.Builder addLootPopupBuilder;
    private AlertDialog addLootPopup;
    private EditText mLootName;
    private Button mBtnSaveLoot;

    private IFarmController farmController;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_farm);

        farmController = new FarmController(this);

        lootListView = findViewById(R.id.lootsListFarmRegister);
        mName = findViewById(R.id.nameFarmRegister);
        mLocation = findViewById(R.id.locationFarmRegister);
        mSaveButton = findViewById(R.id.saveFarmRegister);
        mAddLoot = findViewById(R.id.addLootFarmRegister);

        mSaveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = mName.getText().toString();
                String location = mLocation.getText().toString();
                Farm farm = new Farm(name, location);

                farmController.saveNewFarm(farm);
                //farmController.saveNewLootFarm(farm, );
            }
        });

        mAddLoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPopup();
            }
        });

        lootList = new ArrayList<>();

        loadLootListView();
    }

    private void loadLootListView()
    {
        lootListAdapter = new LootListAdapter(lootList);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        lootListView.setLayoutManager(layoutManager);
        lootListView.setHasFixedSize(true);
        lootListView.addItemDecoration(new DividerItemDecoration(getApplicationContext(), LinearLayout.VERTICAL));
        lootListView.setAdapter(lootListAdapter);
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
    }

    @Override
    public void setSaveResult(boolean result)
    {
        if (result)
        {
            //SetToastMessageAndShow( "Saved successfully", getApplicationContext());
            finish();
        }
        else
        {
            SetToastMessageAndShow( "Unable to save content",getApplicationContext());
        }
    }

    private void showPopup()
    {
        addLootPopupBuilder = new AlertDialog.Builder(getApplicationContext());
        final View popupView = getLayoutInflater().inflate(R.layout.popup_add_new_loot, null);

        mLootName = popupView.findViewById(R.id.nameLootPopup);
        mBtnSaveLoot = popupView.findViewById(R.id.saveLootPopup);
        mBtnSaveLoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveNewLoot();
                addLootPopup.hide();
            }
        });

        addLootPopupBuilder.setView(popupView);
        addLootPopup = addLootPopupBuilder.create();
        addLootPopup.show();
    }

    public void saveNewLoot()
    {
        String name = mLootName.getText().toString();
        String id = Integer.toString(lootList.size());
        Loot loot = new Loot(id, name);

        lootList.add(loot);
        loadLootListView();
    }
}