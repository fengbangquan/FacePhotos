package com.fengbangquan.facephoto;

import android.app.Activity;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;

import com.baidu.location.LocationClient;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.model.LatLngBounds;
import com.fengbangquan.facephoto.data.MapMarkerItem;
import com.fengbangquan.facephoto.data.MediaItem;
import com.fengbangquan.facephoto.data.MediaLoader;
import com.fengbangquan.facephoto.utils.clusterutil.clustering.ClusterManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng Bangquan on 18-1-10
 */
public class BaiduMapActivity extends Activity implements LoaderManager.LoaderCallbacks<List<MediaItem>>,
        BaiduMap.OnMapLoadedCallback {
    MapView mMapView;
    BaiduMap mBaiduMap;
    LocationClient mLocationClient;
    List<MediaItem> itemList;
    private ClusterManager<MapMarkerItem> mClusterManager;
    private List<MapMarkerItem> mClusterList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.map_layout);
        getLoaderManager().initLoader(R.id.main_loader, null, this);

        mMapView = findViewById(R.id.mapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapLoadedCallback(this);
        mClusterManager = new ClusterManager<>(this, mBaiduMap);
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);

//        mBaiduMap.setMyLocationEnabled(true);
//        mLocationClient = new LocationClient(this);
//        LocationClientOption locationClientOption = new LocationClientOption();
//        locationClientOption.setScanSpan(1000);
//        locationClientOption.setOpenGps(true);
//        locationClientOption.setLocationNotify(true);
//        mLocationClient.setLocOption(locationClientOption);
//        mLocationClient.registerLocationListener(new BDAbstractLocationListener() {
//            @Override
//            public void onReceiveLocation(BDLocation bdLocation) {
//                MyLocationData locData = new MyLocationData.Builder()
//                        .direction(100).latitude(bdLocation.latitude())
//                        .longitude(bdLocation.longitude()).build();
//                //设置图标在地图上的位置
//                mBaiduMap.setMyLocationData(locData);
//                // 开始移动百度地图的定位地点到中心位置
//                 LatLng ll = new LatLng(bdLocation.latitude(), bdLocation.longitude());
//                 MapStatusUpdate u = MapStatusUpdateFactory.newLatLng(ll);
//                 mBaiduMap.animateMapStatus(u);
//            }
//        });
//        mLocationClient.start();

    }

    @Override
    protected void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }


    @Override
    public Loader<List<MediaItem>> onCreateLoader(int id, Bundle args) {
        return new MediaLoader(this, MediaLoader.IMAGE_ONLY);
    }

    @Override
    public void onLoadFinished(Loader<List<MediaItem>> loader, List<MediaItem> data) {
        itemList = data;
        MediaItem item;
        /*为什么通过foreach遍历地图时缩放比例达到过大？*/
        for (int i = 0; i < itemList.size(); i++) {
            item = itemList.get(i);
            if (item.latitude() != 0) {
                mClusterList.add(new MapMarkerItem(new LatLng(item.latitude(), item.longitude())));
            }
        }
        mClusterManager.addItems(mClusterList);
    }


    @Override
    public void onLoaderReset(Loader<List<MediaItem>> loader) {
    }


    @Override
    public void onMapLoaded() {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (MapMarkerItem item : mClusterList) {
            builder.include(item.getPosition());
        }
        mBaiduMap.setMapStatus(MapStatusUpdateFactory.newLatLngBounds(builder.build()));
    }
}

