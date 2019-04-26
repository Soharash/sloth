package com.sohara.hangman;

import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;


import com.plattysoft.leonids.ParticleSystem;
import com.sohara.hangman.Helper.Utils;

import androidx.appcompat.app.AppCompatActivity;

public class FinishCategoryActivity extends AppCompatActivity {

    //    GifImageView gif;
    Button returnToHome;
    View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_finish);

        init();
        Utils.forceLtrIfSupported(this);
//        DrawableImageViewTarget imageViewTarget = new DrawableImageViewTarget(gif);
//
//        gif.setVisibility(View.VISIBLE);
//        Glide.with(getApplicationContext()).load(R.raw.category_finish).into(imageViewTarget);


        ViewTreeObserver viewTreeObserver = rootView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    rootView.getViewTreeObserver().removeOnGlobalLayoutListener(this);

                    new ParticleSystem(FinishCategoryActivity.this, 100, R.drawable.circle_gold, 10000)
                            .setSpeedModuleAndAngleRange(0f, 0.3f, 10, 10)
                            .setRotationSpeed(144)
                            .setAcceleration(0.00005f, 90)
                            .emit(findViewById(R.id.emiter_top_left), 4);

//        View view1 = findViewById(R.id.view1);
                    new ParticleSystem(FinishCategoryActivity.this, 100, R.drawable.circle_red, 10000)
                            .setSpeedModuleAndAngleRange(0f, 0.3f, 190, 190)
                            .setRotationSpeed(144)
                            .setAcceleration(0.00005f, 90)
                            .emit(findViewById(R.id.emiter_top_right), 4);
                }
            });
        }
        returnToHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });
    }


    private void init() {
        rootView = findViewById(R.id.content);

//        gif = findViewById(R.id.gif);
        returnToHome = findViewById(R.id.bReturn);
    }

    public void onBackPressed() {

        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);

    }
}
