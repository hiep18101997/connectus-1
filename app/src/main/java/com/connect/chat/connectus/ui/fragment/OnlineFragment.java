package com.connect.chat.connectus.ui.fragment;


import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseFragment;
import com.connect.chat.connectus.base.BasePresenter;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineFragment extends BaseFragment<BasePresenter> {


    public OnlineFragment() {
        // Required empty public constructor
    }


    @Override
    public int getContentViewId() {
        return R.layout.fragment_online;
    }

    @Override
    public void initializeComponents(View view) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getActivity().getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @Override
    public BasePresenter createPresenter() {
        return null;
    }
}
