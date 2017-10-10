package com.mayousheng.www.jsondecodepojo.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.listener.OnLoadMoreListener;
import com.mayousheng.www.jsondecodepojo.utils.ThreadUtils;
import com.mayousheng.www.viewinit.ViewDesc;

import java.util.ArrayList;

/**
 * Created by ma kai on 2017/10/4.
 */

public abstract class BaseNewsFragment<T extends BaseResponse> extends BaseFragment {

    @ViewDesc(viewId = R.id.swipe_refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @ViewDesc(viewId = R.id.recycler_view)
    public RecyclerView recyclerView;
    protected BaseRecyclerAdapter<T> recyclerAdapter;
    protected LinearLayoutManager linearLayoutManager;
    protected OnLoadMoreListener onLoadMoreListener;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = super.onCreateView(inflater, container, savedInstanceState);
        onLoadMoreListener = new OnLoadMoreListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int currentPage) {
                BaseNewsFragment.this.onLoadMore(currentPage);
            }
        };
        recyclerView.setOnScrollListener(onLoadMoreListener);
        recyclerView.setLayoutManager(linearLayoutManager);
        initData(0);
        recyclerView.setAdapter(recyclerAdapter);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ThreadUtils.executor.execute(new Runnable() {
                    @Override
                    public void run() {
                        initData(0);
                        swipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
        });
        recyclerView.addItemDecoration(StaticParam.DEFAULT_ITEM_DECORATION);
        return view;
    }

    public void refreshData() {
        onLoadMoreListener.reSetAll();
    }

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
            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    recyclerAdapter.notifyDataSetChanged();
                }
            });
        }
    }

    public abstract void onLoadMore(int currentPage);

    public abstract void initData(int page);
}
