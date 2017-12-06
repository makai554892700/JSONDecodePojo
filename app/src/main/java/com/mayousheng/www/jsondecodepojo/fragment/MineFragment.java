package com.mayousheng.www.jsondecodepojo.fragment;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseFragment;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.utils.CommonRequestUtils;
import com.mayousheng.www.jsondecodepojo.utils.Settings;
import com.mayousheng.www.jsondecodepojo.utils.UserUtils;

/**
 * Created by ma kai on 2017/10/4.
 */

public class MineFragment extends BaseFragment {

    @ViewDesc(viewId = R.id.logout)
    public TextView logout;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mine;
    }

    @Override
    protected void initView(View view) {
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserUtils.logout(new CommonRequestUtils.Back() {
                    @Override
                    public void succeed() {
                        Settings.remove(getContext(), StaticParam.USER_SESSION);
                        Toast.makeText(getContext(), "succeed", Toast.LENGTH_LONG).show();
                    }

                    @Override
                    public void field(final String message) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
                            }
                        });
                    }
                });
            }
        });
    }
}
