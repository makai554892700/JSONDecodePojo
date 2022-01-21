package com.mayousheng.www.recyclerutils;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.recyclerutils.utils.OnLoadMoreListener;
import com.mayousheng.www.recyclerutils.utils.ThreadUtils;

import java.util.ArrayList;

public abstract class BaseRecyclerActivity<T extends BasePoJo> extends BaseActivity {

    public SwipeRefreshLayout swipeRefreshLayout;
    public RecyclerView recyclerView;
    protected BaseRecyclerAdapter<T> recyclerAdapter;
    protected LinearLayoutManager linearLayoutManager;
    protected OnLoadMoreListener onLoadMoreListener;
    public static final RecyclerView.ItemDecoration DEFAULT_ITEM_DECORATION = new RecyclerView.ItemDecoration() {
        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(0, 0, 0, 1);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        swipeRefreshLayout = findViewById(getSwipeRefreshLayoutId());
        recyclerView = findViewById(getRecyclerViewId());
        recyclerAdapter = getRecyclerAdapter();
        linearLayoutManager = getLinearLayoutManager();
        onLoadMoreListener = new OnLoadMoreListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                BaseRecyclerActivity.this.onLoadMore(currentPage);
            }
        };
        recyclerView.setOnScrollListener(onLoadMoreListener);
        recyclerView.setLayoutManager(linearLayoutManager);
        initData(0);
        recyclerView.setAdapter(recyclerAdapter);
        swipeRefreshLayout.setOnRefreshListener(() -> ThreadUtils.runFixed(() -> {
            initData(0);
            swipeRefreshLayout.setRefreshing(false);
        }));
        recyclerView.addItemDecoration(DEFAULT_ITEM_DECORATION);
    }

    public void refreshData() {
        onLoadMoreListener.reSetAll();
    }

    @SuppressLint("NotifyDataSetChanged")
    protected void onResult(ArrayList<T> responses, int page) {
        if (recyclerAdapter == null) {
            return;
        }
        if (page == 0) {
            recyclerAdapter.setData(responses);
            refreshData();
        } else {
            recyclerAdapter.addData(responses);
        }
        runOnUiThread(() -> recyclerAdapter.notifyDataSetChanged());
    }

    public abstract void onLoadMore(int currentPage);

    public abstract void initData(int page);

    public abstract int getSwipeRefreshLayoutId();

    public abstract int getRecyclerViewId();

    public abstract BaseRecyclerAdapter<T> getRecyclerAdapter();

    public abstract LinearLayoutManager getLinearLayoutManager();
}
