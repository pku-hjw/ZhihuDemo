package com.example.zhihudemo.listener;

import com.example.zhihudemo.bean.ArticleLatest;

public interface OnLoadLatestArticleListener {

    void onSuccess(ArticleLatest articleLatest);

    void onFailure();
}
