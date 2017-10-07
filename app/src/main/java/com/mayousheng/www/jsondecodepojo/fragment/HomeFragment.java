package com.mayousheng.www.jsondecodepojo.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.ViewPagerAdapter;
import com.mayousheng.www.jsondecodepojo.base.BaseFragment;
import com.mayousheng.www.jsondecodepojo.common.ViewDesc;
import com.mayousheng.www.jsondecodepojo.fragment.bsbdj.PunsterFragment;
import com.mayousheng.www.jsondecodepojo.fragment.bsbdj.VideoFragment;
import com.mayousheng.www.jsondecodepojo.fragment.bsbdj.VoiceFragment;
import com.mayousheng.www.jsondecodepojo.fragment.mix.JokeFragment;
import com.mayousheng.www.jsondecodepojo.fragment.bsbdj.PhotoFragment;
import com.mayousheng.www.jsondecodepojo.view.OrientationScrollView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ma kai on 2017/10/4.
 */

public class HomeFragment extends BaseFragment {

    @ViewDesc(viewId = R.id.view_pager)
    ViewPager viewPager;
    @ViewDesc(viewId = R.id.orientation_scroll_view)
    OrientationScrollView orientationScrollView;
    private OrientationScrollView.OnTitleClickListener onTitleClickListener;
    private OrientationScrollView.OnNaPageChangeListener onNaPageChangeListener;
    private ViewPagerAdapter viewPagerAdapter;
    private List<Fragment> fragmentArray = new ArrayList<>();

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView(View view) {
        initFragments();
        viewPagerAdapter = new ViewPagerAdapter(getActivity().getSupportFragmentManager(), fragmentArray);
        viewPager.setAdapter(viewPagerAdapter);
        initOrientationScrollView();
    }

    private void initFragments() {
        fragmentArray.add(new JokeFragment());
        fragmentArray.add(new PunsterFragment());
        fragmentArray.add(new VoiceFragment());
        fragmentArray.add(new VideoFragment());
        fragmentArray.add(new PhotoFragment());
    }

    private void initOrientationScrollView() {
        onTitleClickListener = new OrientationScrollView.OnTitleClickListener() {
            @Override
            public void onTitleClick(View v) {
//                Log.e("-----1", "v=" + v);
            }
        };
        onNaPageChangeListener = new OrientationScrollView.OnNaPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                Log.e("-----1", "position=" + position + ";positionOffset=" + positionOffset
//                        + ";positionOffsetPixels=" + positionOffsetPixels);
            }

            @Override
            public void onPageSelected(int position) {
//                Log.e("-----1", "position=" + position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {
//                Log.e("-----1", "state=" + state);
            }
        };
        orientationScrollView.setViewPager(getContext().getApplicationContext()
                , new String[]{
                        getString(R.string.joke), getString(R.string.punster), getString(R.string.voice),
                        getString(R.string.video), getString(R.string.photo)
                }, viewPager, R.color.gray, R.color.black, 16, 16, 12, true
                , R.color.gray, 0f, 15f, 15f, 100);
        orientationScrollView.setBgLine(getContext().getApplicationContext(), 1, R.color.colorAccent);
        orientationScrollView.setNavLine(getActivity(), 3, R.color.colorPrimary);
        orientationScrollView.setOnTitleClickListener(onTitleClickListener);
        orientationScrollView.setOnNaPageChangeListener(onNaPageChangeListener);
    }

}
