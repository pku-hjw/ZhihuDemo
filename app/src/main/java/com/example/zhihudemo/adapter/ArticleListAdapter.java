package com.example.zhihudemo.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.example.zhihudemo.R;
import com.example.zhihudemo.activity.ArticleContentActivity;
import com.example.zhihudemo.bean.ArticleLatest;
import com.example.zhihudemo.bean.Stories;
import com.example.zhihudemo.bean.TopStories;
import com.example.zhihudemo.holder.ArticleListHolder;
import com.example.zhihudemo.holder.ArticleListTopHolder;
import com.example.zhihudemo.listener.OnLoadTopArticleListener;

import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

public class ArticleListAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private final int TYPE_TOP = 0;
    private final int TYPE_ARTICLE = 1;
    private final int TYPE_FOOTER = 2;
    private final int TYPE_ARTICLE_TITLE = 3;

    private List<Stories> storiesList;

    private Context context;

    private LayoutInflater inflater;

    public OnLoadTopArticleListener onLoadTopArticleListener;

    private ArticleListTopHolder articleListTopHolder;

    public ArticleListAdapter(Context context) {
        this.context = context;
        init();
    }

    private void init() {
        inflater = LayoutInflater.from(context);
        storiesList = new ArrayList<>();

        //加载banner文章事件监听
        onLoadTopArticleListener = new OnLoadTopArticleListener() {
            @Override
            public void onSuccess(List<TopStories> topStoriesList) {
                if (articleListTopHolder != null) {
                    articleListTopHolder.banner.update(topStoriesList);
                    articleListTopHolder.banner.startPlay();
                    notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure() {

            }
        };
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_TOP;
        }
        return TYPE_ARTICLE;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.d("123", "CreateViewHolder");
        View view;
        if(viewType == TYPE_ARTICLE) {
            view = inflater.inflate(R.layout.article_list_item, parent, false);
            final ArticleListHolder holder = new ArticleListHolder(view);

            holder.articleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = holder.getAdapterPosition();
//                    Toast.makeText(view.getContext(), storiesList.get(position - 1).getTitle(), Toast.LENGTH_SHORT).show();
                    int id = storiesList.get(position - 1).getId();
                    Intent intent = new Intent(context, ArticleContentActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putInt("ID", id);
                    intent.putExtras(bundle);
                    context.startActivity(intent);
                }
            });
            return holder;
        }

        view = inflater.inflate(R.layout.banner_layout, parent, false);
        final ArticleListTopHolder holder = new ArticleListTopHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Log.d("123", "bind");

        if (getItemViewType(position) == TYPE_ARTICLE) {
            ArticleListHolder articleListHolder = (ArticleListHolder) holder;
            articleListHolder.articleTitle.setText(storiesList.get(position - 1).getTitle());
            Log.d("title", storiesList.get(position - 1).getTitle());
            if (storiesList.get(position - 1).getImages() != null) {
                Glide.with(context).load(storiesList.get(position - 1).getImages().get(0)).into(articleListHolder.articleImage);
            }
        } else {
            articleListTopHolder = (ArticleListTopHolder) holder;
        }

    }

    public void setData(ArticleLatest articleLatest) {
        storiesList.clear();
        storiesList.addAll(articleLatest.getStories());
        Log.d("123", "文章数目" + storiesList.size());
    }

    @Override
    public int getItemCount() {
        Log.d("123", String.valueOf(storiesList.size()));
        return storiesList.size() + 1;
    }
}
