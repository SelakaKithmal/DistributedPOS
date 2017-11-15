package com.distributedpos.app.ui.item;


import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.distributedpos.app.R;

public class QrDialog extends Dialog {

    private final OnClickListener onClickListener;
    private Bitmap qrBitmap;


    QrDialog(@NonNull Context context, Bitmap qrBitmap, OnClickListener onClickListener) {
        super(context);
        this.qrBitmap = qrBitmap;
        this.onClickListener = onClickListener;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.setCanceledOnTouchOutside(false);
        this.setCancelable(false);
        Window window = this.getWindow();
        if (null != window) {
            window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            window.getAttributes().windowAnimations = R.style.DialogAnimation;
        }
        this.setContentView(R.layout.qr_dialog_layout);
        ImageView qrView = this.findViewById(R.id.qr_image);
        qrView.setImageBitmap(qrBitmap);
        ImageButton buttonClose = this.findViewById(R.id.button_close);
        buttonClose.setOnClickListener(view -> this.onClickListener.buttonCloseOnClick(this));
    }


    interface OnClickListener {
        void buttonCloseOnClick(QrDialog alert);
    }
}
