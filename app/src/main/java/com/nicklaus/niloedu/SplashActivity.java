package com.nicklaus.niloedu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.KeyEvent;

import com.nicklaus.niloedu.BaseView.BaseActivity;
import com.nicklaus.niloedu.user.LoginActivity;

public class SplashActivity extends BaseActivity {

    private final static String TOKEN_KEY = "token";
    private final static String SAVE_SHP_DATA_KEY = "user_cache";

    @SuppressWarnings("deprecation")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //延迟加载1.5秒
        Handler handler = new Handler();
        handler.postDelayed(() -> {
            //如果在user_cache中找到token值，就跳转到主页面
            SharedPreferences shp = getApplication().getSharedPreferences(SAVE_SHP_DATA_KEY, MODE_PRIVATE);
            String token = shp.getString(TOKEN_KEY, "");
            if ("".equals(token)){
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
            }else{
                //找不到跳转到登录页面
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
            }
        },1000);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}