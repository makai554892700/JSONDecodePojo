package com.mayousheng.www.jsondecodepojo.fragment.mix;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.JokeAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsFragment;
import com.mayousheng.www.jsondecodepojo.pojo.JokeResponse;
import com.mayousheng.www.jsondecodepojo.utils.ArrayListBack;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;

import java.util.ArrayList;

/**
 * Created by ma kai on 2017/10/4.
 */

public class JokeFragment extends BaseNewsFragment<JokeResponse> {

    @Override
    protected int getLayoutId() {
        linearLayoutManager = new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, false);
        return R.layout.fragment_joke;
    }

    @Override
    protected void initView(View view) {
        recyclerAdapter = new JokeAdapter(getContext());
        recyclerAdapter.getShowImageUtils().setView(getRootView());
    }

    @Override
    public void onLoadMore(int currentPage) {
        initData(currentPage);
    }

    @Override
    public void initData(final int page) {
        InfoUtils.getJokes(page, 10, new ArrayListBack<JokeResponse>() {
            @Override
            public void onFail(int status, String message) {

            }

            @Override
            public void onResult(ArrayList<JokeResponse> responses) {
                JokeFragment.this.onResult(responses, page);
            }
        });
    }
}
