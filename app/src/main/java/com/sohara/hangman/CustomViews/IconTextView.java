package com.sohara.hangman.CustomViews;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;

import androidx.appcompat.widget.AppCompatTextView;

import com.sohara.hangman.Helper.FontTypeface;

public class IconTextView extends AppCompatTextView {

    private Context context;

    public IconTextView(Context context) {
        super(context);
        this.context = context;
        createView();
    }

    public IconTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        createView();
    }

    @Override
    public void setTag(Object tag) {
        super.setTag(tag);
        setTypeface(FontTypeface.get(getTag().toString(), context));
    }

    private void createView(){
        setGravity(Gravity.CENTER);
        setTypeface(FontTypeface.get(getTag().toString(), context));
    }
}