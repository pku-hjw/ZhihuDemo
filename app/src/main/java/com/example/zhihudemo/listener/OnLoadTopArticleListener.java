package com.example.zhihudemo.listener;

import com.example.zhihudemo.bean.TopStories;

import java.util.List;

public interface OnLoadTopArticleListener {

    void onSuccess(List<TopStories> topStoriesList);

    void onFailure();
}
