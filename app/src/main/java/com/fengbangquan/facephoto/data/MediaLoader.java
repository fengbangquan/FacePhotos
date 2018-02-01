package com.fengbangquan.facephoto.data;

import android.content.AsyncTaskLoader;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;

import java.util.ArrayList;
import java.util.List;

/**
 * created by Feng Bangquan on 17-11-14
 */
public class MediaLoader extends AsyncTaskLoader<List<MediaItem>> {
    private Context mContext;
    private List<MediaItem> mMediaList = new ArrayList<>();
    private int MEDIA_TYPE;
    private boolean observerRegistered = false;
    private final ForceLoadContentObserver forceLoadContentObserver = new ForceLoadContentObserver();
    /**
     * MediaType to scan including(image, video, image and video)
     */
    public static final int IMAGE_ONLY = 1;
    public static final int VIDEO_ONLY = 2;
    public static final int IMAGE_AND_VIDEO = 3;

    /**
     * Image attribute.
     */
    private static final String[] PROJECTION_IMAGES = {
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media.MIME_TYPE,
            MediaStore.Images.Media.DATE_ADDED,
            MediaStore.Images.Media.DATE_TAKEN,
            MediaStore.Images.Media.LATITUDE,
            MediaStore.Images.Media.LONGITUDE,
            MediaStore.Images.Media.SIZE,
            MediaStore.Images.Media.ORIENTATION
    };

    /**
     * Video attribute.
     */
    private static final String[] PROJECTION_VIDEOS = {
            MediaStore.Video.Media._ID,
            MediaStore.Video.Media.DATA,
            MediaStore.Video.Media.DISPLAY_NAME,
            MediaStore.Video.Media.BUCKET_ID,
            MediaStore.Video.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Video.Media.MIME_TYPE,
            MediaStore.Video.Media.DATE_ADDED,
            MediaStore.Video.Media.DATE_TAKEN,
            MediaStore.Video.Media.LATITUDE,
            MediaStore.Video.Media.LONGITUDE,
            MediaStore.Video.Media.SIZE,
            MediaStore.Video.Media.DURATION
    };

     public MediaLoader(Context context, int mediaType) {
        super(context);
        MEDIA_TYPE = mediaType;
        mContext = context.getApplicationContext();
        onContentChanged();
    }

