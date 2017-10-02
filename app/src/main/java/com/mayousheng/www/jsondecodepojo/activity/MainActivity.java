package com.mayousheng.www.jsondecodepojo.activity;

import android.os.Bundle;
import android.widget.ListView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.JockAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseActivity;
import com.mayousheng.www.jsondecodepojo.utils.ThreadUtils;
import com.mayousheng.www.refreshview.RefreshRelativeLayout;

public class MainActivity extends BaseActivity {

    private RefreshRelativeLayout refreshRelativeLayout;
    private ListView listView;
    private JockAdapter jockAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        refreshRelativeLayout = getViewById(R.id.refreshLayout);
        listView = getViewById(R.id.listView);
        loadView();
    }

    private void loadView() {
        jockAdapter = new JockAdapter(this, new JockAdapter.EventBus() {
            @Override
            public void refreshUI() {
                ThreadUtils.executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        MainActivity.this.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                jockAdapter.notifyDataSetChanged();
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
                });
            }
        }, listView);
        listView.setAdapter(jockAdapter);
        listView.setOnScrollListener(jockAdapter);
        refreshRelativeLayout.setRefreshListener(new RefreshRelativeLayout.RefreshListener() {
            @Override
            public void onDown() {

            }

            @Override
            public void onMove() {

            }

            @Override
            public void onUp() {
                jockAdapter.refreshData();
            }
        });
    }


}
