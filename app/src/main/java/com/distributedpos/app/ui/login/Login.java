package com.distributedpos.app.ui.login;


import android.os.Bundle;
import android.widget.EditText;

import com.distributedpos.app.R;
import com.distributedpos.app.api.AuthenticationService;
import com.distributedpos.app.api.RetrofitProvider;
import com.distributedpos.app.helpers.MarshMallowPermission;
import com.distributedpos.app.ui.BaseActivity;
import com.distributedpos.app.ui.ShellActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class Login extends BaseActivity {

    private AuthenticationService authService;
    private Disposable authSubscription;
    @BindView(R.id.tp_field)
    EditText tpField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
        this.authService = RetrofitProvider.createRetrofit(this).create(AuthenticationService.class);
        MarshMallowPermission permission = new MarshMallowPermission(this);
        permission.checkRuntimePermissions();
    }

    @OnClick(R.id.submit_button)
    void submit() {
        checkUser();
    }

    private void checkUser() {
        authSubscription = this.makeUIObservable(authService.userAuth(tpField.getText().toString()))
                .subscribe(
                        success -> {
                            goToLandingPage();
                        },
                        error -> {

                        }
                );
    }

    private void goToLandingPage() {
        launchActivity(ShellActivity.class);
        Login.this.overridePendingTransition(R.anim.animation, R.anim.animation2);
        this.finish();
    }

    @Override
    protected void onDestroy() {
        if (this.authSubscription != null) {
            this.authSubscription.dispose();
        }
        super.onDestroy();
    }
}
