package com.distributedpos.app.ui.login;


import android.os.Bundle;
import com.distributedpos.app.R;
import com.distributedpos.app.ui.BaseActivity;
import com.distributedpos.app.ui.ShellActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

public class Login extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.submit_button)
    void submit()
    {
        goToLandingPage();
    }

    private void goToLandingPage() {
        launchActivity(ShellActivity.class);
        Login.this.overridePendingTransition(R.anim.animation, R.anim.animation2);
        this.finish();
    }
}
