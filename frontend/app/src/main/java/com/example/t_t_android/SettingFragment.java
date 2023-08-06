package com.example.t_t_android;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.t_t_android.ImageDB.ImageDBHelper;
import com.example.t_t_android.login.LoginFragment;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import retrofit2.http.Url;
import android.Manifest;

public class SettingFragment extends Fragment {
    private Context context;
    private TextView nicknameTextView;
    private ImageView profileImageView;
    private Button logoutButton;
    private Button withdrawButton;
    private LoginFragment loginFragment;
    private ImageDBHelper imageDBHelper;
    private int PERMISSION_REQUEST_CODE = 1;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        context = container.getContext();
        nicknameTextView = root.findViewById(R.id.nicknameTextView);
        profileImageView = root.findViewById(R.id.profileImageView);
        logoutButton = root.findViewById(R.id.logoutButton);
        withdrawButton = root.findViewById(R.id.withdrawButton);
        loginFragment = new LoginFragment();
        imageDBHelper = new ImageDBHelper(context);
        Bundle args = getArguments();
        String nickname = args.getString("nickname");
        nicknameTextView.setText(nickname);
        if (imageDBHelper.loadImageFromDatabase()!= null) {
            // 가져온 데이터를 사용하여 UI 업데이트
            Glide.with(context)
                    .load(imageDBHelper.loadImageFromDatabase())
                    .circleCrop()
                    .into(profileImageView);
        }
        profileImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (ContextCompat.checkSelfPermission(context, Manifest.permission.READ_MEDIA_IMAGES)
                        != PackageManager.PERMISSION_GRANTED) {
                    if(
                    // 권한이 허용되지 않은 경우 권한 요청
                    ActivityCompat.shouldShowRequestPermissionRationale(getActivity(), Manifest.permission.READ_CONTACTS)){

                    }else{
                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_MEDIA_IMAGES},
                                PERMISSION_REQUEST_CODE);
                    }
                } else {
                    // 권한이 이미 허용된 경우 이미지 로딩 수행
                    Log.i("db", "버튼 클릭");
                    Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                    intent.addCategory(Intent.CATEGORY_OPENABLE);
                    intent.setType("image/*");
                    launcher.launch(intent);
                }
            }
        });
        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable error) {
                        if (error != null) {
                            Log.e("LOGOUT", "로그아웃 실패, SDK에서 토큰 삭제됨", error);
                            Toast.makeText(getActivity(), "로그아웃 실패, 다시 시도해주세요", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("LOGOUT", "로그아웃 성공, SDK에서 토큰 삭제됨");
                            Toast.makeText(getActivity(), "로그아웃이 성공적으로 처리되었습니다", Toast.LENGTH_SHORT).show();
                            loginFragment.setIsLoggedIn(false);
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                        }
                        return null;
                    }
                });
            }
        });

        withdrawButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().unlink(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable error) {
                        if (error != null) {
                            Log.e("UNLINK", "연결 끊기 실패", error);
                            Toast.makeText(getActivity(), "탈퇴 실패, 다시 시도해주십시오", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.i("UNLINK", "연결 끊기 성공. SDK에서 토큰 삭제됨");
                            Toast.makeText(getActivity(), "탈퇴가 성공적으로 처리되었습니다", Toast.LENGTH_SHORT).show();
                            loginFragment.setIsLoggedIn(false);
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                            imageDBHelper.initDB();
                        }
                        return null;
                    }
                });
            }
        });

        return root;

    }

    ActivityResultLauncher<Intent> launcher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            new ActivityResultCallback<ActivityResult>() {
                @Override
                public void onActivityResult(ActivityResult result) {
                    if (result.getResultCode() == RESULT_OK) {
                        Intent intent = result.getData();
                        if (intent != null) {
                            Uri uri = intent.getData();
                            if (uri != null) {
                                imageDBHelper.saveImageToDatabase(uri);
                                Glide.with(context)
                                        .load(imageDBHelper.loadImageFromDatabase())
                                        .apply(RequestOptions.circleCropTransform())
                                        .into(profileImageView);
                            }
                        }
                    }
                }
            });

    }




