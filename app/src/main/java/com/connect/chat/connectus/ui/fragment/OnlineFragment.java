package com.connect.chat.connectus.ui.fragment;


import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.ImageView;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseFragment;
import com.connect.chat.connectus.data.fixtures.DialogsFixtures;
import com.connect.chat.connectus.data.model.Dialog;
import com.connect.chat.connectus.presenter.OnlinePresenter;
import com.connect.chat.connectus.presenter.impl.OnlinePresenterImpl;
import com.connect.chat.connectus.ui.activity.MessagesActivity;
import com.squareup.picasso.Picasso;
import com.stfalcon.chatkit.commons.ImageLoader;
import com.stfalcon.chatkit.dialogs.DialogsList;
import com.stfalcon.chatkit.dialogs.DialogsListAdapter;
import com.stfalcon.chatkit.utils.DateFormatter;

import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * A simple {@link Fragment} subclass.
 */
public class OnlineFragment extends BaseFragment<OnlinePresenter> implements OnlineView, DialogsListAdapter.OnDialogClickListener<Dialog>, DialogsListAdapter.OnDialogLongClickListener<Dialog>, DateFormatter.Formatter {
    DialogsList dialogsListView;
    DialogsListAdapter<Dialog> dialogsAdapter;

    public static OnlineFragment newInstance() {
        return new OnlineFragment();
    }

    @Override
    public int getContentViewId() {
        return R.layout.fragment_online;
    }

    @Override
    public void initializeComponents(View view) {
        ButterKnife.bind(view);
        dialogsListView=view.findViewById(R.id.dialogsList);
        initAdapter();

    }

    private void initAdapter() {
        dialogsAdapter = new DialogsListAdapter<>(new ImageLoader() {
            @Override
            public void loadImage(ImageView imageView, String url) {
                Picasso.get().load(url).into(imageView);
            }
        });
        dialogsAdapter.setItems(DialogsFixtures.getDialogs());
        dialogsAdapter.setOnDialogClickListener(this);
        dialogsAdapter.setOnDialogLongClickListener(this);
        dialogsAdapter.setDatesFormatter(this);
        dialogsListView.setAdapter(dialogsAdapter);
    }

    @Override
    public OnlinePresenter createPresenter() {
        return new OnlinePresenterImpl(this);
    }

    @Override
    public void onDialogClick(Dialog dialog) {
        startActivity(new Intent(getContext(), MessagesActivity.class));
    }

    @Override
    public void onDialogLongClick(Dialog dialog) {

    }

    @Override
    public String format(Date date) {
        if (DateFormatter.isToday(date)) {
            return DateFormatter.format(date, DateFormatter.Template.TIME);
        } else if (DateFormatter.isYesterday(date)) {
            return getString(R.string.date_header_yesterday);
        } else if (DateFormatter.isCurrentYear(date)) {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH);
        } else {
            return DateFormatter.format(date, DateFormatter.Template.STRING_DAY_MONTH_YEAR);
        }
    }
}
