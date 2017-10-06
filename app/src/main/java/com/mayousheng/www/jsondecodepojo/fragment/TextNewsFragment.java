package com.mayousheng.www.jsondecodepojo.fragment;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.TextNewsAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsFragment;
import com.mayousheng.www.jsondecodepojo.common.ViewDesc;
import com.mayousheng.www.jsondecodepojo.utils.ThreadUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ma kai on 2017/10/4.
 */

public class TextNewsFragment extends BaseNewsFragment {

    @ViewDesc(viewId = R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @ViewDesc(viewId = R.id.recycler_view)
    RecyclerView recyclerView;
    private TextNewsAdapter textNewsAdapter;
    private List<String> data = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_news_text;
    }

    @Override
    protected void initView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, false));
        initData();
        textNewsAdapter = new TextNewsAdapter(getContext());
        textNewsAdapter.addData(data);
        recyclerView.setAdapter(textNewsAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ThreadUtils.executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(3000);
                        } catch (Exception e) {
                        }
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
    }

    private void initData() {
        String tempStr = "我去，这只是个测试";
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < 20; i++) {
            stringBuffer.append(tempStr);
            data.add(stringBuffer.toString());
        }
    }

}
