package com.example.zhihudemo.adapter;

import android.media.Image;
import android.os.IBinder;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.example.zhihudemo.listener.OnBannerClickListener;

import java.util.List;

public class BannerPagerAdapter extends PagerAdapter {

    private List<ImageView> imageViews;

    private OnBannerClickListener onBannerClickListener;

    public BannerPagerAdapter(List<ImageView> imageViews) {
        this.imageViews = imageViews;
    }

    @Override
    public int getCount() {
        return imageViews.size();
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        ImageView imageView1 = imageViews.get(position);
        container.addView(imageView1);
        imageView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (onBannerClickListener != null) {
                    onBannerClickListener.onClick(position);
                }
            }
        });
        return imageView1;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    public void setOnBannerClickListener(OnBannerClickListener onBannerClickListener) {
        this.onBannerClickListener = onBannerClickListener;
    }
}
