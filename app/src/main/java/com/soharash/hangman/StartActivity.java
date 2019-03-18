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
import com.soharash.hangman.Helper.PersianNumber;
import com.soharash.hangman.Helper.Utils;

import java.util.ArrayList;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    Button bCategories;
    //    Button bDifficulty;
    Button bMultiplayer;
    Button bSettings;
    Button bStart;
    Button bLanguage;
    String[] categories = DataContract.categories;
    String[] categoriesPersianNames;
    public static String[] language = new String[]{"English", "فارسی", "Random - تصادفی"};

    //    String[] difficulty = { "Easy", "Medium", "Hard" };
    SharedPreferences.Editor editor;
    Typeface font;
    ImageButton ibNoSound;
    ImageButton ibSound;
    SharedPreferences prefs;
    String selectedCategory;
    String selectedLanguage;
    PersianNumber persianNumber;
//    TextView tvDifficulty;



    public void onBackPressed() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setMessage(getString(R.string.exit_message)).setCancelable(false).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int paramAnonymousInt) {
                dialogInterface.dismiss();
                finish();
            }
        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
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
                builder.setTitle(persianNumber.toPersianNumber(categories.length + getString(R.string.category)));
                builder.setItems(categoriesPersianNames, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialogInterface, int index) {
                        selectedCategory = categoriesPersianNames[index];
                        bCategories.setText(selectedCategory);
                        editor = getSharedPreferences("StartActivity", 0).edit();
                        editor.putString("category", categories[index]);
                        editor.apply();
                        editor.putString("item_category", categories[index]);
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
            bCategories.setText(Utils.getStringResourceID(this , selectedCategory.toLowerCase()));
            selectedLanguage = prefs.getString("language", language[1]);
            bLanguage.setText(selectedLanguage);
//            String difficulty = prefs.getString("difficulty", difficulty[0]);
//            bDifficulty.setText(difficulty);

            return;
            //ibNoSound.setVisibility(View.VISIBLE);
        }
    }

    private void init() {
        persianNumber = new PersianNumber();
        categoriesPersianNames = getResources().getStringArray(R.array.categories);
        prefs = getSharedPreferences("StartActivity", 0);
//        FirstStart.shuffleWords(getApplicationContext());
//        tvDifficulty = ((TextView)findViewById(R.id.tvDifficulty));
        bCategories = findViewById(R.id.bCategories);
        bLanguage = findViewById(R.id.bLanguage);
        bCategories.setText(categoriesPersianNames[0]);
//        bDifficulty = ((Button)findViewById(R.id.bDifficulty));
//        bDifficulty.setText(difficulty[1]);
        bStart = findViewById(R.id.bStart);
        bMultiplayer = findViewById(R.id.bMultiplayer);
        bSettings = findViewById(R.id.bSettings);
        ibSound = findViewById(R.id.ibSound);
        ibNoSound = findViewById(R.id.ibClose);
    }
}
