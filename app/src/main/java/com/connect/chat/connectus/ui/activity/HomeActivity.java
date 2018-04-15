package com.connect.chat.connectus.ui.activity;

import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseActivity;
import com.connect.chat.connectus.presenter.HomePresenter;
import com.connect.chat.connectus.presenter.MainPresenter;
import com.connect.chat.connectus.presenter.impl.HomePresenterImpl;
import com.roughike.bottombar.BottomBar;
import com.roughike.bottombar.OnTabSelectListener;

import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeActivity extends BaseActivity<HomePresenter> implements HomeView, OnTabSelectListener {
    @BindView(R.id.bottom_bar)
    BottomBar bottomBar;
    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public void initializeComponents() {
        ButterKnife.bind(this);
        bottomBar.setOnTabSelectListener(this);
    }

    @Override
    public HomePresenter createPresenter() {
       return new HomePresenterImpl(this);
    }

    @Override
    public void onTabSelected(int tabId) {
        switch (tabId){

        }
    }
}
