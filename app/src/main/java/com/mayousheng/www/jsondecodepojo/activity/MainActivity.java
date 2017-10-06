package com.mayousheng.www.jsondecodepojo.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.SparseArray;
import android.view.View;
import android.widget.FrameLayout;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.base.BaseActivity;
import com.mayousheng.www.jsondecodepojo.common.ViewDesc;
import com.mayousheng.www.jsondecodepojo.fragment.HomeFragment;
import com.mayousheng.www.jsondecodepojo.fragment.MineFragment;
import com.mayousheng.www.jsondecodepojo.fragment.VideoFragment;
import com.mayousheng.www.jsondecodepojo.view.IconAndTextView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @ViewDesc(viewId = R.id.content_view)
    FrameLayout contentView;
    @ViewDesc(viewId = R.id.index_one)
    IconAndTextView indexOne;
    @ViewDesc(viewId = R.id.index_two)
    IconAndTextView indexTwo;
    @ViewDesc(viewId = R.id.index_three)
    IconAndTextView indexThree;
    private FragmentManager fragmentManager;
    private SparseArray<IconAndTextView> iconAndTextViews = new SparseArray<>();
    private ArrayList<Fragment> fragmentArray = new ArrayList<>();
    private int lastIndex = -1;
    private int lastId = -1;

    @Override
    protected int getLayout() {
        return R.layout.activity_main;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        fragmentManager = getSupportFragmentManager();
        initView();
        initFragments();
        showFragment(chooseIndex(R.id.index_one));
    }

    //初始化fragments
    private void initFragments() {
        fragmentArray.add(new HomeFragment());
        fragmentArray.add(new VideoFragment());
        fragmentArray.add(new MineFragment());
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        for (Fragment fragment : fragmentArray) {
            fragmentTransaction.add(R.id.content_view, fragment);
        }
        fragmentTransaction.commit();
    }

    //获取view
    private void initView() {
        iconAndTextViews.put(R.id.index_one, indexOne);
        indexOne.setOnClickListener(this);
        iconAndTextViews.put(R.id.index_two, indexTwo);
        indexTwo.setOnClickListener(this);
        iconAndTextViews.put(R.id.index_three, indexThree);
        indexThree.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        showFragment(chooseIndex(view.getId()));
    }

    private void showFragment(int index) {
        if (lastIndex == index) {
            return;
        }
        lastIndex = index;
        if (fragmentArray.size() > index) {
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            for (int i = 0; i < fragmentArray.size(); i++) {
                if (i == index) {
                    fragmentTransaction.show(fragmentArray.get(i));
                } else {
                    fragmentTransaction.hide(fragmentArray.get(i));
                }
            }
            fragmentTransaction.commitAllowingStateLoss();
        }
    }

    private int chooseIndex(int id) {
        if (lastId == id) {
            return -1;
        }
        int index = -1;
        synchronized (this) {
            for (int i = 0; i < iconAndTextViews.size(); i++) {
                if (id == iconAndTextViews.keyAt(i)) {
                    iconAndTextViews.valueAt(i).setIconAlpha(100);
                    index = i;
                } else {
                    iconAndTextViews.valueAt(i).setIconAlpha(0);
                }
            }
        }
        lastId = id;
        return index;
    }

}
