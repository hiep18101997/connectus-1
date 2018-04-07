package com.connect.chat.connectus.base;

/**
 * Created by Gnaoh Peih on 1/4/2018.
 */

public class BasePresenterImp<BView extends BaseView> implements BasePresenter {
    private BView view;

    public BasePresenterImp(BView view) {
        this.view = view;
    }

    public BView getView() {
        return view;
    }

}
