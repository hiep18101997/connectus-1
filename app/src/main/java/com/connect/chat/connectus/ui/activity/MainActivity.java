package com.connect.chat.connectus.ui.activity;

import android.content.Intent;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseActivity;
import com.connect.chat.connectus.presenter.MainPresenter;
import com.connect.chat.connectus.presenter.impl.MainPresenterImpl;

import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends BaseActivity<MainPresenter> implements View.OnClickListener, MainView {
    @BindView(R.id.btn_online)
    Button btnOnline;
    @BindView(R.id.btn_offline)
    Button btnOffline;

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
        ButterKnife.bind(this);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window w = getWindow(); // in Activity's onCreate() for instance
            w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
    }

    @OnClick({R.id.btn_online, R.id.btn_offline})
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_online:
                Intent intent = new Intent(MainActivity.this, HomeActivity.class) ;
                intent.putExtra("online", 1) ;
                intent.putExtra("offline", 1) ;
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP) ;
                startActivity(intent);
                break;
            case R.id.btn_offline:
                break;

        }
    }

    @Override
    public void bindingdata(ArrayList<JSONObject> data) {
        //binding dữ liệu thì viết ở đây
    }
}
