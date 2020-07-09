package com.example.zhihudemo.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.zhihudemo.R;
import com.example.zhihudemo.adapter.ArticleListAdapter;
import com.example.zhihudemo.bean.ArticleLatest;
import com.example.zhihudemo.bean.TopStories;
import com.example.zhihudemo.decoration.SpaceItemDecoration;
import com.example.zhihudemo.listener.OnLoadLatestArticleListener;
import com.example.zhihudemo.net.RetrofitUtil;

import java.util.List;


public class HomeFragment extends BaseFragment {

    private RecyclerView recyclerView;

    private ArticleListAdapter adapter;

    private OnLoadLatestArticleListener latestArticleListener;

    private boolean flag;


    @Override
    protected View initView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.article_list);

        int spacingInPixels = getResources().getDimensionPixelSize(R.dimen.space);
        recyclerView.addItemDecoration(new SpaceItemDecoration(spacingInPixels));

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, LinearLayoutManager.VERTICAL, false));
        return view;
    }

    @Override
    protected void initData() {
        adapter = new ArticleListAdapter(mActivity);
        recyclerView.setAdapter(adapter);

        latestArticleListener = new OnLoadLatestArticleListener() {
            @Override
            public void onSuccess(ArticleLatest articleLatest) {
                Log.d("123", "setData");
                adapter.setData(articleLatest);
                adapter.notifyDataSetChanged();

                List<TopStories> topStories = articleLatest.getTop_stories();
                if (adapter.onLoadTopArticleListener != null) {
                    adapter.onLoadTopArticleListener.onSuccess(topStories);
                }
                stopRefresh();
                if (!flag) {
                    flag = true;
                } else {
                    Toast.makeText(mActivity, "已经是最新文章啦", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure() {

            }
        };

        getLatestArticleList();
    }

    public void getLatestArticleList() {
        RetrofitUtil.getLatestArticleList(latestArticleListener);
    }

    public void stopRefresh() {
        if (getRootActivity() != null) {
            getRootActivity().setRefresh(false);
        }
    }
}