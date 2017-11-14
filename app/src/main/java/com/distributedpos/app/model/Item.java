package com.distributedpos.app.model;


import android.os.Parcel;
import android.os.Parcelable;


public class Item implements Parcelable {

    private String itemName;
    private String itemPrice;
    private String itemCode;
    private String itemId;

    public Item(Parcel in) {
        super();
        readFromParcel(in);
    }

    public Item(String itemId, String itemCode, String itemName, String itemPrice) {
        this.itemId = itemId;
        this.itemCode = itemCode;
        this.itemName = itemName;
        this.itemPrice = itemPrice;
    }


    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getItemPrice() {
        return itemPrice;
    }

    public void setItemPrice(String itemPrice) {
        this.itemPrice = itemPrice;
    }

    public String getItemCode() {
        return itemCode;
    }

    public void setItemCode(String itemCode) {
        this.itemCode = itemCode;
    }

    public String getItemId() {
        return itemId;
    }

    public void setItemId(String itemId) {
        this.itemId = itemId;
    }

    public static final Parcelable.Creator<Item> CREATOR = new Parcelable.Creator<Item>() {
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        public Item[] newArray(int size) {

            return new Item[size];
        }

    };

    public void readFromParcel(Parcel in) {
        itemName = in.readString();
        itemPrice = in.readString();
        itemCode = in.readString();
        itemId = in.readString();

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(itemName);
        parcel.writeString(itemPrice);
        parcel.writeString(itemCode);
        parcel.writeString(itemId);

    }
}
