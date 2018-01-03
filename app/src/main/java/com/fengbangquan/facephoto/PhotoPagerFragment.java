package com.fengbangquan.facephoto;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbangquan.facephoto.adapter.PhotoPagerAdapter;
import com.fengbangquan.facephoto.data.MediaItem;
import com.fengbangquan.facephoto.data.MediaLoader;

import java.util.List;

/**
 * Created by Feng Bangquan on 17-12-12
 */
public class PhotoPagerFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MediaItem>> {

    private ViewPager mViewPager;
    private PhotoPagerAdapter mPhotoPagerAdapter;
    private List<MediaItem> mItemsList;
    private int mCurrentPosition;
    private String mCurrentUriString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        System.out.println("PhotoPagerFragment onCreate");
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mCurrentPosition = bundle.getInt("vPosition", 0);
        mCurrentUriString = bundle.getString("vUriString");
        getLoaderManager().initLoader(R.id.main_loader, null, this);
    }

    @Override
    public void onResume() {
        System.out.println("PhotoPagerFragment onResume");
        super.onResume();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo_pager, container, false);
        mViewPager = view.findViewById(R.id.view_pager_photo);
        return view;
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }

    @Override
    public Loader<List<MediaItem>> onCreateLoader(int id, Bundle args) {
        return new MediaLoader(getContext(), MediaLoader.IMAGE_AND_VIDEO);
    }

    @Override
    public void onLoadFinished(Loader<List<MediaItem>> loader, List<MediaItem> data) {
        mPhotoPagerAdapter = new PhotoPagerAdapter(getContext(), data);
        mViewPager.setAdapter(mPhotoPagerAdapter);
        mViewPager.setCurrentItem(mCurrentPosition);
    }

    @Override
    public void onLoaderReset(Loader<List<MediaItem>> loader) {

    }
}
