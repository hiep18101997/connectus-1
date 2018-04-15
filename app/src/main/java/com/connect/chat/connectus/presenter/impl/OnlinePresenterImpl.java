package com.connect.chat.connectus.presenter.impl;

import com.connect.chat.connectus.base.BasePresenterImp;
import com.connect.chat.connectus.presenter.HomePresenter;
import com.connect.chat.connectus.presenter.OnlinePresenter;
import com.connect.chat.connectus.ui.activity.HomeView;
import com.connect.chat.connectus.ui.fragment.OnlineView;

public class OnlinePresenterImpl extends BasePresenterImp<OnlineView> implements OnlinePresenter {
    public OnlinePresenterImpl(OnlineView view) {
        super(view);
    }
}
