package com.mayousheng.www.jsondecodepojo.activity;

import android.os.Bundle;
import android.util.Log;
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
        newsAdapter = new NewsAdapter(this);
        refreshRelativeLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.listView);
        listView.setAdapter(newsAdapter);
        refreshRelativeLayout.setRefreshListener(new RefreshRelativeLayout.RefreshListener() {
            @Override
            public void onDown() {

            }

            @Override
            public void onMove() {

            }

            @Override
            public void onUp() {
                newsAdapter.updateData();
                refreshRelativeLayout.endRotate();
            }
        });
    }
}
