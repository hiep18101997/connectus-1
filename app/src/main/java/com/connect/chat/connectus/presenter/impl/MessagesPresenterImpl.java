package com.connect.chat.connectus.presenter.impl;

import com.connect.chat.connectus.base.BasePresenterImp;
import com.connect.chat.connectus.presenter.MessagesPresenter;
import com.connect.chat.connectus.ui.activity.MessagesView;

public class MessagesPresenterImpl extends BasePresenterImp<MessagesView> implements MessagesPresenter{
    public MessagesPresenterImpl(MessagesView view) {
        super(view);
    }
}
