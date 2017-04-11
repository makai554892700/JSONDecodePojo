package com.mayousheng.www.jsondecodepojo;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.ListView;

import com.mayousheng.www.jsondecodepojo.adapter.NewsAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseActivity;
import com.mayousheng.www.jsondecodepojo.pojo.NewsPojo;
import com.mayousheng.www.jsondecodepojo.utils.ArrayListBack;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;
import com.mayousheng.www.refreshview.RefreshRelativeLayout;

import java.util.ArrayList;

public class MainActivity extends BaseActivity {

    private RefreshRelativeLayout refreshRelativeLayout;
    private ListView listView;
    private NewsAdapter newsAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        initView();
        initEvent();
    }

    private void updateData() {
        InfoUtils.getNewsInfo("wxnew", 0, 10, new ArrayListBack<NewsPojo>() {
            @Override
            public void onFail(int status, String message) {
                Log.e("-----1", "status=" + status + ";message=" + message);
            }

            @Override
            public void onResult(final ArrayList<NewsPojo> data) {
                if (data != null) {
                    newsAdapter.updateData(MainActivity.this, data);
                }
                Log.e("-----1", "data=" + (data == null ? "null" : data));
            }
        });
    }

    private void initData() {
        newsAdapter = new NewsAdapter(this, null);
        updateData();
    }

    private void initView() {
        refreshRelativeLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.listView);
        listView.setAdapter(newsAdapter);
    }

    private void initEvent() {
        refreshRelativeLayout.setRefreshListener(new RefreshRelativeLayout.RefreshListener() {
            @Override
            public void onDown() {

            }

            @Override
            public void onMove() {

            }

            @Override
            public void onUp() {
                refreshRelativeLayout.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refreshRelativeLayout.endRotate();
                    }
                }, 3000);
            }
        });
    }
}
