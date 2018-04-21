package com.connect.chat.connectus.ui.fragment;


import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseFragment;
import com.connect.chat.connectus.base.BasePresenter;
import com.connect.chat.connectus.presenter.MapPresenter;
import com.connect.chat.connectus.presenter.impl.MapPresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class MapFragment extends BaseFragment<MapPresenter> implements MapView {

    @Override
    public int getContentViewId() {
        return R.layout.fragment_map;
    }

    @Override
    public void initializeComponents(View view) {

    }

    @Override
    public MapPresenter createPresenter() {
        return new MapPresenterImpl(this);
    }

    public static MapFragment newInstance() {
        return new MapFragment();
    }
}
