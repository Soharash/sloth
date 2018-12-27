package com.soharash.hangman.CustomViews;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatButton;
import android.util.AttributeSet;

import com.soharash.hangman.Helper.Utils;

public class CustomButton extends AppCompatButton {
    int style = Typeface.NORMAL;

    public CustomButton(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CustomButton(Context context) {
        super(context);
        init();
    }

    private void init() {
        if (getTypeface() != null)
            style = getTypeface().getStyle();
        Typeface tf;
        if (getTag().equals(1))
            tf = Typeface.createFromAsset(getContext().getAssets(), Utils.font1);
        else
            tf = Typeface.createFromAsset(getContext().getAssets(), Utils.font2);
        setTypeface(tf, style);

    }
}
