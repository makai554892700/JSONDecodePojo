package com.mayousheng.www.jsondecodepojo.activity;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.NewsAdapter;
import com.mayousheng.www.jsondecodepojo.adapter.NewsTypeAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseActivity;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;
import com.mayousheng.www.refreshview.RefreshRelativeLayout;

public class MainActivity extends BaseActivity {

    private RefreshRelativeLayout refreshRelativeLayout;
    private ListView listView;
    private GridView topGridView;
    private NewsAdapter newsAdapter;
    private NewsTypeAdapter newsTypeAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshRelativeLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.listView);
        topGridView = getViewById(R.id.topGridView);
        loadView();
    }

    private void loadView() {
        newsAdapter = new NewsAdapter(this, new NewsAdapter.EventBus() {
            @Override
            public void refreshUI() {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                newsAdapter.notifyDataSetChanged();
                            }
                        });
                        try {
                            Thread.sleep(1000);
                        } catch (Exception e) {
                        }
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                refreshRelativeLayout.endRotate();
                            }
                        });
                    }
                }).start();
            }
        }, listView);

        newsTypeAdapter = new NewsTypeAdapter(this, InfoUtils.getNewsTypeInfo());
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        float density = dm.density;
        int allWidth = (int) (110 * newsTypeAdapter.getCount() * density);
        int itemWidth = (int) (100 * density);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                allWidth, LinearLayout.LayoutParams.MATCH_PARENT);
        topGridView.setLayoutParams(params);
        topGridView.setColumnWidth(itemWidth);
        topGridView.setStretchMode(GridView.NO_STRETCH);
        topGridView.setNumColumns(newsTypeAdapter.getCount());
        topGridView.setAdapter(newsTypeAdapter);
        topGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                newsTypeAdapter.setSelectItem(position);
                newsTypeAdapter.notifyDataSetChanged();
                newsAdapter.changeNews(newsTypeAdapter.getItem(position).type);
            }
        });

        newsAdapter.changeNews(newsTypeAdapter.getItem(0).type);
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
            }
        });
    }


}
