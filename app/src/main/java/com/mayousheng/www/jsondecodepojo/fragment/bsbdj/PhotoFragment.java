package com.mayousheng.www.jsondecodepojo.fragment.bsbdj;

import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.bsbdj.PhotoAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseNewsFragment;
import com.mayousheng.www.jsondecodepojo.pojo.BSBDJPhotoResponse;
import com.mayousheng.www.jsondecodepojo.utils.ArrayListBack;
import com.mayousheng.www.jsondecodepojo.utils.InfoUtils;

import java.util.ArrayList;

public class PhotoFragment extends BaseNewsFragment<BSBDJPhotoResponse> {

    @Override
    protected int getLayoutId() {
        linearLayoutManager = new LinearLayoutManager(getActivity()
                , LinearLayoutManager.VERTICAL, false);
        return R.layout.fragment_bsbdj_photo;
    }

    @Override
    protected void initView(View view) {
        recyclerAdapter = new PhotoAdapter(getActivity());
    }

    @Override
    public void onLoadMore(int currentPage) {
        initData(currentPage);
    }

    @Override
    public void initData(final int page) {
        InfoUtils.getBSBDJPhotos(page, 10, new ArrayListBack<BSBDJPhotoResponse>() {
            @Override
            public void onFail(int status, String message) {

            }

            @Override
            public void onResult(ArrayList<BSBDJPhotoResponse> data) {
                PhotoFragment.this.onResult(data, page);
            }
        });
    }

}
