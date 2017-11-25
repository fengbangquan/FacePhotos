package com.fengbangquan.facephoto;

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

     MediaLoader(Context context, int mediaType) {
        super(context);
        MEDIA_TYPE = mediaType;
        mContext = context;
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
                    MediaItem imageItem = new MediaItem();
                    int id = imageCursor.getInt(imageCursor.getColumnIndex(PROJECTION_IMAGES[0]));
                    imageItem.setId(id);
                    imageItem.setUriString(CONTENT_URI + "/" + id);
                    imageItem.setFilePath(imageCursor.getString(imageCursor.getColumnIndex(PROJECTION_IMAGES[1])));
                    imageItem.setDisplayName(imageCursor.getString(imageCursor.getColumnIndex(PROJECTION_IMAGES[2])));
                    imageItem.setBucketId(imageCursor.getInt(imageCursor.getColumnIndex(PROJECTION_IMAGES[3])));
                    imageItem.setBucketName(imageCursor.getString(imageCursor.getColumnIndex(PROJECTION_IMAGES[4])));
                    imageItem.setMimeType(imageCursor.getString(imageCursor.getColumnIndex(PROJECTION_IMAGES[5])));
                    imageItem.setDateAddedInSec(imageCursor.getLong(imageCursor.getColumnIndex(PROJECTION_IMAGES[6])));
                    imageItem.setDateTakenInMs(imageCursor.getLong(imageCursor.getColumnIndex(PROJECTION_IMAGES[7])));
                    imageItem.setLatitude(imageCursor.getDouble(imageCursor.getColumnIndex(PROJECTION_IMAGES[8])));
                    imageItem.setLongitude(imageCursor.getDouble(imageCursor.getColumnIndex(PROJECTION_IMAGES[9])));
                    imageItem.setSize(imageCursor.getLong(imageCursor.getColumnIndex(PROJECTION_IMAGES[10])));
                    imageItem.setOrientation(imageCursor.getInt(imageCursor.getColumnIndex(PROJECTION_IMAGES[11])));
                    imageItemList.add(imageItem);
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
                    MediaItem videoItem = new MediaItem();
                    int id = videoCursor.getInt(videoCursor.getColumnIndex(PROJECTION_VIDEOS[0]));
                    videoItem.setId(id);
                    videoItem.setUriString(CONTENT_URI + "/" + id);
                    videoItem.setFilePath(videoCursor.getString(videoCursor.getColumnIndex(PROJECTION_VIDEOS[1])));
                    videoItem.setDisplayName(videoCursor.getString(videoCursor.getColumnIndex(PROJECTION_VIDEOS[2])));
                    videoItem.setBucketId(videoCursor.getInt(videoCursor.getColumnIndex(PROJECTION_VIDEOS[3])));
                    videoItem.setBucketName(videoCursor.getString(videoCursor.getColumnIndex(PROJECTION_VIDEOS[4])));
                    videoItem.setMimeType(videoCursor.getString(videoCursor.getColumnIndex(PROJECTION_VIDEOS[5])));
                    videoItem.setDateAddedInSec(videoCursor.getLong(videoCursor.getColumnIndex(PROJECTION_VIDEOS[6])));
                    videoItem.setDateTakenInMs(videoCursor.getLong(videoCursor.getColumnIndex(PROJECTION_VIDEOS[7])));
                    videoItem.setLatitude(videoCursor.getDouble(videoCursor.getColumnIndex(PROJECTION_VIDEOS[8])));
                    videoItem.setLongitude(videoCursor.getDouble(videoCursor.getColumnIndex(PROJECTION_VIDEOS[9])));
                    videoItem.setSize(videoCursor.getLong(videoCursor.getColumnIndex(PROJECTION_VIDEOS[10])));
                    videoItem.setDuration(videoCursor.getLong(videoCursor.getColumnIndex(PROJECTION_VIDEOS[11])));
                    videoItemList.add(videoItem);
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
        forceLoad();
        registerContentObserver();
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
