package com.fengbangquan.facephoto.utils;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.media.FaceDetector;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DecodeFormat;
import com.bumptech.glide.request.RequestOptions;
import com.fengbangquan.facephoto.data.MediaItem;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

/**
 * Created by Feng Bangquan on 17-12-18
 */
public class FaceDetectionService extends IntentService {
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
    protected void onHandleIntent(Intent intent) {
        ArrayList<MediaItem> mMediaList = intent.getParcelableArrayListExtra("mediaItemList");
        for (int i = 0; i < mMediaList.size(); i++) {
            try {
                Bitmap bitmap = Glide
                        .with(getBaseContext())
                        .asBitmap()
                        .apply(new RequestOptions().format(DecodeFormat.PREFER_RGB_565))
                        .load(mMediaList.get(i).getUriString())
                        .submit()
                        .get();
                FaceDetector faceDetector = new FaceDetector(bitmap.getWidth(), bitmap.getHeight(), 5);
                FaceDetector.Face []faces = new FaceDetector.Face[5];

                long start = System.currentTimeMillis();
                System.out.println("get the face : " + faceDetector.findFaces(bitmap, faces));
                System.out.println("time is :" + (System.currentTimeMillis() - start));
                System.out.println("the bitmap size is : " + bitmap.getByteCount() / 1024);
                if (bitmap != null && !bitmap.isRecycled()) {
                    bitmap.recycle();
                    bitmap = null;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
        }
    }

}
