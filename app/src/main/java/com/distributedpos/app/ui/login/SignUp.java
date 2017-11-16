package com.distributedpos.app.ui.login;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.distributedpos.app.R;
import com.distributedpos.app.api.AuthenticationService;
import com.distributedpos.app.api.RetrofitProvider;
import com.distributedpos.app.helpers.DateFormatter;
import com.distributedpos.app.helpers.MarshMallowPermission;
import com.distributedpos.app.helpers.PreferenceManager;
import com.distributedpos.app.helpers.ValidationHelper;
import com.distributedpos.app.model.User;
import com.distributedpos.app.ui.BaseActivity;
import com.distributedpos.app.ui.ShellActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.disposables.Disposable;

public class SignUp extends BaseActivity {

    private AuthenticationService authService;
    private Disposable subscription;
    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.first_name_field)
    EditText firstNameField;
    @BindView(R.id.last_name_field)
    EditText lastNameField;
    @BindView(R.id.nic_field)
    EditText nicField;
    @BindView(R.id.contact_no_field)
    EditText contactField;
    @BindView(R.id.address1_field)
    EditText address1Field;
    @BindView(R.id.address2_field)
    EditText address2Field;
    @BindView(R.id.city_field)
    EditText cityField;
    @BindView(R.id.email_field)
    EditText emailField;
    @BindView(R.id.mainLayout)
    LinearLayout mainLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        this.authService = RetrofitProvider.createRetrofit(this).create(AuthenticationService.class);
        ButterKnife.bind(this);
        MarshMallowPermission permission = new MarshMallowPermission(this);
        permission.checkRuntimePermissions();
        initView();
    }

    private void initView() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    @OnClick(R.id.submit_button)
    public void signUp() {
        userSignUp();
    }

    private void userSignUp() {
        String firstName = firstNameField.getText().toString().trim();
        String lastName = lastNameField.getText().toString().trim();
        String email = emailField.getText().toString().trim();
        String contactNo = contactField.getText().toString().trim();
        String nic = nicField.getText().toString().trim();
        String address1 = address1Field.getText().toString().trim();
        String address2 = address2Field.getText().toString().trim();
        String city = cityField.getText().toString().trim();

        if (TextUtils.isEmpty(firstName)) {
            firstNameField.requestFocus();
            firstNameField.setError(getString(R.string.error_message_firstName));
        } else if (TextUtils.isEmpty(lastName)) {
            lastNameField.requestFocus();
            lastNameField.setError(getString(R.string.error_message_lastName));
        } else if (TextUtils.isEmpty(email)) {
            emailField.requestFocus();
            emailField.setError(getString(R.string.error_message_email));
        } else if (!ValidationHelper.isValidEmail(email)) {
            emailField.requestFocus();
            emailField.setError(getString(R.string.error_message_invalid_email));
        } else if (TextUtils.isEmpty(contactNo)) {
            contactField.requestFocus();
            contactField.setError(getString(R.string.error_message_contact));
        } else if (TextUtils.isEmpty(nic)) {
            nicField.requestFocus();
            nicField.setError(getString(R.string.error_message_nic));
        } else if (TextUtils.isEmpty(address1)) {
            address1Field.requestFocus();
            address1Field.setError(getString(R.string.error_message_address));
        } else if (TextUtils.isEmpty(city)) {
            cityField.requestFocus();
            cityField.setError(getString(R.string.error_message_city));
        } else if (TextUtils.isEmpty(address2)) {
            cityField.requestFocus();
            cityField.setError(getString(R.string.error_message_address));
        } else {
            this.showProgress();
            User currentUser = new User(firstName + " " + lastName, nic, contactNo, email,
                    address1, address2, city);
            subscription = this.makeUIObservable(authService.signUp(firstName + " " + lastName, nic,
                    contactNo, email, "Cash", address1, address2, city, DateFormatter.getCurrentDate()))
                    .subscribe(
                            success -> {
                                this.hideProgress();
                                new PreferenceManager(this).putUser(currentUser);
                                goToLandingPage();

                            },
                            error -> {
                                this.hideProgress();
                                new PreferenceManager(this).putUser(currentUser);
                                goToLandingPage();

                            }
                    );

        }
    }

    private void goToLandingPage() {
        launchActivity(ShellActivity.class);
        SignUp.this.overridePendingTransition(R.anim.animation, R.anim.animation2);
        this.finish();
    }

    @Override
    public void onBackPressed() {
        this.finish();
        this.overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.home) {

            onBackPressed();
            finish();
            overridePendingTransition(R.anim.back_in, R.anim.back_out);
            return true;

        } else if (id == R.id.homeAsUp) {

            onBackPressed();
            finish();
            overridePendingTransition(R.anim.back_in, R.anim.back_out);
            return true;

        } else if (id == android.R.id.home) {

            onBackPressed();
            finish();
            overridePendingTransition(R.anim.back_in, R.anim.back_out);
            return true;

        }

        return super.onOptionsItemSelected(item);

    }

    @Override
    protected void onDestroy() {
        if (this.subscription != null) {
            this.subscription.dispose();
        }
        super.onDestroy();
    }
}
