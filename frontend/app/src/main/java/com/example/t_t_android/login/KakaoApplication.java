package com.example.t_t_android.login;

import android.app.Application;

import com.example.t_t_android.R;
import com.kakao.sdk.common.KakaoSdk;

public class KakaoApplication extends Application {
    private static KakaoApplication instance;
    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        KakaoSdk.init(this,getString(R.string.kakao_native_app_key));
    }
}
