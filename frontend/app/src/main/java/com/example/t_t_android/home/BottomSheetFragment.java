package com.example.t_t_android.home;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.t_t_android.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import org.w3c.dom.Text;

// 바텀시트 동작은 MarkerEventListener에서 함
public class BottomSheetFragment extends Fragment {
    Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_bottom_sheet, container,false);
        context = container.getContext();
        return root;
    }
}