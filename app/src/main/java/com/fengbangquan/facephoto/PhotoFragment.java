package com.fengbangquan.facephoto;

import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Loader;
import android.os.Bundle;
import android.support.annotation.Nullable;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.fengbangquan.facephoto.adapter.PhotoAdapter;
import com.fengbangquan.facephoto.data.MediaItem;
import com.fengbangquan.facephoto.data.MediaLoader;

import java.util.List;

/**
 * Created by Feng Bangquan on 17-12-11
 */
public class PhotoFragment extends Fragment implements LoaderManager.LoaderCallbacks<List<MediaItem>>,
        PhotoAdapter.OnItemClickListener, PhotoAdapter.OnItemLongClickListener {

    private List<MediaItem> mItemsList;
    private RecyclerView mRecyclerView;
    private PhotoAdapter mPhotoAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLoaderManager().initLoader(R.id.main_loader, null, this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_photo, container, false);
        mRecyclerView = view.findViewById(R.id.recycler_view_photo);
        return view;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        mItemsList.clear();
    }

    @Override
    public Loader<List<MediaItem>> onCreateLoader(int id, Bundle args) {
        return new MediaLoader(getContext(), MediaLoader.IMAGE_AND_VIDEO);
    }

    @Override
    public void onLoadFinished(Loader<List<MediaItem>> loader, List<MediaItem> data) {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        mRecyclerView.setLayoutManager(layoutManager);
        mItemsList = data;
        mPhotoAdapter = new PhotoAdapter(getContext(), mItemsList);
        mRecyclerView.setAdapter(mPhotoAdapter);
        mPhotoAdapter.setOnItemClickListener(this);
        mPhotoAdapter.setOnItemLongClickListener(this);
    }

    @Override
    public void onLoaderReset(Loader<List<MediaItem>> loader) {

    }

    @Override
    public void onItemClick(View view, int position, String uriString) {

    }

    @Override
    public void onItemLongClickListener(View view, int position, String uriString) {

    }
}