    private List<MediaItem> scanImage() {
        List<MediaItem> imageItemList = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri CONTENT_URI  = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
        String SORT_ORDER = MediaStore.Images.Media.DATE_TAKEN  + " DESC";
        Cursor imageCursor = contentResolver.query(
                CONTENT_URI,
                PROJECTION_IMAGES,
                null,
                null,
                SORT_ORDER
        );
        if (imageCursor != null) {
            try {
                while (imageCursor.moveToNext()) {
                    int id = imageCursor.getInt(imageCursor.getColumnIndex(PROJECTION_IMAGES[0]));
                    MediaItem.Builder imageBuilder = new MediaItem.Builder()
                            .id(id)
                            .url(CONTENT_URI + "/" + id)
                            .filePath(imageCursor.getString(imageCursor.getColumnIndex(PROJECTION_IMAGES[1])))
                            .displayName(imageCursor.getString(imageCursor.getColumnIndex(PROJECTION_IMAGES[2])))
                            .bucketId(imageCursor.getInt(imageCursor.getColumnIndex(PROJECTION_IMAGES[3])))
                            .bucketName(imageCursor.getString(imageCursor.getColumnIndex(PROJECTION_IMAGES[4])))
                            .mimeType(imageCursor.getString(imageCursor.getColumnIndex(PROJECTION_IMAGES[5])))
                            .dateAddedInSec(imageCursor.getLong(imageCursor.getColumnIndex(PROJECTION_IMAGES[6])))
                            .dateTakenInMs(imageCursor.getLong(imageCursor.getColumnIndex(PROJECTION_IMAGES[7])))
                            .latitude(imageCursor.getDouble(imageCursor.getColumnIndex(PROJECTION_IMAGES[8])))
                            .longitude(imageCursor.getDouble(imageCursor.getColumnIndex(PROJECTION_IMAGES[9])))
                            .size(imageCursor.getLong(imageCursor.getColumnIndex(PROJECTION_IMAGES[10])))
                            .orientation(imageCursor.getInt(imageCursor.getColumnIndex(PROJECTION_IMAGES[11])));
                    imageItemList.add(imageBuilder.build());
                }
                imageCursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return imageItemList;
    }

    private List<MediaItem> scanVideo() {
        List<MediaItem> videoItemList = new ArrayList<>();
        ContentResolver contentResolver = mContext.getContentResolver();
        Uri CONTENT_URI  = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
        String SORT_ORDER = MediaStore.Video.Media.DATE_TAKEN  + " DESC";
        Cursor videoCursor = contentResolver.query(
                CONTENT_URI,
                PROJECTION_VIDEOS,
                null,
                null,
                SORT_ORDER
        );
        if (videoCursor != null) {
            try {
                while (videoCursor.moveToNext()) {
                    int id = videoCursor.getInt(videoCursor.getColumnIndex(PROJECTION_VIDEOS[0]));
                    MediaItem.Builder videoBuilder = new MediaItem.Builder()
                            .id(id)
                            .url(CONTENT_URI + "/" + id)
                            .filePath(videoCursor.getString(videoCursor.getColumnIndex(PROJECTION_VIDEOS[1])))
                            .displayName(videoCursor.getString(videoCursor.getColumnIndex(PROJECTION_VIDEOS[2])))
                            .bucketId(videoCursor.getInt(videoCursor.getColumnIndex(PROJECTION_VIDEOS[3])))
                            .bucketName(videoCursor.getString(videoCursor.getColumnIndex(PROJECTION_VIDEOS[4])))
                            .mimeType(videoCursor.getString(videoCursor.getColumnIndex(PROJECTION_VIDEOS[5])))
                            .dateAddedInSec(videoCursor.getLong(videoCursor.getColumnIndex(PROJECTION_VIDEOS[6])))
                            .dateTakenInMs(videoCursor.getLong(videoCursor.getColumnIndex(PROJECTION_VIDEOS[7])))
                            .latitude(videoCursor.getDouble(videoCursor.getColumnIndex(PROJECTION_VIDEOS[8])))
                            .longitude(videoCursor.getDouble(videoCursor.getColumnIndex(PROJECTION_VIDEOS[9])))
                            .size(videoCursor.getLong(videoCursor.getColumnIndex(PROJECTION_VIDEOS[10])))
                            .duration(videoCursor.getLong(videoCursor.getColumnIndex(PROJECTION_VIDEOS[11])));
                    videoItemList.add(videoBuilder.build());
                }
                videoCursor.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return videoItemList;
    }

    @Override
    public List<MediaItem> loadInBackground() {
        switch (MEDIA_TYPE) {
            case IMAGE_ONLY:
                mMediaList.addAll(scanImage());
                break;
            case VIDEO_ONLY:
                mMediaList.addAll(scanVideo());
                break;
            case IMAGE_AND_VIDEO:
                mMediaList.addAll(scanImage());
                mMediaList.addAll(scanVideo());
                break;
            default:

        }
        return mMediaList;
    }

    @Override
    protected void onStartLoading() {
        if (takeContentChanged()) {
            forceLoad();
        }
        registerContentObserver();
    }

    @Override
    protected void onStopLoading() {
        cancelLoad();
    }

    @Override
    protected void onReset() {
        super.onReset();
        unregisterContentObserver();
    }

    @Override
    protected void onAbandon() {
        super.onAbandon();
        unregisterContentObserver();
    }

    private void registerContentObserver() {
        if (!observerRegistered) {
            ContentResolver cr = getContext().getContentResolver();
            cr.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, false,
                    forceLoadContentObserver);
            cr.registerContentObserver(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, false,
                    forceLoadContentObserver);

            observerRegistered = true;
        }
    }

    private void unregisterContentObserver() {
        if (observerRegistered) {
            observerRegistered = false;
            getContext().getContentResolver().unregisterContentObserver(forceLoadContentObserver);
        }
    }
}
