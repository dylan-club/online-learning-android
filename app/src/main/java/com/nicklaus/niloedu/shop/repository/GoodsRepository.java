package com.nicklaus.niloedu.shop.repository;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.nicklaus.niloedu.R;
import com.nicklaus.niloedu.shop.entity.Goods;
import com.nicklaus.niloedu.utils.JsonParserUtils;
import com.nicklaus.niloedu.utils.VolleySingleton;

import java.util.List;

import es.dmoral.toasty.Toasty;

public class GoodsRepository {

    private MutableLiveData<List<Goods>> goodsListLive;
    private final Context ctx;

    public GoodsRepository(Context context) {
        ctx = context;
        goodsListLive = getGoodsListLive();
    }

    public MutableLiveData<List<Goods>> getGoodsListLive() {
        if (goodsListLive == null){
            goodsListLive = new MutableLiveData<>();
        }
        return goodsListLive;
    }

    public void fetchGoodsList() {
        //向后台请求数据
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                ctx.getResources().getString(R.string.goods_list_url),
                null,
                response -> {
                    if (JsonParserUtils.responseIsSuccess(response)){
                        goodsListLive.setValue(JsonParserUtils.
                                getDataAsListFromResponse(response, Goods.class,"allGoods"));
                    }else {
                        Toasty.warning(ctx,JsonParserUtils.getResponseMessage(response)).show();
                    }
                },
                error -> Toasty.warning(ctx,ctx.getResources().getString(R.string.server_network_error_message)).show()
        );

        VolleySingleton.getInstance(ctx).addToRequestQueue(jsonObjectRequest);
    }
}
