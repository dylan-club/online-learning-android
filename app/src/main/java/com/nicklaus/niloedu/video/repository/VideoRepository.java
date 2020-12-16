package com.nicklaus.niloedu.video.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.utils.JsonParserUtils;
import com.nicklaus.niloedu.utils.VolleySingleton;
import com.nicklaus.niloedu.video.entity.Video;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class VideoRepository {

    private MutableLiveData<List<Video>> videoListLive;
    private final Context ctx;

    public VideoRepository(Context context){
        ctx = context;
        videoListLive = getVideoListLive();
    }

    public MutableLiveData<List<Video>> getVideoListLive(){
        if (videoListLive == null){
            videoListLive = new MutableLiveData<>();
        }
        return videoListLive;
    }

    public void fetchVideoList(){
        //构造Json请求
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                ctx.getResources().getString(R.string.video_list_url),
                null, response -> {
            if (JsonParserUtils.responseIsSuccess(response)) {
                videoListLive.setValue(JsonParserUtils.
                        getDataAsListFromResponse(response, Video.class, "videos"));
            } else {
                Toasty.warning(ctx, R.string.video_index_toast_server_error).show();
            }
        },
                error -> Toasty.warning(ctx, R.string.video_index_toast_network_error).show()
        );

        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }
}
