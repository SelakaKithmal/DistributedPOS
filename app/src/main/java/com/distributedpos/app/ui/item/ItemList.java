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
import java.util.List;

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
    private List<Item> itemList;

    public static ItemList newInstance(String items) {
        ItemList itemList = new ItemList();
        Bundle args = new Bundle();
        args.putString(ARG_NAME, items);
        itemList.setArguments(args);
        return itemList;
    }

    public ItemList() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
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
        itemList= new ArrayList<>();
        shellActivity.setToolbarTitle(R.string.text_item);
        this.itemContainer.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerItemList.setLayoutManager(linearLayoutManager);

        prepareAlbums();

    }

    private void prepareAlbums() {

        Item a = new Item("True Romance", "130 Rs");
        itemList.add(a);

        a = new Item("Xscpae", "230 Rs");
        itemList.add(a);

        a = new Item("Maroon 5", "430 Rs");
        itemList.add(a);

        a = new Item("Born to Die", "180 Rs");
        itemList.add(a);

        a = new Item("Honeymoon", "170 Rs");
        itemList.add(a);

        a = new Item("I Need a Doctor", "190 Rs");
        itemList.add(a);

        a = new Item("Loud", "1730 Rs");
        itemList.add(a);

        a = new Item("Legend", "1370 Rs");
        itemList.add(a);

        a = new Item("Hello", "130 Rs");
        itemList.add(a);

        a = new Item("Greatest Hits", "130 Rs");
        itemList.add(a);

        listAdapter = new ItemListAdapter(getContext(), itemList);
        recyclerItemList.setAdapter(listAdapter);

        itemCount.setText(itemList.size() + " items");

        itemContainer.setRefreshing(false);


    }

    @Override
    public void onRefresh() {
        listAdapter.clear();

    }
}
