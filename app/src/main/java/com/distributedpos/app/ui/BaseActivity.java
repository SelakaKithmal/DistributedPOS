package com.distributedpos.app.ui;


import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;

import com.distributedpos.app.api.RetrofitProvider;

import io.reactivex.Observable;

public class BaseActivity extends AppCompatActivity {

    public void launchActivity(Class<? extends Activity> activity) {
        Intent intent = new Intent(this, activity);
        startActivity(intent);
    }

    public <T> Observable<T> makeUIObservable(Observable<T> observable) {
        return RetrofitProvider.configureObservable(observable);
    }
}
