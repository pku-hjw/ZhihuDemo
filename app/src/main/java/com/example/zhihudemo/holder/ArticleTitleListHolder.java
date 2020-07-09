package com.example.zhihudemo.holder;

import android.view.View;
import android.widget.TextView;

import com.example.zhihudemo.R;

public class ArticleTitleListHolder extends ArticleListHolder {

    public TextView articleListItemTitle;

    public ArticleTitleListHolder(View itemView) {
        super(itemView);
        articleListItemTitle = itemView.findViewById(R.id.article_title);
    }

}
