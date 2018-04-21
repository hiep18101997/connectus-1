package com.connect.chat.connectus.ui.activity;

import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseActivity;
import com.connect.chat.connectus.presenter.HomePresenter;
import com.connect.chat.connectus.presenter.impl.HomePresenterImpl;
import com.connect.chat.connectus.ui.adapter.HomePagerAdapter;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeView, OnTabSelectListener {
    @BindView(R.id.view_pager)
    ViewPager viewPager;
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;

    private HomePagerAdapter homePagerAdapter;
    private static final int MY_PERMISSION = 123;
    private View mDecorView;

    @Override
    public int getContentViewId() {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        mDecorView = getWindow().getDecorView();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        return R.layout.activity_home;
    }

    @Override
    public void initializeComponents() {

        ButterKnife.bind(this);
        bottomBar.setOnTabSelectListener(this);
        setupViewPager();

    }

    @Override
    public HomePresenter createPresenter() {
        return new HomePresenterImpl(this);
    }

    private void setupViewPager() {
        homePagerAdapter = new HomePagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(homePagerAdapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                bottomBar.selectTabAtPosition(position, true);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        viewPager.setOffscreenPageLimit(4);
    }

    @Override
    public void onTabSelected(int tabId) {
        switch (tabId) {
            case R.id.tab_online:
                viewPager.setCurrentItem(HomePagerAdapter.ONLINE_INDEX);
                break;
            case R.id.tab_offline:
                viewPager.setCurrentItem(HomePagerAdapter.OFFLINE_INDEX);

                break;
            case R.id.tab_near:
                viewPager.setCurrentItem(HomePagerAdapter.MAP_INDEX);
                break;
            case R.id.tab_more:
                viewPager.setCurrentItem(HomePagerAdapter.SETTING_INDEX);
                break;
        }
    }


}
