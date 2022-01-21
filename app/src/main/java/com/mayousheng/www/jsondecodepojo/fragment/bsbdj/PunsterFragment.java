package com.mayousheng.www.jsondecodepojo.fragment.bsbdj;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.bsbdj.PunsterAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsFragment;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.utils.ArrayListBack;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;

import java.util.ArrayList;

public class PunsterFragment extends BaseNewsFragment<BSBDJPunsterResponse> {

    @Override
    protected int getLayoutId() {
        linearLayoutManager = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL, false);
        return R.layout.fragment_bsbdj_punster;
    }

    @Override
    protected void initView(View view) {
        recyclerAdapter = new PunsterAdapter(getActivity());
    }

    @Override
    public void onLoadMore(int currentPage) {
        initData(currentPage);
    }

    @Override
    public void initData(final int page) {
        InfoUtils.getBSBDJPunsters(page, 10, new ArrayListBack<BSBDJPunsterResponse>() {
            @Override
            public void onFail(int status, String message) {

            }

            @Override
            public void onResult(ArrayList<BSBDJPunsterResponse> data) {
                PunsterFragment.this.onResult(data, page);
            }
        });
    }

}
