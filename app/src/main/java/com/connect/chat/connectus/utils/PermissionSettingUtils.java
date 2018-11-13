package com.connect.chat.connectus.utils;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;


public final class PermissionSettingUtils {
    public static final int REQUEST_CODE = 400;

    /**
     * Runtime Permission Check
     */
    public static void requestPermission(AppCompatActivity activity) {
        ActivityCompat.requestPermissions(activity, new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION,
                },
                REQUEST_CODE);
    }


    public static boolean isPermissionGranted(String[] grantPermissions,
                                              int[] grantResults) {
        int permissionSize = grantPermissions.length;
        for (int i = 0; i < permissionSize; i++) {
            if (Manifest.permission.ACCESS_FINE_LOCATION.equals(grantPermissions[i]) ||
                    Manifest.permission.ACCESS_COARSE_LOCATION.equals(grantPermissions[i])) {
                return grantResults[i] == PackageManager.PERMISSION_GRANTED;
            }
        }
        return false;
    }

    public static class LocationSettingDialog extends DialogFragment {

        public static LocationSettingDialog newInstance() {
            return new LocationSettingDialog();
        }

        
        @NonNull
        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {


            return new AlertDialog.Builder(getActivity())
                    .setMessage("Accept Permission")
                    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
                                    Uri.parse("package:" + getActivity().getPackageName()));
                            intent.addCategory(Intent.CATEGORY_DEFAULT);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("NO", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            return;
                        }
                    })
                    .create();
        }
    }
}