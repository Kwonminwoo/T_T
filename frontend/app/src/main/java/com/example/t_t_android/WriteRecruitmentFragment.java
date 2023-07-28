package com.example.t_t_android;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;

import com.google.android.material.navigation.NavigationBarView;

public class WriteRecruitmentFragment extends Fragment {
    Context context;

    ImageButton closeBtn;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_write_recruitment, container,false);
        context = container.getContext();
        hideBottomNavigation(true);

        requireActivity().getOnBackPressedDispatcher().addCallback(getViewLifecycleOwner(), new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack(); // 이전 프래그먼트로 돌아갈 때 현재 프래그먼트를 백 스택에서 제거합니다.

                WriteRecruitmentFragment fragment = (WriteRecruitmentFragment) fragmentManager.findFragmentByTag("writeRecruitmentFragment");

                fragmentManager.beginTransaction()
                        .replace(R.id.main_containers, new RecruitmentFragment()) // 이전 프래그먼트로 전환합니다.
                        .commit();
            }
        });

        closeBtn = root.findViewById(R.id.closeBtn);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();

                WriteRecruitmentFragment fragment = (WriteRecruitmentFragment) fragmentManager.findFragmentByTag("writeRecruitmentFragment");

                fragmentManager.beginTransaction()
                        .replace(R.id.main_containers, new RecruitmentFragment())
                        .commit();

            }
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideBottomNavigation(false);
    }

    public void hideBottomNavigation(Boolean bool) {
        NavigationBarView navigationBarView = getActivity().findViewById(R.id.bottom_navigationView);
        if (bool)
            navigationBarView.setVisibility(View.GONE);
        else
            navigationBarView.setVisibility(View.VISIBLE);
    }
}