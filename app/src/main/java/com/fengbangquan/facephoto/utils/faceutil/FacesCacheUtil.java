package com.fengbangquan.facephoto.utils.faceutil;

import com.fengbangquan.facephoto.cache.Cache;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Map;

/**
 * Created by Feng Bangquan on 18-1-31
 */
public class FacesCacheUtil {

    public final static String FACES_PATH = "facesPath";
    public static synchronized JSONObject getFacesFromCache() throws JSONException {
        String jsonString = Cache.getString(FACES_PATH);
        JSONObject json;
        if (jsonString == null) {
            json = new JSONObject();
        } else {
            json = new JSONObject(jsonString);
        }
        return json;
    }

    public static synchronized void updateFacesInCache(Map<String, String> map) throws JSONException {
        JSONObject cacheJson = getFacesFromCache();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            cacheJson.put(entry.getKey(), entry.getValue());
        }
        Cache.put(FACES_PATH, cacheJson.toString());
    }

    public static synchronized void deleteFacesInCache(Map<String, String> map) throws JSONException {
        JSONObject cacheJson = getFacesFromCache();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            if (cacheJson.has(entry.getKey())) {
                cacheJson.remove(entry.getKey());
            }
        }
        Cache.put(FACES_PATH, cacheJson.toString());
    }

    public static int getFacesCount() throws JSONException {
        return getFacesFromCache().length();
    }
}
