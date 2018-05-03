package com.connect.chat.connectus.utils;

import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;

/**
 * Created by Peih on 4/22/2018.
 */

public class Utils {
    public static int checkPermission(String[] permissions, Context mContext) {
        int permissionCheck = PackageManager.PERMISSION_GRANTED;
        for (String permission : permissions) {
            permissionCheck += ContextCompat.checkSelfPermission(mContext, permission);
        }
        return permissionCheck;
    }
}
