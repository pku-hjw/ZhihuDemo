package com.example.zhihudemo.net;

import android.net.sip.SipSession;
import android.text.TextUtils;
import android.util.Log;

import com.example.zhihudemo.activity.MainActivity;
import com.example.zhihudemo.bean.ArticleContent;
import com.example.zhihudemo.bean.ArticleLatest;
import com.example.zhihudemo.holder.ArticleListTopHolder;
import com.example.zhihudemo.listener.OnLoadArticleContentListener;
import com.example.zhihudemo.listener.OnLoadLatestArticleListener;

import org.w3c.dom.ls.LSException;

import java.util.List;
import java.util.logging.Level;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtil {

    private static Retrofit retrofit = new Retrofit.Builder()
            .baseUrl(MainActivity.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    private static ArticleService service = retrofit.create(ArticleService.class);

    public static void getLatestArticleList(final OnLoadLatestArticleListener listener) {
        Call<ArticleLatest> call = service.getLatestArticles();
        call.enqueue(new Callback<ArticleLatest>() {
            @Override
            public void onResponse(Call<ArticleLatest> call, Response<ArticleLatest> response) {
                Log.d("start", "onResponse: " + response.body().toString());
                ArticleLatest articleLatest = response.body();

                Log.d("123", articleLatest.getStories().get(0).getTitle());
                if (articleLatest != null && articleLatest.getStories() != null && articleLatest.getStories().size() > 0
                        && articleLatest.getTop_stories() != null && articleLatest.getTop_stories().size() > 0) {
                    if (listener != null) {
                        Log.d("123", "listener");
                        listener.onSuccess(articleLatest);
                        return;
                    }
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ArticleLatest> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
    }

    public static void getArticleContent(int id, final OnLoadArticleContentListener listener) {
        Call<ArticleContent> call = service.getArticleDetail(id);
        call.enqueue(new Callback<ArticleContent>() {
            @Override
            public void onResponse(Call<ArticleContent> call, Response<ArticleContent> response) {
                ArticleContent content = response.body();
                if (content != null && !TextUtils.isEmpty(content.getBody())) {
                    String css = "<link rel=\"stylesheet\" href=\"file:///android_asset/src.css\" type=\"text/css\">";
                    String html = "<html><head>" + css + "</head><body>" + content.getBody() + "</body></html>";
                    html = html.replace("<div class=\"img-place-holder\">", "");
                    html = html.replace("<img class=\"content-image\"", "<img height=\"auto\"; width=\"100%\"");
                    html = html.replace("http:", "https:");
                    content.setBody(html);
                    if (listener != null) {
                        listener.onSuccess(content);
                        return;
                    }
                }
                if (listener != null) {
                    listener.onFailure();
                }
            }

            @Override
            public void onFailure(Call<ArticleContent> call, Throwable t) {
                if (listener != null) {
                    listener.onFailure();
                }
            }
        });
    }

}
