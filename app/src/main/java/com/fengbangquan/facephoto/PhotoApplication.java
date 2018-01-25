package com.fengbangquan.facephoto;

import android.app.Application;

import com.baidu.mapapi.SDKInitializer;
import com.fengbangquan.facephoto.data.MediaItem;

import java.util.List;

/**
 * Created by Feng Bangquan on 17-12-13
 */
public class PhotoApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SDKInitializer.initialize(getApplicationContext());
    }

}
