package com.example.t_t_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.example.t_t_android.ImageDB.ImageDBHelper;
import com.example.t_t_android.login.LoginFragment;
import com.google.android.material.navigation.NavigationBarView;
import com.kakao.sdk.user.UserApiClient;
import com.kakao.sdk.user.model.User;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import retrofit2.http.Url;

public class MainActivity extends AppCompatActivity {
    private HomeFragment homeFragment;
    private ChatFragment chatFragment;
    private RecruitmentFragment recruitmentFragment;
    private SettingFragment settingFragment;
    private LoginFragment loginFragment;
    private FrameLayout main_container;
    private NavigationBarView navigationBarView;
    private long backpressedTime = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        main_container =findViewById(R.id.main_containers);
        navigationBarView = findViewById(R.id.bottom_navigationView);
        homeFragment=new HomeFragment();
        chatFragment= new ChatFragment();
        recruitmentFragment=new RecruitmentFragment();
        settingFragment=new SettingFragment();
        loginFragment=new LoginFragment();
        Intent intent = getIntent();
        if (loginFragment.isLoggedIn()==false) {
            Log.i("LOGIN", "로그인 안됨");
            navigationBarView.setVisibility(View.GONE);
            showLoginFragment();
        }
        else if(loginFragment.isLoggedIn()==true)
        {
            Log.i("LOGIN", "로그인 성공 후 네비게이션 바 노출");
            navigationBarView.setVisibility(View.VISIBLE);
            onLoginSuccess();

        }
    }
    private void showLoginFragment(){
        FragmentManager loginFragmentManager = getSupportFragmentManager();
        FragmentTransaction successTransaction = loginFragmentManager.beginTransaction();
        successTransaction.replace(R.id.main_containers,loginFragment).commit();
    }
    public void onLoginSuccess(){
        // LoginFragment를 제거
        FragmentManager removeFragmentManager = getSupportFragmentManager();
        FragmentTransaction removeTransaction = removeFragmentManager.beginTransaction();
        removeTransaction.remove(loginFragment).commit();

        UserApiClient.getInstance().me(new Function2<User, Throwable, Unit>() {
            @Override
            public Unit invoke(User user, Throwable error) {
                if (error != null) {
                    Log.e("USER_INFO", "사용자 정보 요청 실패", error);
                    return null;
                }
                String nickname = user.getKakaoAccount().getProfile().getNickname();
                Uri profileImageUri  = Uri.parse(user.getKakaoAccount().getProfile().getProfileImageUrl());
                Bundle userData = new Bundle();
                userData.putString("nickname", nickname);
                ImageDBHelper imageDBHelper = new ImageDBHelper(MainActivity.this);
                if(imageDBHelper.loadImageFromDatabase()==null)
                {
                    imageDBHelper.saveImageToDatabase(profileImageUri);
                }
                settingFragment.setArguments(userData);
                return null;
            }
        });
        FragmentManager loginSuccessFM=getSupportFragmentManager();
        FragmentTransaction loginSuccseeFT = loginSuccessFM.beginTransaction();
        loginSuccseeFT.replace(R.id.main_containers,homeFragment).commit();
        navigationBarView.setSelectedItemId(R.id.menu_home);
        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int itemId = item.getItemId();
                if (itemId == R.id.menu_home) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_containers, homeFragment).commit();
                    return true;
                } else if (itemId == R.id.menu_chat) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_containers, chatFragment).commit();
                    return true;
                } else if (itemId == R.id.menu_recruitment) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_containers, recruitmentFragment).commit();
                    return true;
                } else if (itemId == R.id.menu_setting) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.main_containers, settingFragment).commit();
                    return true;
                }
                return false;
            }
        });



    }
    @Override
    public void onBackPressed() {
        if (System.currentTimeMillis() > backpressedTime + 2000) {
            backpressedTime = System.currentTimeMillis();
            Toast.makeText(this, "\'뒤로\' 버튼을 한번 더 누르시면 종료됩니다.", Toast.LENGTH_SHORT).show();
        } else{
            finishAffinity();
        }

    }
}