package com.connect.chat.connectus.ui.fragment;


import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseFragment;
import com.connect.chat.connectus.base.BasePresenter;
import com.connect.chat.connectus.presenter.LoginPresenter;
import com.connect.chat.connectus.presenter.impl.LoginPresenterImpl;

/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment<LoginPresenter> implements LoginView{

    @Override
    public int getContentViewId() {
        return R.layout.fragment_login;
    }

    @Override
    public void initializeComponents(View view) {
    }

    @Override
    public LoginPresenter createPresenter() {
        return new LoginPresenterImpl(this);
    }
}
