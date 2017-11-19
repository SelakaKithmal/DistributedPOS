package com.distributedpos.app.ui.fragment;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.distributedpos.app.R;
import com.distributedpos.app.ui.ShellActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;


public class Home extends Fragment {
    private ShellActivity shellActivity;

    public Home() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_home, container, false);
        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        if (activity instanceof ShellActivity) {
            shellActivity = (ShellActivity) activity;
        }
        shellActivity.setToolbarTitle("Home");
        return view;
    }

    @OnClick(R.id.scanner_button)
    void openScanner() {
        shellActivity.loadMainContainer(new Scanner());
    }

}
