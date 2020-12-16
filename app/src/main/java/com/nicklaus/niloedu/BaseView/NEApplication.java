package com.nicklaus.niloedu.BaseView;

import android.app.Application;
import android.graphics.Typeface;

import java.lang.reflect.Field;

import es.dmoral.toasty.Toasty;

public class NEApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        initTypeface();
        initToasty();
    }

    private void initToasty() {
        //初始化提示信息
        Toasty.Config.getInstance()
            .setToastTypeface(Typeface.createFromAsset(getAssets(), "fonts/cartoonfonts.ttf"))
            .apply();
    }

    private void initTypeface() {

        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/cartoonfonts.ttf");

        try {
            Field field = Typeface.class.getDeclaredField("MONOSPACE");
            field.setAccessible(true);
            field.set(null, typeface);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
