package com.example.abba4u_project;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.xiaopo.flying.sticker.TextSticker;

public class CustomTextSticker extends TextSticker {
    private int txtColor;
    public CustomTextSticker(@NonNull Context context) {
        super(context);
    }

    public CustomTextSticker(@NonNull Context context, @Nullable Drawable drawable) {
        super(context, drawable);
    }

    public int getTextColor(){
        return this.txtColor;
    }

    @NonNull
    @Override
    public TextSticker setTextColor(int color) {
        this.txtColor = color;
        return super.setTextColor(color);
    }
}
