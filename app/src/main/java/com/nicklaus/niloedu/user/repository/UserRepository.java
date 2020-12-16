package com.nicklaus.niloedu.user.repository;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nicklaus.niloedu.MainActivity;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.user.LoginActivity;
import com.nicklaus.niloedu.user.entity.User;
import com.nicklaus.niloedu.utils.AuthRequest;
import com.nicklaus.niloedu.utils.JsonParserUtils;
import com.nicklaus.niloedu.utils.VolleySingleton;

import org.json.JSONObject;

import java.util.Map;

import es.dmoral.toasty.Toasty;

public class UserRepository {

    private final Context ctx;
    private final static String TOKEN_KEY = "token";
    private final static String SAVE_SHP_DATA_KEY = "user_cache";
    private final static String DEFAULT_TOKEN_VALUE = "";
    private final SharedPreferences shp;


    private MutableLiveData<User> loginUserLive;

    public UserRepository(Context context){
        ctx = context;
        loginUserLive = getLoginUserLive();
        shp = ctx.getApplicationContext().getSharedPreferences(SAVE_SHP_DATA_KEY, Context.MODE_PRIVATE);
    }

    public MutableLiveData<User> getLoginUserLive() {
        if (loginUserLive == null){
            loginUserLive = new MutableLiveData<>();
        }
        return loginUserLive;
    }

    public void register(Map<String, String> registerMap, LoginActivity loginActivity){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                ctx.getResources().getString(R.string.user_register_url),
                new JSONObject(registerMap),
                response -> {
                    if (JsonParserUtils.responseIsSuccess(response)){
                        //注册成功
                        if (loginActivity != null && loginActivity.loginViewPager != null){
                            loginActivity.loginViewPager.setCurrentItem(0,true);
                        }
                        Toasty.info(ctx, R.string.toast_register_success_text, Toast.LENGTH_SHORT, true).show();
                    }else{
                        //注册失败
                        Toasty.warning(ctx, JsonParserUtils.getResponseMessage(response)).show();
                    }
                },
                error -> {
                    //访问失败
                    Toasty.warning(ctx, ctx.getResources().getString(R.string.server_network_error_message)).show();
                }

        );
        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void login(Map<String, String> loginMap, Activity currentActivity){
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                ctx.getResources().getString(R.string.user_login_url),
                new JSONObject(loginMap),
                response -> {
                    if (JsonParserUtils.responseIsSuccess(response)){
                        //将token数据写回sharedPreference中
                        String tokenString = JsonParserUtils.getDataAsObjectFromResponse(response, String.class, TOKEN_KEY);
                        SharedPreferences.Editor editor = shp.edit();
                        editor.putString(TOKEN_KEY, tokenString);
                        editor.apply();

                        //TODO:跳转到主页面
                        Toasty.success(ctx, R.string.toast_login_success_text, Toast.LENGTH_SHORT, true).show();
                        Intent intent = new Intent(currentActivity, MainActivity.class);
                        currentActivity.startActivity(intent);
                        currentActivity.finish();
                    }else{
                        Toasty.warning(ctx, JsonParserUtils.getResponseMessage(response)).show();
                    }
                },
                error -> {
                    Toasty.warning(ctx, ctx.getResources().getString(R.string.server_network_error_message)).show();
                }
        );

        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }

    public void getLoginInfo() {

        //从user_cache中读取token信息
        String tokenString = shp.getString(TOKEN_KEY, DEFAULT_TOKEN_VALUE);

        if (!tokenString.equals("")){
            AuthRequest authRequest = new AuthRequest(
                    Request.Method.GET,
                    ctx.getResources().getString(R.string.user_getLoginInfo_url),
                    null,
                    tokenString,
                    response -> {
                        if (JsonParserUtils.responseIsSuccess(response)){
                            loginUserLive.setValue(JsonParserUtils.getDataAsObjectFromResponse(response,User.class,"loginUser"));
                        }else {
                            Toasty.warning(ctx,JsonParserUtils.getResponseMessage(response)).show();
                        }
                    },
                    error -> Toasty.warning(ctx, ctx.getResources().getString(R.string.server_network_error_message)).show()

            );
            VolleySingleton.getInstance(ctx).addToRequestQueue(authRequest);
        }else{
            Toasty.warning(ctx,ctx.getResources().getString(R.string.home_please_login_first_message)).show();
        }

    }

    /**
     * 清除token缓存
     */
    public void clearUserCache(){
        SharedPreferences.Editor editor = shp.edit();
        editor.remove(TOKEN_KEY);
        editor.apply();
    }
}
