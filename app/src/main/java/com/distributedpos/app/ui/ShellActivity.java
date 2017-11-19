package com.distributedpos.app.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.distributedpos.app.R;
import com.distributedpos.app.helpers.PreferenceManager;
import com.distributedpos.app.model.Item;
import com.distributedpos.app.model.User;
import com.distributedpos.app.ui.fragment.Home;
import com.distributedpos.app.ui.fragment.Profile;
import com.distributedpos.app.ui.fragment.Scanner;
import com.distributedpos.app.ui.item.ItemList;
import com.distributedpos.app.ui.login.Login;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ShellActivity extends BaseActivity implements NavigationView
        .OnNavigationItemSelectedListener {

    @BindView(R.id.main_toolbar)
    Toolbar toolbar;
    @BindView(R.id.toolbar_title)
    TextView toolbarTitle;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;
    @BindView(R.id.frame_container_system)
    FrameLayout mainFrame;
    ActionBarDrawerToggle toggle;
    View headerView;
    private ArrayList<Item> itemList;
    private TextView headerUserEmail, headerUsername;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shell);
        ButterKnife.bind(this);
        initViews();
        loadMainContainer(new Home());
        setToolbarTitle("Home");
    }

    private void initViews() {
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }
        toolbar.setBackgroundColor(ContextCompat.getColor(this,
                R.color.blue_sapphire));
        itemList = new ArrayList<>();
        toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        navigationView.setNavigationItemSelectedListener(this);
        headerView = navigationView.getHeaderView(0);
        headerUsername = ButterKnife.findById(headerView, R.id.header_user_name);
        headerUserEmail = ButterKnife.findById(headerView, R.id.header_user_email);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        setUserProfile();
    }

    public void setUserProfile() {
        User currentUser = new PreferenceManager(this).getUser();
        if (currentUser != null) {
            headerUsername.setText(currentUser.getCustomerName());
            headerUserEmail.setText(currentUser.getEmail());
        }
    }

    public void setToolbarTitle(String title) {
        toolbarTitle.setText(title);
        toolbarTitle.setTextColor(ContextCompat.getColor(this, R.color.white));
    }

    public void addItemsToList(Item currentItem) {
        itemList.add(currentItem);
    }

    public void loadMainContainer(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_container, fragment);
        fragmentTransaction.commit();
        drawerClosed();
    }

    private void drawerClosed() {
        drawer.closeDrawer(GravityCompat.START);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.nav_scanner:
                loadMainContainer(new Scanner());
                break;
            case R.id.nav_item:
                if (itemList.size() > 0) {
                    Map<String, Item> map = new LinkedHashMap<>();
                    for (Item ays : itemList) {
                        map.put(ays.getItemCode(), ays);
                    }
                    itemList.clear();
                    itemList.addAll(map.values());

                    loadMainContainer(ItemList.newInstance(itemList));
                }
                break;
            case R.id.nav_profile:
                loadMainContainer(new Profile());
                break;
            case R.id.nav_history:
                break;
            case R.id.nav_logOut:
                this.finish();
                launchActivity(Login.class);
                this.overridePendingTransition(R.anim.back_in, R.anim.back_out);
                break;
        }
        return true;
    }

    public void showSnackBar(String message, int backgroundColour) {
        this.showSnackBar(mainFrame, message, backgroundColour);
    }
}
