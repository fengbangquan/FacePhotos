package com.fengbangquan.facephoto;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbangquan.facephoto.adapter.PhotoPagerAdapter;
import com.fengbangquan.facephoto.data.MediaItem;

import java.util.List;

/**
 * Created by Feng Bangquan on 17-12-12
 */
public class PhotoPagerFragment extends Fragment {

    private ViewPager mViewPager;
    private PhotoPagerAdapter mPhotoPagerAdapter;
    private List<MediaItem> mItemsList;
    private int mCurrentPosition;
    private String mCurrentUriString;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        mCurrentPosition = bundle.getInt("vPosition", 0);
        mCurrentUriString = bundle.getString("vUriString");
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
        mPhotoPagerAdapter = new PhotoPagerAdapter(getContext(), PhotoApplication.getStoredItemsList());
        mViewPager.setAdapter(mPhotoPagerAdapter);
        mViewPager.setCurrentItem(mCurrentPosition);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
