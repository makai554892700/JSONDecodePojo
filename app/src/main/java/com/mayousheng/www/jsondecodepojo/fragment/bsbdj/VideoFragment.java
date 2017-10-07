package com.mayousheng.www.jsondecodepojo.fragment.bsbdj;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.bsbdj.PunsterAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsFragment;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPunsterResponse;
import com.mayousheng.www.jsondecodepojo.utils.ArrayListBack;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;
import com.mayousheng.www.jsondecodepojo.utils.ThreadUtils;

import java.util.ArrayList;

/**
 * Created by ma kai on 2017/10/4.
 */

public class VideoFragment extends BaseNewsFragment {

    private PunsterAdapter punsterAdapter;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_bsbdj_video;
    }

    @Override
    protected void initView(View view) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()
                , LinearLayoutManager.VERTICAL, false));
        initData();
        punsterAdapter = new PunsterAdapter(getContext());
        recyclerView.setAdapter(punsterAdapter);
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
        recyclerView.addItemDecoration(StaticParam.DEFAULT_ITEM_DECORATION);
    }

    private void initData() {
        InfoUtils.getBSBDJPunsters(0, 10, new ArrayListBack<BSBDJPunsterResponse>() {
            @Override
            public void onFail(int status, String message) {

            }

            @Override
            public void onResult(ArrayList<BSBDJPunsterResponse> data) {
                punsterAdapter.addData(data);
                if (getActivity() != null) {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            punsterAdapter.onDataChange();
                            punsterAdapter.notifyDataSetChanged();
                        }
                    });
                }
            }
        });
    }

}
