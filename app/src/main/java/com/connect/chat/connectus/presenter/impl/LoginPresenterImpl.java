package com.connect.chat.connectus.presenter.impl;

import com.connect.chat.connectus.base.BasePresenterImp;
import com.connect.chat.connectus.presenter.HomePresenter;
import com.connect.chat.connectus.presenter.LoginPresenter;
import com.connect.chat.connectus.ui.activity.HomeView;
import com.connect.chat.connectus.ui.fragment.LoginView;

public class LoginPresenterImpl extends BasePresenterImp<LoginView> implements LoginPresenter {
    public LoginPresenterImpl(LoginView view) {
        super(view);
    }
}
