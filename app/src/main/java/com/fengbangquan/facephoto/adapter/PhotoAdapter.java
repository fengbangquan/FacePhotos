package com.fengbangquan.facephoto.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.fengbangquan.facephoto.R;
import com.fengbangquan.facephoto.data.MediaItem;

import java.util.List;

/**
 * created by Feng Bangquan on 17-12-11
 */
public class PhotoAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    public List<MediaItem> mMediaItemList;
    private OnItemClickListener mOnItemClickListener;
    private OnItemLongClickListener mOnItemLongClickListener;
    private Context mContext;

    public PhotoAdapter(Context context, List<MediaItem> mediaItemList) {
        mContext = context;
        mMediaItemList = mediaItemList;
    }

    public interface OnItemClickListener {
        void onItemClick(View view, int position, String uriString);
    }

    public interface OnItemLongClickListener {
        void onItemLongClickListener(View view, int position, String uriString);
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        mOnItemClickListener = onItemClickListener;
    }

    public void setOnItemLongClickListener(OnItemLongClickListener onItemLongClickListener) {
        mOnItemLongClickListener = onItemLongClickListener;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ImageViewHolder(LayoutInflater.from(mContext).inflate(R.layout.image_item_square, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        ImageView imageView = ((ImageViewHolder) holder).imageView;
        final int itemPosition = position;
        final String uriString = mMediaItemList.get(position).getUriString();
        Glide
            .with(mContext)
            .load(uriString)
            .apply(RequestOptions.placeholderOf(R.drawable.empty_photo))
            .into(imageView);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnItemClickListener.onItemClick(v, itemPosition, uriString);
            }
        });
        imageView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                mOnItemLongClickListener.onItemLongClickListener(v, itemPosition, uriString);
                return true;
            }
        });
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