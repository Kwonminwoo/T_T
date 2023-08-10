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
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.example.t_t_android.home.UserMarkerObject;
import com.google.android.material.navigation.NavigationBarView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapView;

import java.util.List;
import java.util.PriorityQueue;

public class WriteRecruitmentFragment extends Fragment {
    Context context;
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private PriorityQueue<MapPOIItem> mapPOIItemPriorityQueue;

    ImageButton closeBtn;
    Button completeBtn;
    HomeFragment homeFragment = new HomeFragment();
    private EditText title, starting_point, destination;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_write_recruitment, container,false);
        context = container.getContext();

        if(homeFragment.isPermission()){
            mapView = new MapView(getActivity());
            mapViewContainer = (ViewGroup) root.findViewById(R.id.write_map);
            mapViewContainer.addView(mapView);
        }

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

        title = root.findViewById(R.id.title);
        starting_point = root.findViewById(R.id.starting_point);
        destination = root.findViewById(R.id.destination);


        // UserMarkerObject(String arrival, double latitude, double longitude, int head_cnt)
        completeBtn = root.findViewById(R.id.completeBtn);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_input = null;
                double starting_point_latitude = 0, starting_point_longitude = 0, destination_latitude = 0, destination_longitude = 0;
                title_input = title.getText().toString();
                int head_cnt = 0;

                mapPOIItemPriorityQueue.offer(new MapPOIItem());
                mapPOIItemPriorityQueue.peek().setUserObject(new UserMarkerObject(title_input,
                        starting_point_latitude, starting_point_longitude, destination_latitude, destination_longitude, head_cnt));
            }
        });

        // 닫기 버튼
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