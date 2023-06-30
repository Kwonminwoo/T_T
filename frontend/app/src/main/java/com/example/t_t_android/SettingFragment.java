package com.example.t_t_android;

import android.content.Context;
import android.media.Image;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

public class SettingFragment extends Fragment {
    Context context;
    private TextView nicknameTextView;
    private ImageView profileImageView;
    private Button logoutButton;
    private Button withdrawButton;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_setting, container,false);
        context = container.getContext();
        nicknameTextView =root.findViewById(R.id.nicknameTextView);
        profileImageView=root.findViewById(R.id.profileImageView);
        logoutButton=root.findViewById(R.id.logoutButton);
        withdrawButton=root.findViewById(R.id.withdrawButton);

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
        return root;
    }



}