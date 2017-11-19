package com.distributedpos.app.ui.item;


import android.app.Activity;
import android.app.Dialog;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import com.distributedpos.app.R;
import com.distributedpos.app.model.Item;
import com.distributedpos.app.ui.ShellActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class ItemList extends Fragment implements SwipeRefreshLayout.OnRefreshListener,
        ItemListAdapter.OnItemDelete {

    private ShellActivity shellActivity;
    private static final String ARG_NAME = "item";
    @BindView(R.id.item_recycler_view)
    RecyclerView recyclerItemList;
    @BindView(R.id.item_count)
    TextView itemCount;
    @BindView(R.id.item_container)
    SwipeRefreshLayout itemContainer;
    @BindView(R.id.total_price)
    TextView totalPrice;
    private ItemListAdapter listAdapter;
    private ArrayList<Item> currentItemList;
    public final static int WHITE = 0xFFFFFFFF;
    public final static int BLACK = 0xFF000000;
    public final static int WIDTH = 400;
    public final static int HEIGHT = 400;

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
        shellActivity.setToolbarTitle(getString(R.string.text_item));
        this.itemContainer.setOnRefreshListener(this);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerItemList.setLayoutManager(linearLayoutManager);
        if (currentItemList.size() > 0) {
            prepareItem();
        }


    }

    @OnClick(R.id.generateCode)
    void generateCode() {
        currentItemList = new ArrayList<>();
        currentItemList.addAll(listAdapter.currentList());
        List<String> ids = new ArrayList<>();
        for (int i = 0; i < currentItemList.size(); i++) {
            ids.add(currentItemList.get(i).getItemId());
        }
        String allItems = android.text.TextUtils.join(",", ids);
        try {
            Bitmap bitmap = encodeAsBitmap("A, " + allItems);

            QrDialog alert = new QrDialog(getContext(), bitmap,
                    Dialog::dismiss);
            alert.show();
            if (alert.getWindow() != null) {
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                lp.copyFrom(alert.getWindow().getAttributes());
                lp.width = WindowManager.LayoutParams.MATCH_PARENT;
                lp.height = WindowManager.LayoutParams.MATCH_PARENT;
                alert.getWindow().setAttributes(lp);
            }

        } catch (WriterException e) {
            e.printStackTrace();
        }
    }

    Bitmap encodeAsBitmap(String str) throws WriterException {
        BitMatrix result;
        try {
            result = new MultiFormatWriter().encode(str, BarcodeFormat.QR_CODE, WIDTH, HEIGHT, null);
        } catch (IllegalArgumentException iae) {
            // Unsupported format
            return null;
        }

        int width = result.getWidth();
        int height = result.getHeight();
        int[] pixels = new int[width * height];
        for (int y = 0; y < height; y++) {
            int offset = y * width;
            for (int x = 0; x < width; x++) {
                pixels[offset + x] = result.get(x, y) ? BLACK : WHITE;
            }
        }

        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        bitmap.setPixels(pixels, 0, width, 0, 0, width, height);
        return bitmap;
    }

    private void prepareItem() {
        listAdapter = new ItemListAdapter(getContext(), currentItemList, this);
        recyclerItemList.setAdapter(listAdapter);
        resetValues();
        itemContainer.setRefreshing(false);

    }

    @Override
    public void onRefresh() {
        listAdapter.clear();
        prepareItem();

    }

    @Override
    public void onItemDelete(ArrayList<Item> itemList) {
        currentItemList = new ArrayList<>();
        currentItemList.addAll(itemList);
        resetValues();
    }

    private void resetValues() {
        itemCount.setText(currentItemList.size() + " items");
        int sum = 0;
        for (int i = 0; i < currentItemList.size(); i++) {
            sum = sum + Integer.parseInt(currentItemList.get(i).getItemPrice());
        }
        totalPrice.setText(String.valueOf(sum) + " Rs");
    }
}
