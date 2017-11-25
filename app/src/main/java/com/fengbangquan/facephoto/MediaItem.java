package com.fengbangquan.facephoto;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * created by Feng Bangquan on 17-11-14
 */
public class MediaItem implements Parcelable {
    private int id;
    private String uriString;
    private String displayName;
    private String mimeType;
    private long size;
    private double latitude;
    private double longitude ;
    private long dateTakenInMs;
    private long dateAddedInSec;
    private String filePath;
    private String bucketName;
    private int bucketId;
    private int width;
    private int height;
    private long duration;
    private int orientation;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void setUriString(String uriString) {
        this.uriString = uriString;
    }

    public String getUriString() {
        return this.uriString;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getMimeType() {
        return this.mimeType;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public long getSize() {
        return this.size;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLatitude() {
        return this.latitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLongitude() {
        return this.longitude;
    }

    public void setDateTakenInMs(long dateTakenInMs) {
        this.dateTakenInMs = dateTakenInMs;
    }

    public long getDateTakenInMs() {
        return this.dateTakenInMs;
    }

    public void setDateAddedInSec(long dateAddedInSec) {
        this.dateAddedInSec = dateAddedInSec;
    }

    public long getDateAddedInSec() {
        return this.dateAddedInSec;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public void setBucketName(String bucketName) {
        this.bucketName = bucketName;
    }

    public String getBucketName() {
        return this.bucketName;
    }

    public void setBucketId(int bucketId) {
        this.bucketId = bucketId;
    }

    public int getBucketId() {
        return this.bucketId;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public int getWidth() {
        return this.width;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public int getHeight() {
        return this.height;
    }

    public void setDuration(long duration) {
        this.duration = duration;
    }

    public long getDuration() {
        return this.duration;
    }

    public void setOrientation(int orientation) {
        this.orientation = orientation;
    }

    public int getOrientation() {
        return this.orientation;
    }

    @Override
    public boolean equals(Object obj) {
        if ((obj != null) && (obj instanceof MediaItem)) {
            return this.filePath.equals(((MediaItem) obj).filePath);
        }
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return filePath.hashCode();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.uriString);
        dest.writeString(this.displayName);
        dest.writeString(this.mimeType);
        dest.writeLong(this.size);
        dest.writeDouble(this.latitude);
        dest.writeDouble(this.longitude);
        dest.writeLong(this.dateTakenInMs);
        dest.writeLong(this.dateAddedInSec);
        dest.writeString(this.filePath);
        dest.writeString(this.bucketName);
        dest.writeInt(this.bucketId);
        dest.writeInt(this.width);
        dest.writeInt(this.height);
        dest.writeLong(this.duration);
        dest.writeInt(this.orientation);
    }

    public MediaItem() {
    }

    protected MediaItem(Parcel in) {
        this.id = in.readInt();
        this.uriString = in.readString();
        this.displayName = in.readString();
        this.mimeType = in.readString();
        this.size = in.readLong();
        this.latitude = in.readDouble();
        this.longitude = in.readDouble();
        this.dateTakenInMs = in.readLong();
        this.dateAddedInSec = in.readLong();
        this.filePath = in.readString();
        this.bucketName = in.readString();
        this.bucketId = in.readInt();
        this.width = in.readInt();
        this.height = in.readInt();
        this.duration = in.readLong();
        this.orientation = in.readInt();
    }

    public static final Creator<MediaItem> CREATOR = new Creator<MediaItem>() {
        @Override
        public MediaItem createFromParcel(Parcel source) {
            return new MediaItem(source);
        }

        @Override
        public MediaItem[] newArray(int size) {
            return new MediaItem[size];
        }
    };
}
