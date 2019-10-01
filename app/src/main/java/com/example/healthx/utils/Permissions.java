package com.example.healthx.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.healthx.app.AppController;

public class Permissions implements ActivityCompat.OnRequestPermissionsResultCallback {

    public static int PERMISSION_CODE = 1;

    public static boolean checkPermissions() {
        if (ContextCompat.checkSelfPermission(AppController.getAppContext(), Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(AppController.getAppContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        return false;
    }

    public static void requestPermission(Activity context) {
        if (ActivityCompat.shouldShowRequestPermissionRationale(context,
                Manifest.permission.READ_EXTERNAL_STORAGE)) {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE,}
                    , PERMISSION_CODE);

        } else {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, PERMISSION_CODE);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                //Toast.makeText(context, "Permission GRANTED", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AppController.getAppContext(), "Permission DENIED", Toast.LENGTH_SHORT).show();
            }
        }

    }
}
