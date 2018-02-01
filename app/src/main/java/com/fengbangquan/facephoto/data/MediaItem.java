package com.fengbangquan.facephoto.data;


import android.os.Parcel;
import android.os.Parcelable;

/**
 * created by Feng Bangquan on 17-11-14
 */
public class MediaItem implements Parcelable {
    private int id;
    private String url;
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

    public MediaItem(Builder builder) {
        this.id = builder.id;
        this.url = builder.url;
        this.displayName = builder.displayName;
        this.mimeType = builder.mimeType;
        this.size = builder.size;
        this.latitude = builder.latitude;
        this.longitude = builder.longitude;
        this.dateTakenInMs = builder.dateTakenInMs;
        this.dateAddedInSec = builder.dateAddedInSec;
        this.filePath = builder.filePath;
        this.bucketName = builder.bucketName;
        this.bucketId = builder.bucketId;
        this.width = builder.width;
        this.height = builder.height;
        this.duration = builder.duration;
        this.orientation = builder.orientation;
    }

    public int id() {
        return this.id;
    }

    public String url() {
        return this.url;
    }

    public String displayName() {
        return this.displayName;
    }

    public String mimeType() {
        return this.mimeType;
    }

    public long size() {
        return this.size;
    }

    public double latitude() {
        return this.latitude;
    }

    public double longitude() {
        return this.longitude;
    }

    public long dateTakenInMs() {
        return this.dateTakenInMs;
    }

    public long dateAddedInSec() {
        return this.dateAddedInSec;
    }

    public String filePath() {
        return this.filePath;
    }

    public String bucketName() {
        return this.bucketName;
    }

    public int bucketId() {
        return this.bucketId;
    }

    public int width() {
        return this.width;
    }

    public int height() {
        return this.height;
    }

    public long duration() {
        return this.duration;
    }

    public int orientation() {
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
        dest.writeString(this.url);
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
        this.url = in.readString();
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

    public static class Builder {
        int id;
        String url;
        String displayName;
        String mimeType;
        long size;
        double latitude;
        double longitude;
        long dateTakenInMs;
        long dateAddedInSec;
        String filePath;
        String bucketName;
        int bucketId;
        int width;
        int height;
        long duration;
        int orientation;

        public Builder id(int id) {
            this.id = id;
            return this;
        }

        public Builder url(String url) {
            this.url = url;
            return this;
        }

        public Builder displayName(String displayName) {
            this.displayName = displayName;
            return this;
        }

        public Builder mimeType(String mimeType) {
            this.mimeType = mimeType;
            return this;
        }

        public Builder size(long size) {
            this.size = size;
            return this;
        }

        public Builder latitude(double latitude) {
            this.latitude = latitude;
            return this;
        }

        public Builder longitude(double longitude) {
            this.longitude = longitude;
            return this;
        }

        public Builder dateTakenInMs(long dateTakenInMs) {
            this.dateTakenInMs = dateTakenInMs;
            return this;
        }

        public Builder dateAddedInSec(long dateAddedInSec) {
            this.dateAddedInSec = dateAddedInSec;
            return this;
        }

        public Builder filePath(String filePath) {
            this.filePath = filePath;
            return this;
        }

        public Builder bucketName(String bucketName) {
            this.bucketName = bucketName;
            return this;
        }

        public Builder bucketId(int bucketId) {
            this.bucketId = bucketId;
            return this;
        }

        public Builder width(int width) {
            this.width = width;
            return this;
        }

        public Builder height(int height) {
            this.height = height;
            return this;
        }

        public Builder duration(long duration) {
            this.duration = duration;
            return this;
        }

        public Builder orientation(int orientation) {
            this.orientation = orientation;
            return this;
        }

        public MediaItem build() {
            if (filePath == null) throw new NullPointerException(" file path == null");
            return new MediaItem(this);
        }
    }
}
