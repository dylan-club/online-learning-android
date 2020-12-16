package com.nicklaus.niloedu.utils;

import androidx.annotation.Nullable;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class AuthRequest extends JsonObjectRequest {

    private final String token;
    private static final String TOKEN_KEY = "token";

    public AuthRequest(int method, String url, @Nullable JSONObject jsonRequest,String token, Response.Listener<JSONObject> listener, @Nullable Response.ErrorListener errorListener) {
        super(method, url, jsonRequest, listener, errorListener);
        this.token = token;
    }

    @Override
    public Map<String, String> getHeaders() {
        //将token信息放入http的header中
        Map<String, String> headers = new HashMap<>();
        headers.put(TOKEN_KEY, token);
        return headers;
    }
}
