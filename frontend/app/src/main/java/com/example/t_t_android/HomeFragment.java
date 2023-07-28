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
import java.util.Map;
import java.util.zip.Inflater;



public class HomeFragment extends Fragment implements MapView.CurrentLocationEventListener, MapView.MapViewEventListener {
    private MapView mapView;
    private ViewGroup mapViewContainer;
    Context context;
    private MarkerEventListener eventListener;

    FloatingActionButton my_location_Btn;

    //boolean isFirstRun = true;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);
        context = container.getContext();

        // 권한ID를 가져옵니다
        int permission = ContextCompat.checkSelfPermission(context,
                Manifest.permission.INTERNET);

        int permission2 = ContextCompat.checkSelfPermission(context,
                Manifest.permission.ACCESS_FINE_LOCATION);

        int permission3 = ContextCompat.checkSelfPermission(context,
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

        //mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);

        eventListener = new MarkerEventListener(inflater);

        // 말풍선 넣기와 마커 클릭 이벤트
        mapView.setCalloutBalloonAdapter(new CustomCalloutBalloonAdapter(inflater));
        mapView.setPOIItemEventListener(eventListener);


        root.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // 사용자가 지도를 터치하면 트래킹 모드를 해제합니다.
                Log.e("이거", "안 되는건가??");
                mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOff);
                return true;
            }
        });


        // 현위치로 돌아가기 버튼
        my_location_Btn = root.findViewById(R.id.my_locationBtn);
        my_location_Btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // 현재 위치를 가져옵니다.
                LocationManager lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
                if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
                if (location != null) {
                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    // 현재 위치로 지도의 중심점을 이동시킵니다.
                    mapView.setMapCenterPoint(MapPoint.mapPointWithGeoCoord(latitude, longitude), true);
                    //mapView.setCurrentLocationTrackingMode(MapView.CurrentLocationTrackingMode.TrackingModeOnWithoutHeading);
                }
            }
        });

        // MapPoint 객체 , MapPOIItem 객체 로 위치 찍기
        // MapPoint에는 위도와 경도를 받아옴
        MapPoint MARKER_POINT = MapPoint.mapPointWithGeoCoord(37.491046,126.745065);
        // MapPOIItem으로는 지도에 마커를 찍어줌
        MapPOIItem marker = new MapPOIItem();
        marker.setItemName("OO병원 행 택시");
        marker.setTag(0);
        marker.setMapPoint(MARKER_POINT);
        marker.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker);


        MapPoint MARKER_POINT2 = MapPoint.mapPointWithGeoCoord(37.487639,126.752917);
        MapPOIItem marker2 = new MapPOIItem();
        marker2.setItemName("OO역 행 택시");
        marker2.setTag(1);
        marker2.setMapPoint(MARKER_POINT2);
        marker2.setMarkerType(MapPOIItem.MarkerType.BluePin); // 기본으로 제공하는 BluePin 마커 모양.
        marker2.setSelectedMarkerType(MapPOIItem.MarkerType.RedPin); // 마커를 클릭했을때, 기본으로 제공하는 RedPin 마커 모양.

        mapView.addPOIItem(marker2);

        return root;
    }

    // 권한 체크 이후로직
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grandResults) {
        // READ_PHONE_STATE의 권한 체크 결과를 불러온다
        super.onRequestPermissionsResult(requestCode, permissions, grandResults);
        if (requestCode == 1000) {
            boolean check_result = true;

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

    // MapPOIItem 클래스를 상속받은 클래스인데 밑에 함수에서 오버라이딩이 안 돼서 일단 보류함
    // 오버라이딩 시에 매개변수는 MapPOIItem을 써야하는데 CustomMapPOIItem을 썼기 때문 -> 어떻게 해결하지..
    class CustomMapPOIItem extends MapPOIItem {
        private int people_cnt = 0;

        public int getPeople_cnt(){
            return people_cnt;
        }

        public void setPeople_cnt(int people_cnt){
            this.people_cnt = people_cnt;
        }
    }

    // 말풍선 클래스
    class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
        private final View mCalloutBalloon;
        private TextView title;
        private TextView people_cnt;

        //인원수 - 여기에 하면 안 될거 같고 후에 MapPOIItem 객체 상속받아서 메서드를 추가해야 할 것 같기도
        private int n = 0;
        private String people_info = "인원 " + getN() + "/4";

        public CustomCalloutBalloonAdapter(LayoutInflater inflater) {
            mCalloutBalloon = inflater.inflate(R.layout.balloon_layout, null);
            title = mCalloutBalloon.findViewById(R.id.ball_tv_title);
            people_cnt = mCalloutBalloon.findViewById(R.id.ball_tv_people_cnt);
        }

        public void setN(int n) {
            this.n = n;
        }

        public int getN() {
            return n;
        }

        // 마커 클릭 시 나오는 말풍선
        @Override
        public View getCalloutBalloon(MapPOIItem poiItem) {
            ((TextView) title).setText(poiItem.getItemName());
            ((TextView) people_cnt).setText(people_info);
            return mCalloutBalloon;
        }

        @Override
        public View getPressedCalloutBalloon(MapPOIItem poiItem) {
            return mCalloutBalloon;
        }
    }

    //마커 클릭 이벤트 리스너
    class MarkerEventListener implements MapView.POIItemEventListener {
        private BottomSheetDialog bottomSheetDialog;
        private int n = 0;

        public MarkerEventListener(LayoutInflater inflater){
            View view = inflater.inflate(R.layout.fragment_bottom_sheet, null, false);
            bottomSheetDialog = new BottomSheetDialog(getContext());
            bottomSheetDialog.setContentView(view);

            Button btnClose = view.findViewById(R.id.btnClose);
            btnClose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    bottomSheetDialog.dismiss();
                }
            });
        }

        @Override
        public void onPOIItemSelected(MapView mapView, MapPOIItem poiItem) {

        }

        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem poiItem) {

        }

        public void setN(int n) {
            this.n = n;
        }

        public int getN() {
            return n;
        }

        // 말 풍선 클릭 시 - 바텀시트 보여짐
        @Override
        public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem poiItem, MapPOIItem.CalloutBalloonButtonType buttonType) {
            bottomSheetDialog.show();
            TextView titleTextView = bottomSheetDialog.findViewById(R.id.bottom_sheet_title);
            ((TextView)titleTextView).setText(poiItem.getItemName());
            TextView infoTextView = bottomSheetDialog.findViewById(R.id.bottom_sheet_info);
            ((TextView)infoTextView).setText("인원 " + getN() + "/4");
        }

        @Override
        public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPOint){

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