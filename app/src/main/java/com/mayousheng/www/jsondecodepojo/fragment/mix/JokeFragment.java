package com.mayousheng.www.jsondecodepojo.fragment.mix;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.JokeAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsFragment;
import com.mayousheng.www.jsondecodepojo.pojo.JokeResponse;
import com.mayousheng.www.jsondecodepojo.utils.ArrayListBack;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;

import java.util.ArrayList;

public class JokeFragment extends BaseNewsFragment<JokeResponse> {

    @Override
    protected int getLayoutId() {
        linearLayoutManager = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL, false);
        return R.layout.fragment_joke;
    }

    @Override
    protected void initView(View view) {
        recyclerAdapter = new JokeAdapter(getActivity());
        getShowImageUtils().setView(getRootView());
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
