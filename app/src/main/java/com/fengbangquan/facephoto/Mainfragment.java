package com.fengbangquan.facephoto;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Intent;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fengbangquan.facephoto.adapter.MainAdapter;
import com.fengbangquan.facephoto.data.MediaItem;
import com.fengbangquan.facephoto.data.MediaLoader;
import com.fengbangquan.facephoto.utils.faceutil.FaceDetectionService;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Feng Bangquan on 18-2-1
 */
public class Mainfragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MediaItem>> {
    
    private MainAdapter mMainAdapter;
    private RecyclerView mMainView;
    private List<MediaItem> mItemsList;
    private List<String> mImageList;
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        mMainView = view.findViewById(R.id.recycler_view_main);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public Loader<List<MediaItem>> onCreateLoader(int id, Bundle args) {
        return new MediaLoader(getContext(), MediaLoader.IMAGE_AND_VIDEO);
    }

    @Override
    public void onLoadFinished(Loader<List<MediaItem>> loader, List<MediaItem> data) {
        mItemsList = data;
        mImageList = new ArrayList<>();
        mMainAdapter = new MainAdapter(getContext(), mItemsList, 0);
        mMainView.setAdapter(mMainAdapter);
        for (int i = 0; i < mItemsList.size(); i++) {
            if (mItemsList.get(i).duration() == 0) {
                mImageList.add(mItemsList.get(i).filePath());
            }
        }
        if (mImageList.size() > 0) {
            Intent intent = new Intent(getActivity(), FaceDetectionService.class);
            getActivity().startService(intent);
        }
    }

    @Override
    public void onLoaderReset(Loader<List<MediaItem>> loader) {

    }
}
