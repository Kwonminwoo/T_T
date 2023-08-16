package com.example.t_t_android;

import static android.content.Context.LOCATION_SERVICE;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;

import androidx.activity.OnBackPressedCallback;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t_t_android.home.UserMarkerObject;
import com.google.android.material.navigation.NavigationBarView;

import net.daum.android.map.coord.MapCoord;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import java.util.List;
import java.util.PriorityQueue;

public class WriteRecruitmentFragment extends Fragment implements MapView.MapViewEventListener {
    Context context;
    private MapView mapView;
    private ViewGroup mapViewContainer;
    private PriorityQueue<MapPOIItem> mapPOIItemPriorityQueue;
    private Button completeBtn;
    private HomeFragment homeFragment = new HomeFragment();
    private EditText title, startEt, endEt;
    private ImageButton plusPeoPleBtn, minusPeopleBtn, eraseStartBtn, eraseEndBtn, closeBtn;
    private TextView peopleCntTv;
    private int headCnt = 1;

    private UserMarkerObject userMarkerObject;
    public MapPoint.GeoCoordinate geoCoordinate;
    public boolean startEndFlag = true; // start 커서에 true, end 커서에 false
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_write_recruitment, container,false);
        context = container.getContext();

        userMarkerObject = new UserMarkerObject();

        title = root.findViewById(R.id.title);
        startEt = root.findViewById(R.id.start_et);
        endEt = root.findViewById(R.id.end_et);

        // 위치 정보 클릭리스너 활용 startEt, endEt 클릭 리스너 (startEndFlag 활용)
        startEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startEndFlag = true;
                return false;
            }
        });

        endEt.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                startEndFlag = false;
                return false;
            }
        }); // startEt, endEt 클릭 리스너

        if(homeFragment.isPermission()){
            mapView = new MapView(getActivity());
            mapView.setMapViewEventListener(this);
            mapViewContainer = (ViewGroup) root.findViewById(R.id.write_map);
            mapViewContainer.addView(mapView);
        }

        // 하단 navi 숨김
        hideBottomNavigation(true);

        // ScrollView
        ScrollView writeRecruitmentScrollView = root.findViewById(R.id.write_recruitment_scrollView);
        mapView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent){
                int action = motionEvent.getAction();

                switch (action){
                    case MotionEvent.ACTION_DOWN:
                        writeRecruitmentScrollView.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_UP:
                        writeRecruitmentScrollView.requestDisallowInterceptTouchEvent(true);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        writeRecruitmentScrollView.requestDisallowInterceptTouchEvent(true);
                        break;
                }
                return false;
            }
        }); // end of ScrollView

        // 뒤로가기
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
        }); // end of 뒤로가기

        // closeBtn
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
        }); // end of closeBtn

        // 출발지 디폴트 값 : 현위치
        LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        if (ActivityCompat.checkSelfPermission(context, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();

                userMarkerObject.setStartLat(latitude);
                userMarkerObject.setStartLon(longitude);

                startEt.setText("현 위치 :(위도) " + latitude + " (경도) " + longitude);
            }
        } // end of 출발지 디폴트값 설정

        // eraseBtn
        eraseStartBtn = root.findViewById(R.id.erase_start_btn);
        eraseStartBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startEt.setText("");
            }
        });
        eraseEndBtn = root.findViewById(R.id.erase_end_btn);
        eraseEndBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endEt.setText("");
            }
        }); // end of eraseBtn

        // completeBtn
        completeBtn = root.findViewById(R.id.completeBtn);
        completeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title_input = null;
                double startEt_latitude = 0, startEt_longitude = 0, endEt_latitude = 0, endEt_longitude = 0;
                title_input = title.getText().toString();

                mapPOIItemPriorityQueue.offer(new MapPOIItem());
                mapPOIItemPriorityQueue.peek().setUserObject(new UserMarkerObject(title_input,
                        startEt_latitude, startEt_longitude, endEt_latitude, endEt_longitude, headCnt));
            }
        }); // end of completeBtn

        // 인원 수 체크 view
        plusPeoPleBtn = root.findViewById(R.id.plus_people_btn);
        minusPeopleBtn = root.findViewById(R.id.minus_people_btn);
        peopleCntTv = root.findViewById(R.id.people_cnt_tv);

        plusPeoPleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headCnt ++;
                if(headCnt > 4) {
                    Toast toast = Toast.makeText(getContext(), "택시 탑승 최대 인원은 4명입니다.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    headCnt --;
                }
                peopleCntTv.setText(Integer.toString(headCnt));
            }
        });

        minusPeopleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                headCnt --;
                if(headCnt < 1) {
                    Toast toast = Toast.makeText(getContext(), "택시 탑승 최소 인원은 1명입니다.",
                            Toast.LENGTH_SHORT);
                    toast.show();
                    headCnt ++;
                }
                peopleCntTv.setText(Integer.toString(headCnt));
            }
        }); // end of 인원수 체크 view

        return root;
    } // end of onCreateView()

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
        Log.e("sss", "dgsdgsdg");
        geoCoordinate = mapPoint.getMapPointGeoCoord();
        if(startEndFlag) {
                startEt.setText("(위도) " + geoCoordinate.latitude + " (경도) " + geoCoordinate.longitude);
            } else {
                endEt.setText("(위도) " + geoCoordinate.latitude + " (경도) " + geoCoordinate.longitude);
            }
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
} // end of class