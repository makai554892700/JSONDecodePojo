package com.mayousheng.www.recyclerutils;

import android.annotation.SuppressLint;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.mayousheng.www.basepojo.BasePoJo;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.recyclerutils.utils.OnLoadMoreListener;
import com.mayousheng.www.recyclerutils.utils.ThreadUtils;

import java.util.ArrayList;

public abstract class BaseRecyclerFragment<T extends BasePoJo> extends BaseFragment {

    @SuppressLint("NonConstantResourceId")
    @ViewDesc(viewId = R.id.swipe_refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @SuppressLint("NonConstantResourceId")
    @ViewDesc(viewId = R.id.recycler_view)
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

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        recyclerAdapter = getRecyclerAdapter();
        linearLayoutManager = getLinearLayoutManager();
        onLoadMoreListener = new OnLoadMoreListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                BaseRecyclerFragment.this.onLoadMore(currentPage);
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
        return view;
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
        if (getActivity() != null) {
            getActivity().runOnUiThread(() -> recyclerAdapter.notifyDataSetChanged());
        }
    }

    public abstract void onLoadMore(int currentPage);

    public abstract void initData(int page);

    public abstract BaseRecyclerAdapter<T> getRecyclerAdapter();

    public abstract LinearLayoutManager getLinearLayoutManager();
}
