package com.fengbangquan.facephoto.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.fengbangquan.facephoto.data.MediaItem;
import com.fengbangquan.facephoto.R;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

/**
 * created by Feng Bangquan on 17-12-4
 */
public class PhotoPagerAdapter extends PagerAdapter {

    private List<MediaItem> mMediaItemList;
    private Context mConText;
    int mSize;

    public PhotoPagerAdapter(Context context, List<MediaItem> mediaItemList) {
        super();
        mConText = context;
        mSize = mediaItemList.size();
        mMediaItemList = mediaItemList;
    }

    @Override
    public int getCount() {
        int size = mMediaItemList.size();
        return size;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return  view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        LayoutInflater inflater = LayoutInflater.from(mConText);
        View view = inflater.inflate(R.layout.view_pager_photo, null);
        ImageView imageView = view.findViewById(R.id.view_pager_item);
        Glide
            .with(mConText)
            .load(mMediaItemList.get(position).getUriString())
            .apply(RequestOptions.placeholderOf(R.drawable.empty_photo))
            .into(imageView);
        container.addView(view);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View)object);
    }

}
