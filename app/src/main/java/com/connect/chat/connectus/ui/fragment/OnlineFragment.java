package com.connect.chat.connectus.ui.fragment;


import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseFragment;
import com.connect.chat.connectus.base.BasePresenter;
import com.connect.chat.connectus.presenter.OnlinePresenter;
import com.connect.chat.connectus.presenter.impl.OnlinePresenterImpl;
import com.connect.chat.connectus.ui.adapter.OnlineAdapter;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineFragment extends BaseFragment<OnlinePresenter> implements OnlineView{
    @BindView(R.id.dialogsList)
    DialogsList dialogsListView;
    @Override
    public int getContentViewId() {
        return R.layout.fragment_online;
    }

    @Override
    public void initializeComponents(View view) {
        ButterKnife.bind(view);
        DialogsListAdapter dialogsListAdapter = new DialogsListAdapter<>(R.layout.item_custom_dialog_view_holder, new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                //If you using another library - write here your way to load image
                Picasso.get().load(url).into(imageView);
            }
        });

        dialogsListView.setAdapter(dialogsListAdapter);

    }

    @Override
    public OnlinePresenter createPresenter() {
        return new OnlinePresenterImpl(this);
    }

    public static OnlineFragment newInstance() {
        return new OnlineFragment();
    }
}
