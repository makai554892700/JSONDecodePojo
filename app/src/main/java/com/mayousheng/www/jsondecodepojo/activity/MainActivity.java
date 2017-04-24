package com.mayousheng.www.jsondecodepojo.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.NewsAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseActivity;
import com.mayousheng.www.refreshview.RefreshRelativeLayout;

public class MainActivity extends BaseActivity {

    private RefreshRelativeLayout refreshRelativeLayout;
    private ListView listView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshRelativeLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.listView);
        newsAdapter = new NewsAdapter(this, new NewsAdapter.EventBus() {
            @Override
            public void refreshUI(final boolean refreshImg) {
                MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        newsAdapter.updateData(refreshImg);
                    }
                });
            }
        }, listView);
        listView.setAdapter(newsAdapter);
        listView.setOnScrollListener(newsAdapter);
        refreshRelativeLayout.setRefreshListener(new RefreshRelativeLayout.RefreshListener() {
            @Override
            public void onDown() {

            }

            @Override
            public void onMove() {

            }

            @Override
            public void onUp() {
                newsAdapter.refreshData();
                refreshRelativeLayout.endRotate();
            }
        });
    }
}
