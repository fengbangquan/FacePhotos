package com.fengbangquan.facephoto.cache;

import android.graphics.Bitmap;

import java.io.IOException;

/**
 * created by Feng Bangquan on 17-11-11
 */
public interface ICache {

    void put(String key, Object value);

    void remove(String key) throws IOException;

    void clear() throws IOException;

    Object getObject(String key);

    int getInt(String key);

    long getLong(String key);

    double getDouble(String key);

    float getFloat(String key);

    boolean getBoolean(String key);

    Bitmap getBitmap(String key);

    String getString(String key);

    byte[] getBytes(String key);

}
