package com.example.t_t_android;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
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
import com.example.t_t_android.login.LoginFragment;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;

public class SettingFragment extends Fragment {
    private Context context;
    private TextView nicknameTextView;
    private ImageView profileImageView;
    private Button logoutButton;
    private Button withdrawButton;
    private LoginFragment loginFragment;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_setting, container,false);
        context = container.getContext();
        nicknameTextView =root.findViewById(R.id.nicknameTextView);
        profileImageView=root.findViewById(R.id.profileImageView);
        logoutButton=root.findViewById(R.id.logoutButton);
        withdrawButton=root.findViewById(R.id.withdrawButton);
        loginFragment=new LoginFragment();

        Bundle args = getArguments();
        if (args != null) {
            String nickname = args.getString("nickname");
            String profileImageUrl = args.getString("profileImageUrl");

            // 가져온 데이터를 사용하여 UI 업데이트
            nicknameTextView.setText(nickname);
            Glide.with(requireContext())
                    .load(profileImageUrl)
                    .circleCrop()
                    .into(profileImageView);
        }

        logoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApiClient.getInstance().logout(new Function1<Throwable, Unit>() {
                    @Override
                    public Unit invoke(Throwable error) {
                        if(error!=null){
                            Log.e("LOGOUT","로그아웃 실패, SDK에서 토큰 삭제됨",error);
                            Toast.makeText(getActivity(), "로그아웃 실패, 다시 시도해주세요", Toast.LENGTH_SHORT).show();

                        }else{
                            Log.i("LOGOUT","로그아웃 성공, SDK에서 토큰 삭제됨");
                            Toast.makeText(getActivity(), "로그아웃이 성공적으로 되었습니다", Toast.LENGTH_SHORT).show();
                            loginFragment.setIsLoggedIn(false);
                            Intent intent = new Intent(context, MainActivity.class);
                            startActivity(intent);
                        }
                        return null;
                    }
                });
            }
        });
        return root;
    }

}