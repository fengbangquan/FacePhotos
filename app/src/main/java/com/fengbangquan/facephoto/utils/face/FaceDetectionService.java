package com.fengbangquan.facephoto.utils.face;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    public final static String FACE_SEARCH_URL = "https://api-cn.faceplusplus.com/facepp/v3/search";
    public final static String FACE_API_KEY = "api_key";
    public final static String FACE_API_SECRET = "api_secret";
    public final static String FACE_IMAGE_FILE = "image_file";
    public final static String FACE_OUTER_ID = "outer_id";
    public final static String FACE_RESULT_COUNT = "return_result_count";
    public final static String FACE_IMAGE_ID = "image_id";
    public final static String FACE_FACES = "faces";

    private int MESSAGE_GET_FACES = 0x01;
    private FaceHandler mFaceHandler;
    private OkHttpClient mOkHttpClient;

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
        mFaceHandler = new FaceHandler();
        mOkHttpClient = new OkHttpClient();
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        List<String> pathList = new ArrayList<>();
        pathList = intent.getStringArrayListExtra("faces_path");
        faceDetect("/storage/emulated/0/DCIM/Camera/IMG_1819.JPG");

    }

    private void faceDetect(final String fileString) {

        File file = new File(fileString);
        RequestBody fileBody = RequestBody.create(MultipartBody.FORM, file);
        RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart(FACE_API_KEY, FACE_API_KEY_VALUE)
                .addFormDataPart(FACE_API_SECRET, FACE_API_SECRET_VALUE)
                .addFormDataPart(FACE_IMAGE_FILE, file.getName(), fileBody)
                .addFormDataPart(FACE_OUTER_ID, "test")
                .build();
        Request request = new Request.Builder()
                .url(FACE_SEARCH_URL)
                .post(requestBody)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.code() == 200) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        int faces_count = jsonObject.getJSONArray(FACE_FACES).length();
                        if (faces_count > 0) {
                            String image_id = jsonObject.getString(FACE_IMAGE_ID);
                            Map<String, String> imagePathMap = new HashMap<>();
                            imagePathMap.put(fileString, image_id);
                            Message message = new Message();
                            message.what = MESSAGE_GET_FACES;
                            message.obj = imagePathMap;
                            mFaceHandler.sendMessage(message);
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    static class FaceHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            HashMap<String, String> map = (HashMap<String, String>) msg.obj;
            for (Map.Entry<String, String> entry : map.entrySet()) {

            }

        }
    }
}
