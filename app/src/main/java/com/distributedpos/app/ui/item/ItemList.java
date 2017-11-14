package com.distributedpos.app.ui.item;


import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.distributedpos.app.R;
import com.distributedpos.app.model.Item;
import com.distributedpos.app.ui.ShellActivity;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;


public class ItemList extends Fragment implements SwipeRefreshLayout.OnRefreshListener {

    private ShellActivity shellActivity;
    private static final String ARG_NAME = "item";
    @BindView(R.id.item_recycler_view)
    RecyclerView recyclerItemList;
    @BindView(R.id.item_count)
    TextView itemCount;
    @BindView(R.id.item_container)
    SwipeRefreshLayout itemContainer;
    private ItemListAdapter listAdapter;
    private ArrayList<Item> currentItemList;

    public static ItemList newInstance(ArrayList<Item> currentItemList) {
        ItemList itemList = new ItemList();
        Bundle args = new Bundle();
        args.putParcelableArrayList(ARG_NAME, currentItemList);
        itemList.setArguments(args);
        return itemList;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_item_list, container, false);
        ButterKnife.bind(this, view);
        Activity activity = getActivity();
        if (activity instanceof ShellActivity) {
            shellActivity = (ShellActivity) activity;
        }
        initViews();
        return view;
    }

    private void initViews() {
        currentItemList = new ArrayList<>();
        currentItemList = getArguments().getParcelableArrayList(ARG_NAME);
        shellActivity.setToolbarTitle(R.string.text_item);
        this.itemContainer.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerItemList.setLayoutManager(linearLayoutManager);
        if (currentItemList.size() > 0) {
            prepareItem();
        }


    }

    private void prepareItem() {
        listAdapter = new ItemListAdapter(getContext(), currentItemList);
        recyclerItemList.setAdapter(listAdapter);
        itemCount.setText(currentItemList.size() + " items");
        itemContainer.setRefreshing(false);

    }

    @Override
    public void onRefresh() {
        listAdapter.clear();
        prepareItem();

    }
}
