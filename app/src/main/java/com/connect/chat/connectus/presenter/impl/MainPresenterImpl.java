package com.connect.chat.connectus.presenter.impl;


import com.connect.chat.connectus.base.BasePresenterImp;
import com.connect.chat.connectus.presenter.MainPresenter;
import com.connect.chat.connectus.ui.MainView;

import org.json.JSONObject;

import java.util.ArrayList;

//Xong ccái này th
// kieeur hàm này dùng lm gì
// chứa nhưng phương thức gì
//.. để t đọc xong tổng hợp lại ms có hcais hỏi ý giờ chưa hiểu lắm nên hỏi mong lung lắm ko vào trọng tâm//
public class MainPresenterImpl extends BasePresenterImp<MainView> implements MainPresenter {
    ArrayList<JSONObject> data = new ArrayList<>();
    public MainPresenterImpl(MainView view) {
        super(view);
    }

    @Override
    public ArrayList<JSONObject> data() {
        return data;
    }

    @Override
    public void getAPI() {
        //gọi api trong đây
        //data = cái đéo gì đấy
        //gọi xong api thì binding dữ liệu ra;
        getView().bindingdata(data);
    }
}
