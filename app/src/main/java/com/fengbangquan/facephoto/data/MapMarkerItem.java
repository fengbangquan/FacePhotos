package com.fengbangquan.facephoto.data;

import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.model.LatLng;
import com.fengbangquan.facephoto.R;
import com.fengbangquan.facephoto.utils.clusterutil.clustering.ClusterItem;

/**
 * Created by Feng Bangquan on 18-1-22
 */
public class MapMarkerItem implements ClusterItem {

    private final LatLng mLatLng;
    private final String mPath;

    public MapMarkerItem(LatLng latLng) {
        this(null, latLng);
    }

    public MapMarkerItem(String imagePath, LatLng latLng) {
        mPath = imagePath;
        mLatLng = latLng;
    }

    @Override
    public LatLng getPosition() {
        return mLatLng;
    }

    @Override
    public BitmapDescriptor getBitmapDescriptor() {
        return BitmapDescriptorFactory.fromResource(R.drawable.icon_marker);
    }
}
