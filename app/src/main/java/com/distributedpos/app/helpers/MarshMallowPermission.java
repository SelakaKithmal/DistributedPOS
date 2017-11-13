package com.distributedpos.app.helpers;


import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class MarshMallowPermission {

    private static final int PERMISSION_REQUEST_CODE = 1;
    private Activity activity;

    public MarshMallowPermission(Activity activity) {
        this.activity = activity;
    }

    public void checkRuntimePermissions() {
        if (!checkPermission()) {
            requestPermission();
        }
    }

    private boolean checkPermission() {
        int cameraPermissionResult = ContextCompat.checkSelfPermission(activity,
                android.Manifest.permission.CAMERA);

        return cameraPermissionResult == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        ActivityCompat.requestPermissions(activity, new String[]
                {
                        android.Manifest.permission.CAMERA,
                }, PERMISSION_REQUEST_CODE);
    }
}
