package com.mayousheng.www.jsondecodepojo.fragment;

import android.app.Activity;
import android.content.Intent;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.activity.FirstActivity;
import com.mayousheng.www.recyclerutils.BaseFragment;
import com.mayousheng.www.jsondecodepojo.common.StaticParam;
import com.mayousheng.www.jsondecodepojo.utils.CommonRequestUtils;
import com.mayousheng.www.jsondecodepojo.utils.Settings;
import com.mayousheng.www.jsondecodepojo.utils.UserUtils;

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
                UserUtils.logout(getActivity(), new CommonRequestUtils.Back() {
                    @Override
                    public void succeed() {
                        Settings.remove(getActivity(), StaticParam.USER_SESSION);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), "succeed", Toast.LENGTH_LONG).show();
                            }
                        });
                        finish();
                    }

                    @Override
                    public void field(final String message) {
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();
                            }
                        });
                        finish();
                    }
                });
                Settings.remove(getActivity(), StaticParam.USER_SESSION);
            }
        });
    }

    private void finish() {
        try {
            startActivity(new Intent(getActivity(), FirstActivity.class));
        } catch (Exception e) {
            return;
        }
        Activity activity = getActivity();
        if (activity != null) {
            getActivity().finish();
        }
    }
}
