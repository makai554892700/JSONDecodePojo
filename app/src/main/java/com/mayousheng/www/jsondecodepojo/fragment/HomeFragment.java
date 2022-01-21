package com.mayousheng.www.jsondecodepojo.fragment;

import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.mayousheng.www.jsondecodepojo.R;
import com.mayousheng.www.jsondecodepojo.adapter.ViewPagerAdapter;
import com.mayousheng.www.recyclerutils.BaseFragment;
import com.mayousheng.www.initview.ViewDesc;
import com.mayousheng.www.jsondecodepojo.fragment.bsbdj.PunsterFragment;
import com.mayousheng.www.jsondecodepojo.fragment.bsbdj.VideoFragment;
import com.mayousheng.www.jsondecodepojo.fragment.bsbdj.VoiceFragment;
import com.mayousheng.www.jsondecodepojo.fragment.mix.JokeFragment;
import com.mayousheng.www.jsondecodepojo.fragment.bsbdj.PhotoFragment;
import com.mayousheng.www.jsondecodepojo.view.OrientationScrollView;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends BaseFragment {

    @ViewDesc(viewId = R.id.view_pager)
    public ViewPager viewPager;
    @ViewDesc(viewId = R.id.orientation_scroll_view)
    public OrientationScrollView orientationScrollView;
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
        viewPagerAdapter = new ViewPagerAdapter(getFragmentManager(), fragmentArray);
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
        orientationScrollView.setViewPager(getActivity().getApplicationContext()
                , new String[]{
                        getString(R.string.joke), getString(R.string.punster), getString(R.string.voice),
                        getString(R.string.video), getString(R.string.photo)
                }, viewPager, R.color.gray, R.color.black, 16, 16, 12, true
                , R.color.gray, 0f, 15f, 15f, 100);
        orientationScrollView.setBgLine(getActivity().getApplicationContext(), 1, R.color.colorAccent);
        orientationScrollView.setNavLine(getActivity(), 3, R.color.colorPrimary);
        orientationScrollView.setOnTitleClickListener(onTitleClickListener);
        orientationScrollView.setOnNaPageChangeListener(onNaPageChangeListener);
    }

}
