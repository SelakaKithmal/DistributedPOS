package com.distributedpos.app.ui.fragment;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.view.ContextThemeWrapper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.distributedpos.app.R;
import com.google.zxing.Result;

import butterknife.BindView;
import butterknife.ButterKnife;
import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission.CAMERA;


public class Scanner extends Fragment implements ZXingScannerView.ResultHandler {

    @BindView(R.id.container)
    LinearLayout mainContainer;
    private ZXingScannerView mScannerView;
    private static final int REQUEST_CAMERA = 1;
    private static int camId = Camera.CameraInfo.CAMERA_FACING_BACK;


    public Scanner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        ButterKnife.bind(this, view);
        mScannerView = new ZXingScannerView(getActivity());
        mainContainer.addView(mScannerView);
        return view;
    }

    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case REQUEST_CAMERA:
                if (grantResults.length > 0) {
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted) {
                        Toast.makeText(getActivity(), "Permission Granted, Now you can access camera", Toast.LENGTH_LONG).show();
                    } else {
                        Toast.makeText(getActivity(), "Permission Denied, You cannot access and camera", Toast.LENGTH_LONG).show();
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                            if (shouldShowRequestPermissionRationale(CAMERA)) {
                                showMessageOKCancel("You need to allow access to both the permissions",
                                        (dialog, which) -> {
                                            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                                requestPermissions(new String[]{CAMERA},
                                                        REQUEST_CAMERA);
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    private void showMessageOKCancel(String message, DialogInterface.OnClickListener okListener) {
        new android.support.v7.app.AlertDialog.Builder(getContext())
                .setMessage(message)
                .setPositiveButton("OK", okListener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    @Override
    public void handleResult(Result result) {
        final String result1 = result.getText();
        Log.e("QRCodeScanner", result.getText());
        Log.e("QRCodeScanner", result.getBarcodeFormat().toString());
        AlertDialog.Builder builder = new AlertDialog.Builder
                (new ContextThemeWrapper(getActivity(), R.style.DialogTheme));
        builder.setTitle("Scan Result");
        builder.setPositiveButton("OK", (dialog, which) -> mScannerView.resumeCameraPreview(Scanner.this));
        builder.setNeutralButton("Visit", (dialog, which) -> {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result1));
            startActivity(browserIntent);
        });
        builder.setMessage(result.getText());
        AlertDialog alert1 = builder.create();
        alert1.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        mScannerView.setResultHandler(this);
        mScannerView.startCamera(camId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mScannerView.stopCamera();
        mScannerView = null;
    }
}
