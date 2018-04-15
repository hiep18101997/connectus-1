package com.connect.chat.connectus.ui.activity;

import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseActivity;
import com.connect.chat.connectus.presenter.MainPresenter;

public class HomeActivity extends BaseActivity<MainPresenter> {

    @Override
    public int getContentViewId() {
        return R.layout.activity_home;
    }

    @Override
    public void initializeComponents() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public MainPresenter createPresenter() {
        return new MainPresenter() {

            @Override
            public int hashCode() {
                return super.hashCode();
            }
        };
    }
}
