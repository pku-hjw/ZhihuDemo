package com.example.zhihudemo.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.zhihudemo.R;
import com.example.zhihudemo.banner.Banner;

public class ArticleListTopHolder extends RecyclerView.ViewHolder {

    public Banner banner;

    public ArticleListTopHolder(View itemView) {
        super(itemView);
        banner = itemView.findViewById(R.id.banner);
    }
}
