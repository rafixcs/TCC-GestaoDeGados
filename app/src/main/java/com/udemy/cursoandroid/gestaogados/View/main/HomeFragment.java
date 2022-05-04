package com.udemy.cursoandroid.gestaogados.View.main;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.udemy.cursoandroid.gestaogados.R;
import com.udemy.cursoandroid.gestaogados.databinding.FragmentHomeBinding;


public class HomeFragment extends Fragment {

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_consult_bovine, container, false);
    }

    @Override
    public void onDestroyView()
    {
        super.onDestroyView();
    }
}