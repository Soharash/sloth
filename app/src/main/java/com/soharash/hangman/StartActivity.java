package com.soharash.hangman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.soharash.hangman.Data.DataContract;

import java.util.ArrayList;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    Button bCategories;
    //    Button bDifficulty;
    Button bMultiplayer;
    Button bSettings;
    Button bStart;
    Button bLanguage;
    String[] categories = DataContract.categories;
    public static String[] language = new String[]{"English", "فارسی", "Random - تصادفی"};

    //    String[] difficulty = { "Easy", "Medium", "Hard" };
    SharedPreferences.Editor editor;
    Typeface font;
    ImageButton ibNoSound;
    ImageButton ibSound;
    SharedPreferences prefs;
    String selectedCategory;
    String selectedLanguage;
//    TextView tvDifficulty;


    public void onBackPressed() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int paramAnonymousInt) {
                dialogInterface.dismiss();
                finish();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int paramAnonymousInt) {
                dialogInterface.cancel();
            }
        });
        localBuilder.create().show();
    }

    AlertDialog.Builder builder;

    public void onClick(View view) {
        switch (view.getId()) {
            default:
                return;
            case R.id.bCategories:
                builder = new AlertDialog.Builder(this);
                builder.setTitle(categories.length + " Free Categories:");
                builder.setItems(categories, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                        selectedCategory = categories[paramAnonymousInt];
                        bCategories.setText(selectedCategory);
                        editor = getSharedPreferences("StartActivity", 0).edit();
                        editor.putString("category", categories[paramAnonymousInt]);
                        editor.apply();
                        editor.putString("item_category", categories[paramAnonymousInt]);
                    }
                });
                builder.show();
                return;
//            case R.id.bDifficulty:
//                builder = new AlertDialog.Builder(this);
//                builder.setTitle("Difficulty");
//                builder.setItems(difficulty, new DialogInterface.OnClickListener()
//                {
//                    public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt)
//                    {
//                        StartActivity.bDifficulty.setText(StartActivity.difficulty[paramAnonymousInt]);
//                        StartActivity.editor = StartActivity.getSharedPreferences("StartActivity", 0).edit();
//                        StartActivity.editor.putString("difficulty", StartActivity.difficulty[paramAnonymousInt]);
//                        StartActivity.editor.apply();
//                    }
//                });
//                builder.show();
//                return;
            case R.id.bStart:
                //  pushInfo();
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
                return;
            case R.id.ibSound:
                if (prefs.getBoolean("sound", true)) {
                    ibNoSound.setVisibility(View.VISIBLE);
                    editor = getSharedPreferences("StartActivity", MODE_PRIVATE).edit();
                    editor.putBoolean("sound", false);
                    editor.apply();
                } else {
                    ibNoSound.setVisibility(View.INVISIBLE);
                    editor = getSharedPreferences("StartActivity", MODE_PRIVATE).edit();
                    editor.putBoolean("sound", true);
                    editor.apply();
                }
                break;
            case R.id.bLanguage:
                builder = new AlertDialog.Builder(this);
                builder.setTitle("Language:");
                builder.setItems(language, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int index) {
                        selectedLanguage = language[index];
                        bLanguage.setText(selectedLanguage);
                        editor = getSharedPreferences("StartActivity", MODE_PRIVATE).edit();
                        editor.putString("language", language[index]);
                        editor.apply();
                        //  editor.putString("item_category", language.get(index));
                    }
                });
                builder.show();
                break;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // OneSignal.startInit(this).inFocusDisplaying(OneSignal.OSInFocusDisplayOption.Notification).unsubscribeWhenNotificationsAreDisabled(true).init();
        setContentView(R.layout.activity_start);
        init();

        ibSound.setOnClickListener(this);
        if (prefs.getBoolean("sound", true)) {
            ibNoSound.setVisibility(View.INVISIBLE);
        }
        for (; ; ) {
            bCategories.setOnClickListener(this);
            bLanguage.setOnClickListener(this);
//            bDifficulty.setOnClickListener(this);
            bStart.setOnClickListener(this);
            bMultiplayer.setOnClickListener(this);
            bSettings.setOnClickListener(this);
            selectedCategory = prefs.getString("category", categories[0]);
            bCategories.setText(selectedCategory);
            selectedLanguage = prefs.getString("language", language[0]);
            bLanguage.setText(selectedLanguage);
//            String difficulty = prefs.getString("difficulty", difficulty[0]);
//            bDifficulty.setText(difficulty);

            return;
            //ibNoSound.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        prefs = getSharedPreferences("StartActivity", 0);
//        FirstStart.shuffleWords(getApplicationContext());
//        tvDifficulty = ((TextView)findViewById(R.id.tvDifficulty));
        bCategories = findViewById(R.id.bCategories);
        bLanguage = findViewById(R.id.bLanguage);
        bCategories.setText(categories[0]);
//        bDifficulty = ((Button)findViewById(R.id.bDifficulty));
//        bDifficulty.setText(difficulty[1]);
        bStart = findViewById(R.id.bStart);
        bMultiplayer = findViewById(R.id.bMultiplayer);
        bSettings = findViewById(R.id.bSettings);
        ibSound = findViewById(R.id.ibSound);
        ibNoSound = findViewById(R.id.ibSound);
    }
}
