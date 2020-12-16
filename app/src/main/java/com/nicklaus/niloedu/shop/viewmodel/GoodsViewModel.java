package com.nicklaus.niloedu.shop.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.nicklaus.niloedu.shop.entity.Goods;
import com.nicklaus.niloedu.shop.repository.GoodsRepository;

import java.util.List;

public class GoodsViewModel extends AndroidViewModel {

    private final GoodsRepository goodsRepository;

    public GoodsViewModel(@NonNull Application application) {
        super(application);
        this.goodsRepository = new GoodsRepository(application.getApplicationContext());
    }

    public MutableLiveData<List<Goods>> getGoodsListLive(){
        return goodsRepository.getGoodsListLive();
    }

    public void fetchGoodsList() {
        goodsRepository.fetchGoodsList();
    }
}
