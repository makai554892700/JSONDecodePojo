package com.mayousheng.www.jsondecodepojo.base;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.viewinit.ViewDesc;

/**
 * Created by ma kai on 2017/10/4.
 */

public abstract class BaseNewsFragment extends BaseFragment {

    @ViewDesc(viewId = R.id.swipe_refresh_layout)
    public SwipeRefreshLayout swipeRefreshLayout;
    @ViewDesc(viewId = R.id.recycler_view)
    public RecyclerView recyclerView;
}
