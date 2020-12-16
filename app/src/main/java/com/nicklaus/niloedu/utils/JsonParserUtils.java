package com.nicklaus.niloedu.utils;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonParserUtils {

    private final static String RESPONSE_TAG = "success";
    private final static String DATA_TAG = "data";

    private final static Gson gsonParser = new Gson();

    private JsonParserUtils() {
    }


    public static boolean responseIsSuccess(JSONObject response) {
        boolean flag = false;
        try {
            flag = response.getBoolean(RESPONSE_TAG);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return flag;
    }

    public static String getResponseMessage(JSONObject response) {
        String message = null;
        try {
            message = response.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return message;
    }

    public static <T> List<T> getDataAsListFromResponse(JSONObject response, Class<T> type, String realDataTag) {
        List<T> dataList = new ArrayList<>();
        try {
            String dataListJsonString = response.getJSONObject(DATA_TAG).getString(realDataTag);
            JsonArray jsonArray = JsonParser.parseString(dataListJsonString).getAsJsonArray();
            for (JsonElement jsonElement : jsonArray) {
                dataList.add(gsonParser.fromJson(jsonElement, type));
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return dataList;
    }

    public static <T> T getDataAsObjectFromResponse(JSONObject response, Class<T> type, String realDataTag) {
        String dataJsonString = null;
        try {
            dataJsonString = response.getJSONObject(DATA_TAG).getString(realDataTag);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return gsonParser.fromJson(dataJsonString, type);
    }
}
