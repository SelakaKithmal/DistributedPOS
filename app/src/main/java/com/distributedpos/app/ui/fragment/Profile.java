package com.distributedpos.app.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.distributedpos.app.R;
import com.distributedpos.app.helpers.PreferenceManager;
import com.distributedpos.app.model.User;
import com.distributedpos.app.ui.ShellActivity;
import com.distributedpos.app.ui.login.Login;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class Profile extends Fragment {
    private ShellActivity shellActivity;
    @BindView(R.id.first_name_field)
    TextView firstNameField;
    @BindView(R.id.last_name_field)
    TextView lastNameField;
    @BindView(R.id.nic_field)
    TextView nicField;
    @BindView(R.id.contact_no_field)
    TextView contactField;
    @BindView(R.id.address1_field)
    TextView address1Field;
    @BindView(R.id.address2_field)
    TextView address2Field;
    @BindView(R.id.city_field)
    TextView cityField;
    @BindView(R.id.email_field)
    TextView emailField;


    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        if (activity instanceof ShellActivity) {
            shellActivity = (ShellActivity) activity;
        }
        User currentUser = new PreferenceManager(getContext()).getUser();
        if (currentUser != null) {
            shellActivity.setToolbarTitle(currentUser.getCustomerName());
            String[] splited = currentUser.getCustomerName().split("\\s+");
            firstNameField.setText(splited[0]);
            lastNameField.setText(splited[1]);
            nicField.setText(currentUser.getnIC());
            contactField.setText(currentUser.getnIC());
            address1Field.setText(currentUser.getAddressLine1());
            address2Field.setText(currentUser.getAddressLine2());
            cityField.setText(currentUser.getCity());
            emailField.setText(currentUser.getEmail());

        }

        return view;
    }

    @OnClick(R.id.logout_button)
    void logout() {
        getActivity().finish();
        shellActivity.launchActivity(Login.class);
        getActivity().overridePendingTransition(R.anim.back_in, R.anim.back_out);
    }

}
