package com.connect.chat.connectus.ui.fragment;


import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseFragment;
import com.connect.chat.connectus.base.BasePresenter;
import com.connect.chat.connectus.presenter.OnlinePresenter;
import com.connect.chat.connectus.presenter.impl.OnlinePresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineFragment extends BaseFragment<OnlinePresenter> implements OnlineView{

    @Override
    public int getContentViewId() {
        return R.layout.fragment_online;
    }

    @Override
    public void initializeComponents(View view) {

    }

    @Override
    public OnlinePresenter createPresenter() {
        return new OnlinePresenterImpl(this);
    }

    public static OnlineFragment newInstance() {
        return new OnlineFragment();
    }
}
