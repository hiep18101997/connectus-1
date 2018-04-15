package com.connect.chat.connectus.ui.adapter;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.connect.chat.connectus.ui.fragment.MapFragment;
import com.connect.chat.connectus.ui.fragment.OfflineFragment;
import com.connect.chat.connectus.ui.fragment.OnlineFragment;
import com.connect.chat.connectus.ui.fragment.SettingFragment;

/**
 * Created by Gnaoh Peih on 15/4/2018.
 */
public class HomePagerAdapter extends FragmentPagerAdapter {
    public static final int ONLINE_INDEX = 0;
    public static final int OFFLINE_INDEX = 1;
    public static final int MAP_INDEX = 2;
    public static final int SETTING_INDEX = 3;
    public static final int MAX_PAGES = 4;
    public OnlineFragment onlineFragment;
    public OfflineFragment offlineFragment;
    public MapFragment mapFragment;
    public SettingFragment settingFragment;

    public HomePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case ONLINE_INDEX:
                onlineFragment = OnlineFragment.newInstance();
                return onlineFragment;
            case OFFLINE_INDEX:
                offlineFragment = OfflineFragment.newInstance();
                return offlineFragment;
            case MAP_INDEX:
                mapFragment = MapFragment.newInstance();
                return mapFragment;
            case SETTING_INDEX:
                settingFragment = SettingFragment.newInstance();
                return settingFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return MAX_PAGES;
    }
}

