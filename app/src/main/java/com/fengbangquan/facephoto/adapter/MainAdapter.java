package com.fengbangquan.facephoto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.fengbangquan.facephoto.R;
import com.fengbangquan.facephoto.data.MediaItem;

import java.util.List;

/**
 * Created by Feng Bangquan on 18-2-1
 */
public class MainAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<MediaItem> mItemList;
    private int mItemsCount = 0;
    private int mVideosCount = 0;
    private int mFacesCount = 0;
    private int mMapsCount = 0;
    public MainAdapter(Context context, List<MediaItem> list) {
        mContext = context.getApplicationContext();
        mItemList = list;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_main, parent, false));

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    private void getCountExceptFaces(List<MediaItem> list) {
        mItemsCount = list.size();
        for (int i = 0; i < mItemsCount; i++) {
            if (list.get(i).duration() > 0) mVideosCount++;
            if (list.get(i).longitude() != 0) mMapsCount++;
        }
    }

    private void getFacesCount() {

    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView imageView;
        public TextView titleText;
        public TextView countsText;

        public ItemViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_main);
            titleText = itemView.findViewById(R.id.itemTitle);
            countsText = itemView.findViewById(R.id.itemCounts);
        }
    }
}
