package com.nicklaus.niloedu.shop.entity;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.annotation.Nullable;

public class Goods implements Parcelable {

    private String id;

    private String goodsTitle;

    private String goodsDetail;

    private String goodsImageUrl;

    private Integer goodsCredit;

    private Integer goodsAmount;

    public Goods() {}

    protected Goods(Parcel in) {
        id = in.readString();
        goodsTitle = in.readString();
        goodsDetail = in.readString();
        goodsImageUrl = in.readString();
        if (in.readByte() == 0) {
            goodsCredit = null;
        } else {
            goodsCredit = in.readInt();
        }
        if (in.readByte() == 0) {
            goodsAmount = null;
        } else {
            goodsAmount = in.readInt();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(goodsTitle);
        dest.writeString(goodsDetail);
        dest.writeString(goodsImageUrl);
        if (goodsCredit == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(goodsCredit);
        }
        if (goodsAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(goodsAmount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Goods> CREATOR = new Creator<Goods>() {
        @Override
        public Goods createFromParcel(Parcel in) {
            return new Goods(in);
        }

        @Override
        public Goods[] newArray(int size) {
            return new Goods[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGoodsTitle() {
        return goodsTitle;
    }

    public void setGoodsTitle(String goodsTitle) {
        this.goodsTitle = goodsTitle;
    }

    public String getGoodsDetail() {
        return goodsDetail;
    }

    public void setGoodsDetail(String goodsDetail) {
        this.goodsDetail = goodsDetail;
    }

    public String getGoodsImageUrl() {
        return goodsImageUrl;
    }

    public void setGoodsImageUrl(String goodsImageUrl) {
        this.goodsImageUrl = goodsImageUrl;
    }

    public Integer getGoodsCredit() {
        return goodsCredit;
    }

    public void setGoodsCredit(Integer goodsCredit) {
        this.goodsCredit = goodsCredit;
    }

    public Integer getGoodsAmount() {
        return goodsAmount;
    }

    public void setGoodsAmount(Integer goodsAmount) {
        this.goodsAmount = goodsAmount;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        return super.equals(obj);
    }
}
