package com.connect.chat.connectus.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseActivity;
import com.connect.chat.connectus.presenter.MainPresenter;
import com.connect.chat.connectus.presenter.impl.MainPresenterImpl;
import com.connect.chat.connectus.ui.MainView;

import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener ,MainView{


    @Override
    public MainPresenter createPresenter() {

        return new MainPresenterImpl(this);
    }

    @Override
    public int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    public void initializeComponents() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }

        findViewById(R.id.btn_online).setOnClickListener(this);
        findViewById(R.id.btn_offline).setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {

        switch (view.getId()) {

            case R.id.btn_online:

                Intent intent = new Intent(MainActivity.this, HomeActivity.class) ;
                intent.putExtra("online", 1) ;
                intent.putExtra("offline", 1) ;
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;

                startActivity(intent);

                break;


            case R.id.btn_offline:

                break;


            default:

        }

    }

    @Override
    public void bindingdata(ArrayList<JSONObject> data) {
        //binding dữ liệu thì viết ở đây
    }
}
