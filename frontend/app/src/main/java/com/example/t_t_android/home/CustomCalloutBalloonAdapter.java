package com.example.t_t_android.home;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.t_t_android.R;

import net.daum.mf.map.api.CalloutBalloonAdapter;
import net.daum.mf.map.api.MapPOIItem;

// 마커 클릭 시 리스너 (말풍선)
public class CustomCalloutBalloonAdapter implements CalloutBalloonAdapter {
    private final View mCalloutBalloon;
    private TextView title;
    private TextView people_cnt;

    public CustomCalloutBalloonAdapter(LayoutInflater inflater) {
        mCalloutBalloon = inflater.inflate(R.layout.balloon_layout, null);
        title = mCalloutBalloon.findViewById(R.id.ball_tv_title);
        people_cnt = mCalloutBalloon.findViewById(R.id.ball_tv_people_cnt);
    }

    // 마커 클릭 시 나오는 말풍선
    @Override
    public View getCalloutBalloon(MapPOIItem poiItem) {
        UserMarkerObject userMarkerObject = (UserMarkerObject) poiItem.getUserObject();
        ((TextView) title).setText(poiItem.getItemName());
        ((TextView) people_cnt).setText(userMarkerObject.userInfo());
        return mCalloutBalloon;
    }

    @Override
    public View getPressedCalloutBalloon(MapPOIItem poiItem) {
        return mCalloutBalloon;
    }
}
