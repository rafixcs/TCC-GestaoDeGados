package com.udemy.cursoandroid.gestaogados.View.main.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.udemy.cursoandroid.gestaogados.Controller.MainController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.ConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.animals.consult.IConsultAnimalController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.FarmController;
import com.udemy.cursoandroid.gestaogados.Controller.farm.IFarmController;
import com.udemy.cursoandroid.gestaogados.Controller.task.IVaccineTaskController;
import com.udemy.cursoandroid.gestaogados.Controller.task.VaccineTaskController;
import com.udemy.cursoandroid.gestaogados.Helper.RecyclerItemClickListener;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegister;
import com.udemy.cursoandroid.gestaogados.Model.AnimalRegister.AnimalRegisterStatusEnum;
import com.udemy.cursoandroid.gestaogados.Model.Farm.FarmCollection;
import com.udemy.cursoandroid.gestaogados.Model.Task.Vaccine.VaccineTask;
import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.View.ICommonView;
import com.udemy.cursoandroid.gestaogados.View.main.consult.IConsultAnimalRegisterView;
import com.udemy.cursoandroid.gestaogados.View.task.VaccineRegisterAdapter;
import com.udemy.cursoandroid.gestaogados.View.task.ITaskView;
import com.udemy.cursoandroid.gestaogados.databinding.FragmentHomeBinding;

import java.util.List;


public class HomeFragment extends Fragment implements ICommonView, ITaskView, IConsultAnimalRegisterView {

    private static final int MAX_RECYCLER_VIEW_SIZE = 5;
    private FragmentHomeBinding binding;
    private RecyclerView mRecyclerView;
    private TextView mFarmQuantityView;
    private TextView mLootQuantityView;
    private TextView mAnimalsQuantityView;
    private TextView mEmptyListText;
    private TextView mActiveAnimalsText;

    private IFarmController mFarmController;
    private FarmCollection mFarmCollection;

    private String mFarmQuantity;
    private String mLootQuantity;
    private String mAnimalsQuantity;

    private IVaccineTaskController mVaccineController;
    private List<VaccineTask> mVaccineTaskList;

    private IConsultAnimalController mConsultAnimalController;
    private List<AnimalRegister> mAnimalRegisterList;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        mEmptyListText = root.findViewById(R.id.emptyVaccineListHome);
        mFarmQuantityView = root.findViewById(R.id.farmQuantityHome);
        mLootQuantityView = root.findViewById(R.id.lootQuantityHome);
        mAnimalsQuantityView = root.findViewById(R.id.animalsQuantityHome);
        mActiveAnimalsText = root.findViewById(R.id.activeAnimalsHome);
        mRecyclerView = root.findViewById(R.id.recyclerViewVaccineHome);
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(
                getContext(),
                mRecyclerView,
                new RecyclerItemClickListener.OnItemClickListener()
                {
                    @Override
                    public void onItemClick(View view, int position)
                    {

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


        mFarmController = new FarmController(this, getContext());
        mVaccineController = new VaccineTaskController(this, getContext());
        mConsultAnimalController = new ConsultAnimalController(this, getContext());

        return root;
    }

    private void reloadRecyclerView()
    {
        mFarmCollection = new FarmCollection(mFarmController.getFarms());
        mFarmCollection.setLootCollectionToFarm(mFarmController);
        mFarmQuantity = Integer.toString(mFarmCollection.size());
        mLootQuantity = Integer.toString(mFarmController.getLootsTotalQuantity(mFarmCollection));
        mAnimalRegisterList = mConsultAnimalController.getAnimalsRegisters();
        mAnimalsQuantity = Integer.toString(mAnimalRegisterList.size());

        mLootQuantityView.setText(mLootQuantity);
        mFarmQuantityView.setText(mFarmQuantity);
        mAnimalsQuantityView.setText(mAnimalsQuantity);

        mVaccineTaskList = mVaccineController.getLatestVaccines(mFarmCollection, MAX_RECYCLER_VIEW_SIZE);

        if (mVaccineTaskList.isEmpty())
        {
            mEmptyListText.setVisibility(View.VISIBLE);
            return;
        }

        // Configure adapter
        VaccineRegisterAdapter vaccineRegisterAdapter =
                new VaccineRegisterAdapter(mVaccineTaskList, MAX_RECYCLER_VIEW_SIZE);

        // Configure recycler view
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayout.VERTICAL));
        mRecyclerView.setAdapter(vaccineRegisterAdapter);
    }

    private void reloadActiveAnimals()
    {
        int activeAnimals = getActiveAnimalQuantity();
        mActiveAnimalsText.setText(Integer.toString(activeAnimals));
    }

    private int getActiveAnimalQuantity()
    {
        int count = 0;

        for(AnimalRegister animal: mAnimalRegisterList)
        {
            if (animal.getStatus() == AnimalRegisterStatusEnum.ACTIVE)
            {
                count++;
            }
        }

        return count;
    }

    @Override
    public void onResume() {
        super.onResume();
        MainController mainController = MainController.getInstance();
        if (mainController.getCurrentUser() != null)
        {
            reloadRecyclerView();
            reloadActiveAnimals();
        }
    }

    @Override
    public void setSaveResult(boolean result)
    {

    }

    @Override
    public void setAnimalRegister(AnimalRegister animal) {

    }

    @Override
    public void onFailedConsultRegister() {

    }
}