package com.connect.chat.connectus.ui.fragment;


import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseFragment;
import com.connect.chat.connectus.base.BasePresenter;
import com.connect.chat.connectus.presenter.SettingPresenter;
import com.connect.chat.connectus.presenter.impl.SettingPresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class SettingFragment extends BaseFragment<SettingPresenter> implements SettingView {
    @Override
    public int getContentViewId() {
        return R.layout.fragment_setting;
    }

    @Override
    public void initializeComponents(View view) {

    }

    @Override
    public SettingPresenter createPresenter() {
        return new SettingPresenterImpl(this);
    }

    public static SettingFragment newInstance() {
        return new SettingFragment();
    }
}
