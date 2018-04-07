package com.connect.chat.connectus.utils;

/**
 * Created by Blue on 8/23/2017.
 */

import android.content.Context;

/**
 * Created by Versa on 10.09.15.
 */
public class ApplicationContextSingleton {
    private static volatile ApplicationContextSingleton mInstance;
    private Context context;

    public static ApplicationContextSingleton getInstance() {
        if (mInstance == null) mInstance = getSync();
        return mInstance;
    }

    private static synchronized ApplicationContextSingleton getSync() {
        if (mInstance == null) mInstance = new ApplicationContextSingleton();
        return mInstance;
    }

    public void initialize(Context context) {
        this.context = context;
    }

    public Context getApplicationContext() {
        return context;
    }

}
