package com.distributedpos.app.ui;


import android.os.Bundle;

import com.distributedpos.app.R;
import com.distributedpos.app.ui.login.Login;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;

public class Splash extends BaseActivity {

    private static final long SPLASH_TIME = 2L;
    private Disposable timerSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        this.timerSubscription = this.makeUIObservable(
                Observable.timer(SPLASH_TIME, TimeUnit.SECONDS))
                .subscribe(time -> {
                    this.launchActivity(Login.class);
                    this.finish();
                });
    }

    @Override
    protected void onDestroy() {
        if (timerSubscription != null) {
            timerSubscription.dispose();
        }
        super.onDestroy();
    }
}
