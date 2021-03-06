package com.sohara.hangman.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;

import com.sohara.hangman.Helper.Utils;

import androidx.appcompat.widget.AppCompatTextView;

public class CustomTextView extends AppCompatTextView {
    int style = Typeface.NORMAL;

    public CustomTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomTextView(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (getTypeface() != null)
            style = getTypeface().getStyle();
        Typeface tf;
        if (getTag().equals("1"))
            tf = Typeface.createFromAsset(getContext().getAssets(), Utils.font1);
        else
            tf = Typeface.createFromAsset(getContext().getAssets(), Utils.font2);
        setTypeface(tf, style);

    }
}
