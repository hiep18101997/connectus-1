package com.connect.chat.connectus.presenter;

import com.connect.chat.connectus.base.BasePresenter;

import org.json.JSONObject;

import java.util.ArrayList;


//Cai nay de khai bao cac phuong thuc trong Presenter
//Vi du m gọi API thì m sẽ viết ở đây
public interface MainPresenter extends BasePresenter {
    ArrayList<JSONObject> data();
    void getAPI();


}
