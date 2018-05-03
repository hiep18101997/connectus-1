package com.connect.chat.connectus.presenter.impl;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.connect.chat.connectus.base.BasePresenterImp;
import com.connect.chat.connectus.presenter.OfflinePresenter;
import com.connect.chat.connectus.ui.fragment.OfflineView;

public class OfflinePresenterImpl extends BasePresenterImp<OfflineView> implements OfflinePresenter {

    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();


    public OfflinePresenterImpl(OfflineView view) {
        super(view);
    }

    @Override
    public void checkBluetoothAvailabe(Context context) {
        if (BTAdapter == null) {
            new AlertDialog.Builder(context)
                    .setTitle("Not compatible")
                    .setMessage("Your phone does not support Bluetooth")
                    .setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            System.exit(0);
                        }
                    })
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .show();
        }
    }
}
