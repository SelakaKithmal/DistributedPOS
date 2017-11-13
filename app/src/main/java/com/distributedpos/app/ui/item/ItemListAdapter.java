package com.distributedpos.app.ui.item;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.distributedpos.app.R;
import com.distributedpos.app.model.Item;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ItemListViewHolder> {

    private final Context mContext;
    private final List<Item> itemList;
    private final int DIRECTORY_DETAIL = 100;
    private View directoryItemView;

    ItemListAdapter(Context mContext, List<Item> itemList) {
        this.mContext = mContext;
        this.itemList = itemList;
    }

    @Override
    public ItemListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        directoryItemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_list_card, parent, false);

        return new ItemListAdapter.ItemListViewHolder(directoryItemView);
    }

    @Override
    public void onBindViewHolder(ItemListViewHolder holder, int position) {
        Item item = itemList.get(position);

        holder.title.setText(item.getItemName());
        holder.price.setText(item.getItemPrice());

    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    void clear() {
        int size = this.itemList.size();
        this.itemList.clear();
        notifyItemRangeRemoved(0, size);
    }


    class ItemListViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.title)
        TextView title;
        @BindView(R.id.price)
        TextView price;

        ItemListViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

    }
}
