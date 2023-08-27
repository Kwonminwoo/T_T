package com.example.t_t_android;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class RecruitmentFragment extends Fragment {
    Context context;
    MainActivity activity;
    FloatingActionButton writeBtn;

    @Override
    public void onAttach(Context context){
        super.onAttach(context);
        activity = (MainActivity) context;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_recruitment, container,false);
        context = container.getContext();

        writeBtn = root.findViewById(R.id.writeBtn);
        writeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                activity.onFragmentChange(1);
            }
        });

        return root;
    }
}