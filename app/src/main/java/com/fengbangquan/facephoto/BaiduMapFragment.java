package com.fengbangquan.facephoto;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
 * Created by Feng Bangquan on 18-1-25
 */
public class BaiduMapFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MediaItem>>,
        BaiduMap.OnMapLoadedCallback {
    private MapView mMapView;
    private BaiduMap mBaiduMap;
    private LocationClient mLocationClient;
    private List<MediaItem> mMediaItemsList;
    private ClusterManager<MapMarkerItem> mClusterManager;
    private List<MapMarkerItem> mClusterList = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(R.id.main_loader, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.map_layout, container, false);
        mMapView = view.findViewById(R.id.mapView);
        mBaiduMap = mMapView.getMap();
        mBaiduMap.setOnMapLoadedCallback(this);
        mClusterManager = new ClusterManager<>(getContext(), mBaiduMap);
        mBaiduMap.setOnMapStatusChangeListener(mClusterManager);
        return view;

    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mMapView.onDestroy();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public Loader<List<MediaItem>> onCreateLoader(int id, Bundle args) {
        return new MediaLoader(getContext(), MediaLoader.IMAGE_ONLY);
    }

    @Override
    public void onLoadFinished(Loader<List<MediaItem>> loader, List<MediaItem> data) {
        mMediaItemsList = data;
        MediaItem item;
        //为什么通过foreach遍历地图时缩放比例达到过大？
        for (int i = 0; i < mMediaItemsList.size(); i++) {
            item = mMediaItemsList.get(i);
            if (item.getLatitude() != 0) {
                mClusterList.add(new MapMarkerItem(new LatLng(item.getLatitude(), item.getLongitude())));
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
