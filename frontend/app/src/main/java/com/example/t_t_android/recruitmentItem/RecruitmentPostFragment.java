package com.example.t_t_android.recruitmentItem;

import android.content.Context;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;

import com.example.t_t_android.HomeFragment;
import com.example.t_t_android.MainActivity;
import com.example.t_t_android.R;
import com.example.t_t_android.RecruitmentFragment;
import com.example.t_t_android.WriteRecruitmentFragment;
import com.google.android.material.navigation.NavigationBarView;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

public class RecruitmentPostFragment extends Fragment implements MapView.MapViewEventListener {
    Context context;
    private MapView mapView;
    private TextView titleTv, startTv, endTv, contentTv, dateTv;
    private RecruitmentItems recruitmentItem;
    private HomeFragment homeFragment = new HomeFragment();
    private ViewGroup mapViewContainer;
    private MapPOIItem startMarker = new MapPOIItem(), endMarker = new MapPOIItem();
    private ImageButton closeBtn;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_recruitment_post, container,false);
        context = container.getContext();

        titleTv = root.findViewById(R.id.title_tv_post);
        startTv = root.findViewById(R.id.start_tv_post);
        endTv = root.findViewById(R.id.end_tv_post);
        contentTv = root.findViewById(R.id.contents_tv_post);
        dateTv = root.findViewById(R.id.date_tv);

        // 하단 navi 숨김
        hideBottomNavigation(true);

        if(getArguments() != null) {
            recruitmentItem = (RecruitmentItems) getArguments().getSerializable("recruitInfo");
            titleTv.setText(recruitmentItem.getTitle());
            startTv.setText(String.format("출발지: 위도:%.3f 경도:%.3f", recruitmentItem.getStartLon(), recruitmentItem.getStartLat()));
            endTv.setText(String.format("목적지: 위도:%.3f 경도:%.3f", recruitmentItem.getEndLon(), recruitmentItem.getEndLat()));
            contentTv.setText(recruitmentItem.getContents());
            dateTv.setText("작성된 시간 " + recruitmentItem.getTime());
        }

        // code about mapView
        if(homeFragment.isPermission()){
            mapView = new MapView(getActivity());
            mapView.setMapViewEventListener(this);
            mapViewContainer = (ViewGroup) root.findViewById(R.id.post_map);
            mapViewContainer.addView(mapView);
        }

        // add marker on the map
        startMarker.setItemName("출발지");
        startMarker.setMarkerType(MapPOIItem.MarkerType.RedPin);
        endMarker.setItemName("목적지");
        endMarker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        startMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(recruitmentItem.getStartLat(), recruitmentItem.getStartLon()));
        endMarker.setMapPoint(MapPoint.mapPointWithGeoCoord(recruitmentItem.getEndLat(), recruitmentItem.getEndLon()));

        mapView.addPOIItem(startMarker);
        mapView.addPOIItem(endMarker);

        // ScrollView
        ScrollView recruitmentPostScrollView = root.findViewById(R.id.recruitment_post_scrollView);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                int action = motionEvent.getAction();

                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        recruitmentPostScrollView.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        recruitmentPostScrollView.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        recruitmentPostScrollView.requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }
        }); // end of ScrollView

        // close btn
        closeBtn = root.findViewById(R.id.closeBtn_Post);
        closeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = requireActivity().getSupportFragmentManager();
                fragmentManager.popBackStack();

                RecruitmentPostFragment fragment = (RecruitmentPostFragment) fragmentManager.findFragmentByTag("recruitmentPostFragment");

                fragmentManager.beginTransaction().replace(R.id.main_containers, new RecruitmentFragment()).commit();
            }
        }); // end of close btn

        return root;
    }

    // 프레그먼트 벗어날 시에 하단 navi 보임
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        hideBottomNavigation(false);
    }

    // 하단 navi 숨김 함수(bool)
    public void hideBottomNavigation(Boolean bool) {
        NavigationBarView navigationBarView = getActivity().findViewById(R.id.bottom_navigationView);
        if (bool)
            navigationBarView.setVisibility(View.GONE);
        else
            navigationBarView.setVisibility(View.VISIBLE);
    }

    @Override
    public void onMapViewInitialized(MapView mapView) {

    }

    @Override
    public void onMapViewCenterPointMoved(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewZoomLevelChanged(MapView mapView, int i) {

    }

    @Override
    public void onMapViewSingleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDoubleTapped(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewLongPressed(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragStarted(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewDragEnded(MapView mapView, MapPoint mapPoint) {

    }

    @Override
    public void onMapViewMoveFinished(MapView mapView, MapPoint mapPoint) {

    }
}