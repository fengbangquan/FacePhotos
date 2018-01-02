package com.fengbangquan.facephoto;

import android.app.Application;

import com.fengbangquan.facephoto.data.MediaItem;

import java.util.List;

/**
 * Created by Feng Bangquan on 17-12-13
 */
public class PhotoApplication extends Application {

    private static List<MediaItem> storedItemsList;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    public static void storeItemsToNext(List<MediaItem> list) {
        if (storedItemsList != null) {
            storedItemsList.clear();
            storedItemsList = list;
        } else {
            storedItemsList = list;
        }
    }

    public static List<MediaItem> getStoredItemsList() {
        return storedItemsList;
    }
}
