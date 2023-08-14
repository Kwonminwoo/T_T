package com.example.t_t_android.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.t_t_android.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import net.daum.mf.map.api.MapPOIItem;
import net.daum.mf.map.api.MapPoint;
import net.daum.mf.map.api.MapView;

import org.w3c.dom.Text;

// 말풍선 클릭 시 리스너 (바텀시트)
public class MarkerEventListener implements MapView.POIItemEventListener{
    private BottomSheetDialog bottomSheetDialog;

    public MarkerEventListener(LayoutInflater inflater){
        View view = inflater.inflate(R.layout.fragment_bottom_sheet, null, false);
        bottomSheetDialog = new BottomSheetDialog(inflater.getContext());
        bottomSheetDialog.setContentView(view);

        // 바텀시트 닫기 버튼 구현
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

    // 말 풍선 클릭 시 - 바텀시트 보여짐
    @Override
    public void onCalloutBalloonOfPOIItemTouched(MapView mapView, MapPOIItem poiItem, MapPOIItem.CalloutBalloonButtonType buttonType) {
        bottomSheetDialog.show();

        // 바텀시트에 객체 정보 띄우기
        UserMarkerObject userMarkerObject = (UserMarkerObject) poiItem.getUserObject();

        TextView BStitle = bottomSheetDialog.findViewById(R.id.bottom_sheet_title);
        ((TextView)BStitle).setText(userMarkerObject.getArrival());

        TextView BScontent = bottomSheetDialog.findViewById(R.id.bottom_sheet_contents);
        ((TextView)BScontent).setText(userMarkerObject.getContent());

        TextView BSstartLocation = bottomSheetDialog.findViewById(R.id.bottom_sheet_start_location);
        ((TextView)BSstartLocation).setText("출발지 : 위도 " + userMarkerObject.getStartLat()
                + ", 경도 " + userMarkerObject.getStartLon());

        TextView BSendLocation = bottomSheetDialog.findViewById(R.id.bottom_sheet_end_location);
        ((TextView)BSendLocation).setText("도착지 : 위도 " + userMarkerObject.getEndLat()
                + ", 경도 " + userMarkerObject.getEndLon());

        TextView BSpeopleCntInfo = bottomSheetDialog.findViewById(R.id.bottom_sheet_people_info);
        ((TextView)BSpeopleCntInfo).setText(userMarkerObject.userInfo());
    }

    @Override
    public void onDraggablePOIItemMoved(MapView mapView, MapPOIItem mapPOIItem, MapPoint mapPOint){

    }
}
