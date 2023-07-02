package com.example.t_t_android;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Build;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;
import android.view.ViewGroup;

import com.google.android.material.navigation.NavigationBarView;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    HomeFragment homeFragment;
    ChatFragment chatFragment;
    RecruitmentFragment recruitmentFragment;
    SettingFragment settingFragment;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent intent = getIntent();
        homeFragment = new HomeFragment();
        chatFragment = new ChatFragment();
        recruitmentFragment = new RecruitmentFragment();
        settingFragment = new SettingFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.main_containers, homeFragment).commit();
        NavigationBarView navigationBarView = findViewById(R.id.bottom_navigationView);
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
}