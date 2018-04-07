package com.connect.chat.connectus.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Gnaoh Peih on 1/4/2018.
 */

public abstract class BaseFragment <BPresenter extends BasePresenter>
        extends Fragment
        implements BaseView<BPresenter> {
    private BPresenter presenter;

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(getContentViewId(), container, false);
        presenter = createPresenter();

        initializeComponents(view);
        return view;
    }
    @Override
    public BPresenter getPresenter() {
        return presenter;
    }

    public abstract int getContentViewId();

    public abstract void initializeComponents(View view);
}