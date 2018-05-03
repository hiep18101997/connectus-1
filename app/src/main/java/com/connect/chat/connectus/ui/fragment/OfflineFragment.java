package com.connect.chat.connectus.ui.fragment;


import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.TextView;

import com.connect.chat.connectus.R;
import com.connect.chat.connectus.base.BaseFragment;
import com.connect.chat.connectus.model.ItemOffline;
import com.connect.chat.connectus.presenter.OfflinePresenter;
import com.connect.chat.connectus.presenter.impl.OfflinePresenterImpl;
import com.connect.chat.connectus.ui.adapter.OffLineAdapter;

import java.util.ArrayList;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class OfflineFragment extends BaseFragment<OfflinePresenter> implements OfflineView, OffLineAdapter.iCom {

    /**
     * Tag for Log
     */
    private static final String TAG = "DeviceListActivity";

    /**
     * Return Intent extra
     */
    public static String EXTRA_DEVICE_ADDRESS = "device_address";

    /**
     * Member fields
     */
    private BluetoothAdapter mBtAdapter;

    /**
     * Newly discovered devices
     */
    private OffLineAdapter devicesArrayAdapter;

    @BindView(R.id.button_scan)
    Button scanButton;

    @BindView(R.id.paired_devices)
    RecyclerView rclListDevices;

    public static int REQUEST_BLUETOOTH = 1;

    private BluetoothAdapter BTAdapter = BluetoothAdapter.getDefaultAdapter();

    private ArrayList<ItemOffline> listDevices = new ArrayList<>();

    @Override
    public int getContentViewId() {

        return R.layout.fragment_offline;
    }

    @OnClick(R.id.button_scan)
    void onClick() {
        doDiscovery();
//        scanButton.setVisibility(View.GONE);
    }

    @Override
    public void initializeComponents(View view) {

        ButterKnife.bind(this, view);

        if (!BTAdapter.isEnabled()) {
            Intent enableBT = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(enableBT, REQUEST_BLUETOOTH);
        }
        getPresenter().checkBluetoothAvailabe(getContext());


        devicesArrayAdapter = new OffLineAdapter(this);

        // Register for broadcasts when a device is discovered
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        getActivity().registerReceiver(mReceiver, filter);

        // Register for broadcasts when discovery has finished
        filter = new IntentFilter(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);
        getActivity().registerReceiver(mReceiver, filter);

        // Get the local Bluetooth adapter
        mBtAdapter = BluetoothAdapter.getDefaultAdapter();

        // Get a set of currently paired devices
        Set<BluetoothDevice> pairedDevices = mBtAdapter.getBondedDevices();

        // If there are paired devices, add each one to the ArrayAdapter
        if (pairedDevices.size() > 0) {
            for (BluetoothDevice device : pairedDevices) {
                listDevices.add(new ItemOffline(device.getName(), device.getAddress(), device.getBondState()));
            }
        }

        rclListDevices.setLayoutManager(new LinearLayoutManager(getContext()));
        rclListDevices.smoothScrollToPosition(0);
        // Find and set up the ListView for paired devices
        rclListDevices.setAdapter(devicesArrayAdapter);

    }

    @Override
    public OfflinePresenter createPresenter() {
        return new OfflinePresenterImpl(this);
    }

    public static OfflineFragment newInstance() {
        return new OfflineFragment();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        // Make sure we're not doing discovery anymore
        if (mBtAdapter != null) {
            mBtAdapter.cancelDiscovery();
        }

        // Unregister broadcast listeners
        getActivity().unregisterReceiver(mReceiver);
    }

    /**
     * Start device discover with the BluetoothAdapter
     */
    private void doDiscovery() {
        Log.d(TAG, "doDiscovery()");
        listDevices.clear();

        // Indicate scanning in the title
        getActivity().setProgressBarIndeterminateVisibility(true);
        getActivity().setTitle(R.string.scanning);

        // Turn on sub-title for new devices
        // If we're already discovering, stop it
        if (mBtAdapter.isDiscovering()) {
            mBtAdapter.cancelDiscovery();
        }

        // Request discover from BluetoothAdapter
        mBtAdapter.startDiscovery();
    }

    /**
     * The on-click listener for all devices in the ListViews
     */
    private AdapterView.OnItemClickListener mDeviceClickListener
            = new AdapterView.OnItemClickListener() {
        public void onItemClick(AdapterView<?> av, View v, int arg2, long arg3) {
            // Cancel discovery because it's costly and we're about to connect
            mBtAdapter.cancelDiscovery();

            // Get the device MAC address, which is the last 17 chars in the View
            String info = ((TextView) v).getText().toString();
            String address = info.substring(info.length() - 17);

            // Create the result Intent and include the MAC address
            Intent intent = new Intent();
            intent.putExtra(EXTRA_DEVICE_ADDRESS, address);

            // Set result and finish this Activity
            getActivity().setResult(Activity.RESULT_OK, intent);
            getActivity().finish();
        }
    };

    /**
     * The BroadcastReceiver that listens for discovered devices and changes the title when
     * discovery is finished
     */
    private final BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            // When discovery finds a device
            if (BluetoothDevice.ACTION_FOUND.equals(action)) {
                // Get the BluetoothDevice object from the Intent
                BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                // If it's already paired, skip it, because it's been listed already
//                if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
                listDevices.add(new ItemOffline(device.getName(), device.getAddress(), device.getBondState()));
                devicesArrayAdapter.notifyDataSetChanged();
                Log.d("Lisssssst" ,listDevices.size() + "" ) ;
//                }
                // When discovery is finished, change the Activity title
            } else if (BluetoothAdapter.ACTION_DISCOVERY_FINISHED.equals(action)) {
                getActivity().setTitle(R.string.select_device);
                if (listDevices.size() == 0) {
                    String noDevices = getResources().getText(R.string.none_found).toString();
//                    mNewDevicesArrayAdapter.add(noDevices);
                    Log.d("Lisssst", "finish" + listDevices.size()) ;
                }
            }
        }
    };


    @Override
    public int getCount() {
        return listDevices.size();
    }

    @Override
    public ItemOffline getItem(int position) {
        return listDevices.get(position);
    }
}
