package com.distributedpos.app.ui;


import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.distributedpos.app.R;
import com.distributedpos.app.api.RetrofitProvider;
import com.distributedpos.app.helpers.CustomProgressDialog;

import io.reactivex.Observable;

public class BaseActivity extends AppCompatActivity {

    private CustomProgressDialog progressDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        if (getResources().getBoolean(R.bool.portrait_only)) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }
        this.progressDialog = new CustomProgressDialog(this);
    }

    public void launchActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public <T> Observable<T> makeUIObservable(Observable<T> observable) {
        return RetrofitProvider.configureObservable(observable);
    }

    public void showSnackBar(View mainLayout, String message, int backgroundColour) {
        Snackbar snackbar = Snackbar
                .make(mainLayout, message, Snackbar.LENGTH_LONG);
        View snackBarView = snackbar.getView();
        snackBarView.setBackgroundColor(ContextCompat.getColor(this, backgroundColour));
        snackbar.show();
    }

    public void showProgress() {
        if (!progressDialog.isShowing()) {
            this.progressDialog.show();
        }
    }

    public void hideProgress() {
        if (progressDialog.isShowing()) {
            this.progressDialog.dismiss();
        }
    }


}
