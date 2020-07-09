package com.example.zhihudemo.net;

import com.example.zhihudemo.bean.ArticleContent;
import com.example.zhihudemo.bean.ArticleLatest;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ArticleService {


    /**
     * 获得今日文章列表
     *
     * @return
     */
    @GET("latest")
    Call<ArticleLatest> getLatestArticles();


    /**
     * 根据文章id获得全文
     *
     * @param id 文章id
     * @return 文章内容
     */
    @GET("{id}")
    Call<ArticleContent> getArticleDetail(@Path("id") int id);
}
