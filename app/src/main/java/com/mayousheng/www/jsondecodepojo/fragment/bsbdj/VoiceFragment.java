package com.mayousheng.www.jsondecodepojo.fragment.bsbdj;

import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.bsbdj.VoiceAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsFragment;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJVoiceResponse;
import com.mayousheng.www.jsondecodepojo.utils.ArrayListBack;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;

import java.util.ArrayList;

/**
 * Created by ma kai on 2017/10/4.
 */

public class VoiceFragment extends BaseNewsFragment<BSBDJVoiceResponse> {

    @Override
    protected int getLayoutId() {
        linearLayoutManager = new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, false);
        return R.layout.fragment_bsbdj_photo;
    }

    @Override
    protected void initView(View view) {
        recyclerAdapter = new VoiceAdapter(getContext());
    }

    @Override
    public void onLoadMore(int currentPage) {
        initData(currentPage);
    }

    @Override
    public void initData(final int page) {
        InfoUtils.getBSBDJVoices(page, 10, new ArrayListBack<BSBDJVoiceResponse>() {
            @Override
            public void onFail(int status, String message) {

            }

            @Override
            public void onResult(ArrayList<BSBDJVoiceResponse> data) {
                VoiceFragment.this.onResult(data, page);
            }
        });
    }

}
