package com.fengbangquan.facephoto.utils.faceutil;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.fengbangquan.facephoto.utils.Constants;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Feng Bangquan on 17-12-18
 */
public class FaceDetectionService extends IntentService {
    public final static String FACE_API_KEY_VALUE = "lkW14rDTiqwNiPnzs8JNy8aqxxY9KUsi";
    public final static String FACE_API_SECRET_VALUE = "jxHtMc6pb_4PsBNuQtR2m6Unw-evYh6W";
    public final static String FACE_OUTER_ID_VALUE = "test";
    public final static String FACE_SEARCH_URL = "https://api-cn.faceplusplus.com/facepp/v3/search";
    public final static String FACE_API_KEY = "api_key";
    public final static String FACE_API_SECRET = "api_secret";
    public final static String FACE_IMAGE_FILE = "image_file";
    public final static String FACE_OUTER_ID = "outer_id";
    public final static String FACE_RESULT_COUNT = "return_result_count";
    public final static String FACE_IMAGE_ID = "image_id";
    public final static String FACE_FACES = "faces";

    private static Map<String, String> mFacesPathMap;
    private static final int MESSAGE_DONE = 0x01;


    private OkHttpClient mOkHttpClient;
    private FaceHandler mHandler;
    private List<String> mPathList;

    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public FaceDetectionService(String name) {
        super(name);
    }
    public FaceDetectionService(){
        super("faceDetectionService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mOkHttpClient = new OkHttpClient();
        mHandler = new FaceHandler();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        mFacesPathMap = new HashMap<>();
        mPathList = intent.getStringArrayListExtra(Constants.DETECTION_LIST);
        for (int i = 0; i < mPathList.size(); i++) {
            faceDetect(mPathList.get(i));
        }
    }

    private void faceDetect(final String path) {
        final File file = new File(path);
        RequestBody fileBody = RequestBody.create(MultipartBody.FORM, file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(FACE_API_KEY, FACE_API_KEY_VALUE)
                .addFormDataPart(FACE_API_SECRET, FACE_API_SECRET_VALUE)
                .addFormDataPart(FACE_IMAGE_FILE, file.getName(), fileBody)
                .addFormDataPart(FACE_OUTER_ID, FACE_OUTER_ID_VALUE)
                .build();
        Request request = new Request.Builder()
                .url(FACE_SEARCH_URL)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                if (path.equals(mPathList.get(mPathList.size() - 1))) {
                    mHandler.sendEmptyMessage(MESSAGE_DONE);
                }
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                try {
                    if (response.code() == 200) {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int facesCount = jsonObject.getJSONArray(FACE_FACES).length();
                        if (facesCount > 0) {
                            String image_id = jsonObject.getString(FACE_IMAGE_ID);
                            mFacesPathMap.put(path, image_id);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } finally {
                    if (path.equals(mPathList.get(mPathList.size() - 1))) {
                        mHandler.sendEmptyMessage(MESSAGE_DONE);
                    }
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mHandler.removeMessages(MESSAGE_DONE);
    }

    static class FaceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MESSAGE_DONE:
                    try {
                        FacesCacheUtil.updateFacesInCache(mFacesPathMap);
                        mFacesPathMap.clear();
                        mFacesPathMap = null;
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
