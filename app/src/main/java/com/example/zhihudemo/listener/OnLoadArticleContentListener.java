package com.example.zhihudemo.listener;

import com.example.zhihudemo.bean.ArticleContent;

public interface OnLoadArticleContentListener {

    void onSuccess(ArticleContent content);

    void onFailure();
}
