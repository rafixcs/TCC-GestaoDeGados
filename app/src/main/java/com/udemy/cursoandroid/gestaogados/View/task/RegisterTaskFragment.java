package com.udemy.cursoandroid.gestaogados.View.task;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.udemy.cursoandroid.gestaogados.R;

public class RegisterTaskFragment extends Fragment {

    public RegisterTaskFragment() {
        // Required empty public constructor
    }

    public static RegisterTaskFragment newInstance(String param1, String param2) {
        RegisterTaskFragment fragment = new RegisterTaskFragment();

        Bundle args = new Bundle();
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_task, container, false);
    }
}