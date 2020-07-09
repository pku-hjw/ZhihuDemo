package com.example.zhihudemo.activity;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import com.example.zhihudemo.R;
import com.example.zhihudemo.fragment.HomeFragment;
import com.githang.statusbar.StatusBarCompat;


import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.widget.TextView;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.NavigationUI;

public class MainActivity extends AppCompatActivity {

//    private AppBarConfiguration mAppBarConfiguration;

    private int currentId;

    private SwipeRefreshLayout refreshLayout;

    private DrawerLayout drawerLayout;

    private String date;

    private boolean isExit;

    public boolean isHomepage;

    public static String BASE_URL = "https://news-at.zhihu.com/api/4/news/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        SharedPreferences sp = getSharedPreferences("MainActivity", Context.MODE_PRIVATE);
        if (sp.contains("zhihu_article_url")) {
            BASE_URL = sp.getString("zhihu_article_url", "https://news-at.zhihu.com/api/4/news/");
        } else {
            SharedPreferences.Editor editor = sp.edit();
            editor.putString("zhihu_article_url", "https://news-at.zhihu.com/api/4/news/");
            editor.apply();
        }

        int color = Color.rgb(248, 248, 255);
        StatusBarCompat.setStatusBarColor(this, color);

        initView();
        currentId = -1;

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.title_frameLayout, new HomeFragment(), "Fragment" + currentId).commit();
        isHomepage = true;
    }

    private void initView() {

        Toolbar toolbar = findViewById(R.id.toolbar);
        TextView toolbarText = findViewById(R.id.toolbarText);
        toolbarText.setText("知乎日报");

        drawerLayout = findViewById(R.id.drawerLayout);
        refreshLayout = findViewById(R.id.refreshLayout);
        ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,
                toolbar, R.string.app_name, R.string.app_name);
        drawerLayout.addDrawerListener(drawerToggle);
        drawerToggle.syncState();

        refreshLayout.setOnRefreshListener(() -> {
            if (isHomepage) {
                HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("Fragment" + currentId);
                homeFragment.getLatestArticleList();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(drawerLayout, navController)
                || super.onSupportNavigateUp();
    }


    public void getHomepage() {
        HomeFragment homeFragment = (HomeFragment) getSupportFragmentManager().findFragmentByTag("Fragment" + "-1");
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (homeFragment == null) {
            transaction.add(R.id.title_frameLayout, new HomeFragment(), "Fragment" + "-1").commit();
        } else {
            transaction.show(homeFragment).commit();
        }
        currentId = -1;
        isHomepage = true;
        if(getSupportActionBar() != null) {
            getSupportActionBar().setTitle("知乎日报");
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawerLayout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
            return;
        }
        if (!isHomepage) {
            getHomepage();
            return;
        }
        if (isExit) {
            refreshLayout.setRefreshing(false);
            super.onBackPressed();
        } else {
            hint();
        }
    }

    private void hint() {
        Snackbar snackbar = Snackbar.make(refreshLayout, "再按一次退出", Snackbar.LENGTH_SHORT);
        snackbar.addCallback(new Snackbar.Callback() {
            @Override
            public void onDismissed(Snackbar transientBottomBar, int event) {
                isExit = false;
            }

            @Override
            public void onShown(Snackbar sb) {
                isExit = true;
            }
        }).show();
    }

    public void setRefresh(boolean flag) {
        refreshLayout.setRefreshing(flag);
    }
}
