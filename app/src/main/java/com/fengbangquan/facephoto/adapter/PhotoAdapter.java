package com.fengbangquan.facephoto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fengbangquan.facephoto.R;
import com.fengbangquan.facephoto.data.MediaItem;

import java.util.List;

/**
 * created by Feng Bangquan on 17-12-11
 */
public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> implements
        View.OnClickListener{
    public List<MediaItem> mMediaItemList;
    public ItemOnClickListener mItemOnClickListener;
    private Context mContext;

    public PhotoAdapter(Context context, List<MediaItem> mediaItemList) {
        mContext = context;
        mMediaItemList = mediaItemList;
    }

    public interface ItemOnClickListener {
        void onItemClick(View view, int position);
    }

    public void setItemOnClickListener(ItemOnClickListener itemOnClickListener) {
        mItemOnClickListener = itemOnClickListener;
    }

    @Override
    public void onClick(View v) {
        mItemOnClickListener.onItemClick(v, (int)v.getTag());
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.image_item_square, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = ((ImageViewHolder) holder).imageView;
        Glide
            .with(mContext)
            .load(mMediaItemList.get(position).getUriString())
            .apply(RequestOptions.placeholderOf(R.drawable.empty_photo))
            .into(imageView);
        imageView.setOnClickListener(this);
        imageView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mMediaItemList.size();
    }

    private static class ImageViewHolder extends RecyclerView.ViewHolder{
        public ImageView imageView;
        public ImageViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_item);
        }

    }

}