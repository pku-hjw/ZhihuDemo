package com.example.zhihudemo.holder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.zhihudemo.R;


/**
 * @author littlecorgi
 * @Date 2018-11-09 17:37
 * @email a1203991686@126.com
 */
public class ArticleListHolder extends RecyclerView.ViewHolder{
    public TextView articleTitle;

    public ImageView articleImage;

    public View articleView;

    public ArticleListHolder(View itemView) {
        super(itemView);
        articleView = itemView;
        articleTitle = itemView.findViewById(R.id.article_title);
        articleImage = itemView.findViewById(R.id.article_img);
    }
}
