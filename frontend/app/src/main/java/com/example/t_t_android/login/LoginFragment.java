package com.example.t_t_android.login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.t_t_android.MainActivity;
import com.example.t_t_android.R;
import com.kakao.sdk.auth.model.OAuthToken;
import com.kakao.sdk.common.model.ClientError;
import com.kakao.sdk.common.model.ClientErrorCause;
import com.kakao.sdk.user.UserApiClient;

import kotlin.Unit;
import kotlin.jvm.functions.Function2;

public class LoginFragment extends Fragment {
    private Context context;
    private static boolean isLoggedIn=false;
    private Button login_btn;
    public boolean isLoggedIn()
    {
        return isLoggedIn;
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root= inflater.inflate(R.layout.fragment_login, container,false);
        context = container.getContext();
        login_btn = root.findViewById(R.id.login_btn);
        Function2<OAuthToken, Throwable, Unit> callback = new Function2<OAuthToken, Throwable, Unit>() {
            @Override
            public Unit invoke(OAuthToken token, Throwable error) {
                if (error != null) {
                    Log.e("LOGIN", "카카오계정으로 로그인 실패", error);
                } else if (token != null) {
                    Log.i("LOGIN", "카카오계정으로 로그인 성공 " + token.getAccessToken());
                    isLoggedIn=true;
                }
                return null;
            }
        };
        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
// 카카오톡이 설치되어 있으면 카카오톡으로 로그인, 아니면 카카오계정으로 로그인
                if (UserApiClient.getInstance().isKakaoTalkLoginAvailable(context)) {
                    UserApiClient.getInstance().loginWithKakaoTalk(context, new Function2<OAuthToken, Throwable, Unit>() {
                        @Override
                        public Unit invoke(OAuthToken token, Throwable error) {
                            if (error != null) {
                                Log.e("LOGIN", "카카오톡으로 로그인 실패", error);

                                // 사용자가 카카오톡 설치 후 디바이스 권한 요청 화면에서 로그인을 취소한 경우,
                                // 의도적인 로그인 취소로 보고 카카오계정으로 로그인 시도 없이 로그인 취소로 처리 (예: 뒤로 가기)
                                if (error instanceof ClientError && ((ClientError) error).getReason() == ClientErrorCause.Cancelled) {
                                    return null;
                                }
                                // 카카오톡에 연결된 카카오계정이 없는 경우, 카카오계정으로 로그인 시도
                                UserApiClient.getInstance().loginWithKakaoAccount(context, callback);
                            } else if (token != null) {
                                Log.i("LOGIN", "카카오톡으로 로그인 성공 " + token.getAccessToken());
                                isLoggedIn=true;
                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                                startActivity(intent);
                            }
                            return null;
                        }
                    });
                } else {
                    UserApiClient.getInstance().loginWithKakaoAccount(context, callback);
                }
            }
        });
        return root;
    }
}
