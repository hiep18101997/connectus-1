package com.connect.chat.connectus.presenter.impl;

import android.content.Context;

import com.connect.chat.connectus.base.BasePresenterImp;
import com.connect.chat.connectus.presenter.HomePresenter;
import com.connect.chat.connectus.ui.activity.HomeView;

public class HomePresenterImpl extends BasePresenterImp<HomeView> implements HomePresenter {
    public HomePresenterImpl(HomeView view) {
        super(view);
    }

    @Override
    public void onClick(Context mContext) {
//        Toast.makeText(mContext, "ok", Toast.LENGTH_SHORT).show() ;
    }
}
