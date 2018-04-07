package com.connect.chat.connectus.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;



import java.util.Map;

public class PrefUtil {

    private static final String TAG = "SHAREPREF";
    private static PrefUtil sInstance;

    public static PrefUtil getInstance() {
        if (sInstance == null) {
            sInstance = new PrefUtil();
        }
        return sInstance;
    }

    public static void saveBoolean(Context context, String key, boolean value) {
        SharedPreferences preferences = context.getSharedPreferences(Key.APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(Key.APP_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    public static void saveString(Context context, String key, String value) {
        SharedPreferences preferences = context.getSharedPreferences(Key.APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(Key.APP_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getString(key, defaultValue);
    }

    public static void saveLong(Context context, String key, long value) {
        SharedPreferences preferences = context.getSharedPreferences(Key.APP_PREFERENCE, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences preferences = context.getSharedPreferences(Key.APP_PREFERENCE, Context.MODE_PRIVATE);
        return preferences.getLong(key, defaultValue);
    }

    public static void saveBoolean(Context context, String key, boolean value, String name) {
        if (name == null || name.equals("")) {
            name = Key.APP_PREFERENCE;
        }
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public static void delete(Context context, String name) {
        if (name == null || name.equals("")) {
            name = Key.APP_PREFERENCE;
        }
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = preferences.edit();
        editor.clear();
        editor.apply();
    }

    public static boolean getBoolean(Context context, String key, boolean defaultValue, String name) {
        if (name == null || name.equals("")) {
            name = Key.APP_PREFERENCE;
        }
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getBoolean(key, defaultValue);
    }

    public static long[] getAllkey(Context context, String name) {
        String TAG = "FILTERS";
        long[] res;

        Map<String, ?> keys = context.getSharedPreferences(name, Context.MODE_PRIVATE).getAll();
        Log.d(TAG, "getAllkey size:" + keys.toString());
        res = new long[keys.size()];
        int i = 0;
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            res[i] = Integer.parseInt(entry.getValue().toString());
            Log.d(TAG, "getAllkey: " + res[i] + "       " + entry.getValue());
            i = i + 1;
        }
        return res;
    }

    public static void saveLong(Context context, String key, long value, String name) {
        if (name == null || name.isEmpty()) {
            name = Key.APP_PREFERENCE;
        }
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value);
        editor.apply();
    }

    public static long getLong(Context context, String key, long defaultValue, String name) {
        if (name == null || name.isEmpty()) {
            name = Key.APP_PREFERENCE;
        }
        SharedPreferences preferences = context.getSharedPreferences(name, Context.MODE_PRIVATE);
        return preferences.getLong(key, defaultValue);
    }


    public SharedPreferences get(Context context) {
        return context.getSharedPreferences(Key.APP_PREFERENCE, Context.MODE_PRIVATE);
    }


}
