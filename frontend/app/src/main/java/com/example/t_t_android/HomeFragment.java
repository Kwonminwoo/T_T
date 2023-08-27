package com.example.t_t_android;

import static android.content.Context.LOCATION_SERVICE;
import static android.graphics.Color.RED;
import static androidx.core.content.ContextCompat.getSystemService;
import static net.daum.mf.map.api.MapView.MapType.Hybrid;
import static net.daum.mf.map.api.MapView.MapType.Satellite;
import static net.daum.mf.map.api.MapView.MapType.Standard;
import static net.daum.mf.map.n.api.internal.NativeMapLocationManager.isShowingCurrentLocationMarker;
import static net.daum.mf.map.n.api.internal.NativeMapLocationManager.setCustomCurrentLocationMarkerImage;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.hardware.usb.UsbRequest;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.Layout;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.t_t_android.home.CustomCalloutBalloonAdapter;
import com.example.t_t_android.home.MarkerEventListener;
import com.example.t_t_android.home.UserMarkerObject;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.CameraUpdateFactory;
import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapPointBounds;
import net.daum.mf.map.api.MapPolyline;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Text;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;
import java.util.zip.Inflater;

import com.example.t_t_android.home.UserMarkerObject;



public class HomeFragment extends Fragment implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener {
    private MapView mapView;
    private ViewGroup mapViewContainer;
    Context context;
    private MarkerEventListener eventListener;
    private int permission, permission2, permission3;

    private boolean check_result;
    FloatingActionButton my_location_Btn;

    public boolean isPermission() {
        if(permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED || permission3 == PackageManager.PERMISSION_DENIED){
            return false;
        }
        else return true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = container.getContext();

        // 권한ID를 가져옵니다
        permission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.INTERNET);

        permission2 = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);

        permission3 = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_COARSE_LOCATION);

        // 0 인 경우 : 권한 받아짐 , -1 인 경우 : 권한 안 받아짐
        Log.e("permission", String.valueOf(permission));
        Log.e("permission2", String.valueOf(permission2));
        Log.e("permission3", String.valueOf(permission3));

        // 권한이 열려있는지 확인
        if (permission == PackageManager.PERMISSION_DENIED || permission2 == PackageManager.PERMISSION_DENIED || permission3 == PackageManager.PERMISSION_DENIED) {
            // 마쉬멜로우 이상버전부터 권한을 물어본다
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                // 권한 체크(READ_PHONE_STATE의 requestCode를 1000으로 세팅
                requestPermissions(
                        new String[]{Manifest.permission.INTERNET, Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION},
                        1000);
            }
            return null;
        }

        //지도 띄우기
        mapView = new MapView(getActivity());
        mapViewContainer = (ViewGroup) root.findViewById(R.id.map_view);
        mapViewContainer.addView(mapView);
        mapView.setMapViewEventListener(this);
        mapView.setMapType(Standard);
        mapView.setHDMapTileEnabled(true);

        mapView.setCurrentLocationRadius(100);
        mapView.setCurrentLocationRadiusStrokeColor(RED);

        eventListener = new MarkerEventListener(inflater);

        // 말풍선 넣기와 마커 클릭 이벤트
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter(inflater));
        mapView.setPOIItemEventListener(eventListener);


        // 현위치로 돌아가기 버튼 my_location_Btn 구현
        my_location_Btn = root.findViewById(R.id.my_locationBtn);
        my_location_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 위치를 가져옵니다.
                LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED || ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    return;
                }
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // 현재 위치로 지도의 중심점을 이동시킵니다.
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
                }
            }
        });


        // 마커객체 활용 예시
        double startLat = 37.491046, startLon = 126.745065, endLat = 37.487639, endLon = 126.752917;
        MapPOIItem marker = new MapPOIItem();
        UserMarkerObject userMarkerObject = new UserMarkerObject("OO병원 행 택시", startLat, startLon, endLat, endLon, 1);
        userMarkerObject.setContent("00병원에 갈 사람을 모집합니다.");
        marker.setUserObject(userMarkerObject);
        marker.setItemName(userMarkerObject.getArrival());
        marker.setMapPoint(MapPoint.mapPointWithGeoCoord(userMarkerObject.getStartLat(), userMarkerObject.getStartLon()));
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin);
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin);

        mapView.addPOIItem(marker);

        return root;
    }

    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            check_result = true;

            // 모든 퍼미션을 허용했는지 체크
            for (int result : grandResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    check_result = false;
                    break;
                }
            }
            // 권한 체크 동의
            if (check_result) {
                Toast.makeText(context, "권한이 허용되었습니다.", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(context, "권한이 허용되지 않았습니다.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onCurrentLocationUpdate(MapView mapView, MapPoint mapPoint, float v) {

    }

    @Override
    public void onCurrentLocationDeviceHeadingUpdate(MapView mapView, float v) {

    }

    @Override
    public void onCurrentLocationUpdateFailed(MapView mapView) {

    }

    @Override
    public void onCurrentLocationUpdateCancelled(MapView mapView) {

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