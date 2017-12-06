package com.fengbangquan.facephoto.cache;

import android.graphics.Bitmap;
import android.util.LruCache;

/**
 * This class uses {@link LruCache} to cache data and does not allow null
 * to be used as a key or value. The more information can be found on {@link LruCache}.
 *
 * Created by Feng Bangquan on 17-11-11
 */
public class MemoryCache implements ICache {
    private LruCache<Object, Object> mLruCache;

    /**
     * @param maxMemorySize this is the maximum sum of the sizes of the entries
     * in this cache.
     */
    public MemoryCache(int maxMemorySize){
        mLruCache = new LruCache<>(maxMemorySize);
    }

    /**
     * Caches {@code value} for {@code key} calling {@link LruCache#put(Object, Object)}
     */
    @Override
    public void put(String key, Object value) {
        mLruCache.put(key, value);
    }

    /**
     * Removes the entry for {@code key} if it exists.
     */
    @Override
    public void remove(String key) {
        mLruCache.remove(key);
    }

    /**
     * Clear the MemoryCache, calling {@link LruCache#evictAll()}
     */
    @Override
    public void clear() {
        mLruCache.evictAll();
    }

    /**
     * Returns the value for {@code key} if it exists in the cache .
     * The following methods call {@link #getObject(String)}
     * to get the specific types' value:
     * @see #getInt(String)
     * @see #getBitmap(String)
     * @see #getBoolean(String)
     * @see #getBytes(String)
     * @see #getDouble(String)
     * @see #getFloat(String)
     * @see #getLong(String)
     * @see #getString(String)
     */
    @Override
    public Object getObject(String key) {
        return mLruCache.get(key);
    }

    @Override
    public int getInt(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (int) object;
    }

    @Override
    public long getLong(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (long) object;
    }

    @Override
    public double getDouble(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (double) object;
    }

    @Override
    public float getFloat(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (float) object;
    }

    @Override
    public boolean getBoolean(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (boolean) object;
    }

    @Override
    public Bitmap getBitmap(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (Bitmap) object;
    }

    @Override
    public String getString(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : String.valueOf(object);
    }

    @Override
    public byte[] getBytes(String key) {
        Object object = mLruCache.get(key);
        return object == null ? null : (byte[]) object;
    }

}
