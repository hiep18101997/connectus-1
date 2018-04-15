package com.connect.chat.connectus.ui.fragment;


import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseFragment;
import com.connect.chat.connectus.base.BasePresenter;
import com.connect.chat.connectus.presenter.OfflinePresenter;
import com.connect.chat.connectus.presenter.impl.OfflinePresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineFragment extends BaseFragment<OfflinePresenter> implements OfflineView {
    @Override
    public int getContentViewId() {
        return R.layout.fragment_offline;
    }

    @Override
    public void initializeComponents(View view) {

    }

    @Override
    public OfflinePresenter createPresenter() {
        return new OfflinePresenterImpl(this);
    }

    public static OfflineFragment newInstance() {
        return new OfflineFragment();
    }
}
