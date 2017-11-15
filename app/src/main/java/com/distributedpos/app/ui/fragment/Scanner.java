package com.distributedpos.app.ui.fragment;


import android.app.Activity;
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
import com.distributedpos.app.model.Item;
import com.distributedpos.app.ui.ShellActivity;
import com.google.zxing.Result;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
    private ShellActivity shellActivity;


    public Scanner() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_scanner, container, false);
        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        if (activity instanceof ShellActivity) {
            shellActivity = (ShellActivity) activity;
        }
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
        final String qrResult = result.getText();
        List<String> itemCategoryList = Arrays.asList(qrResult.split(","));
        if (itemCategoryList.size() == 4) {
            AlertDialog.Builder builder = new AlertDialog.Builder
                    (new ContextThemeWrapper(getActivity(), R.style.DialogTheme));
            builder.setTitle(itemCategoryList.get(2));
            builder.setPositiveButton("OK", (dialog, which) ->
            {
                Item currentItem = new Item(itemCategoryList.get(0), itemCategoryList.get(1)
                        , itemCategoryList.get(2), itemCategoryList.get(3));
                shellActivity.addItemsToList(currentItem);
                mScannerView.resumeCameraPreview(Scanner.this);
            });
            builder.setNeutralButton("Cancel", (dialog, which) -> {
                mScannerView.resumeCameraPreview(Scanner.this);
                //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(result1));
                //startActivity(browserIntent);
            });
            builder.setMessage("Item price Rs.:" + itemCategoryList.get(3) + " Add to cart");
            AlertDialog alert1 = builder.create();
            alert1.show();
        } else {
            shellActivity.showSnackBar("Item Not Found",
                    R.color.red_cinnabar);
        }
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
