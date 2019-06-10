package com.sohara.hangman;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;

import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sohara.hangman.Data.DataContract;
import com.sohara.hangman.Data.DatabaseHelper;
import com.sohara.hangman.Helper.PersianNumber;
import com.sohara.hangman.Helper.Utils;

import java.util.ArrayList;
import java.util.ResourceBundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    Button bCategories;
    //    Button bDifficulty;
    Button bMultiplayer;
    Button bSettings;
    Button bStart;
    Button bLanguage;
    public static ArrayList<String> tableNames;
    String[] categoryNames;
    public static String[] language = new String[]{"english", "فارسی"};
    public static String[] languageCodes = new String[]{"en", "fa"};
    SharedPreferences.Editor editor;
    ImageButton ibNoSound;
    ImageButton ibSound;
    SharedPreferences prefs;
    String selectedCategory;
    String selectedLanguage;
    PersianNumber persianNumber;
    TextView tvPoints;
    String TAG = "StartActivity";
    Resources currentResources;
    LinearLayout privacyPolicyLayout;


    public void onBackPressed() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setMessage(currentResources.getString(R.string.exit_message)).setCancelable(false).setPositiveButton(currentResources.getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int paramAnonymousInt) {
                dialogInterface.dismiss();
                finish();
            }
        }).setNegativeButton(currentResources.getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialogInterface, int paramAnonymousInt) {
                dialogInterface.cancel();
            }
        });
        localBuilder.create().show();
    }

    AlertDialog.Builder builder;


    private void showCategoriesDialog() {
        final Dialog dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.category_dialog, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        ListView listView = view.findViewById(R.id.list_view);
        TextView textView = view.findViewById(R.id.title);
        textView.setText(persianNumber.toPersianNumber(tableNames.size() + " " + currentResources.getString(R.string.category)));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, categoryNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoryNames[position];
                bCategories.setText(selectedCategory);
                editor = getSharedPreferences("StartActivity", 0).edit();
                editor.putString("category", tableNames.get(position));
                editor.apply();
                editor.putString("item_category", tableNames.get(position));
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void showLanguageDialog() {
        final Dialog dialog = new Dialog(this);
        View view = getLayoutInflater().inflate(R.layout.category_dialog, null);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            dialog.getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }

        ListView listView = view.findViewById(R.id.list_view);
        TextView textView = view.findViewById(R.id.title);
        textView.setText(currentResources.getString(R.string.language));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, language);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedLanguage = language[position];
                applyLanguage(position);
                dialog.dismiss();
            }
        });
        dialog.setContentView(view);
        dialog.show();
    }

    private void applyLanguage(int position) {
        String beforeLanguage = getSharedPreferences("StartActivity", MODE_PRIVATE).getString("language", language[1]);
        editor = getSharedPreferences("StartActivity", MODE_PRIVATE).edit();
        editor.putString("language", language[position]);
        editor.apply();
        if (!beforeLanguage.equals(selectedLanguage)) {
            Intent intent = new Intent(this, StartActivity.class);
            startActivity(intent);
            finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bStart.setClickable(true);
        prefs = getSharedPreferences("StartActivity", 0);
        int no_score = prefs.getInt("no_score", 0);
        tvPoints.setText(currentResources.getString(R.string.score) + " " + persianNumber.toPersianNumber(String.valueOf(no_score)));
    }

    public void onClick(View view) {
        switch (view.getId()) {
            default:
                return;
            case R.id.privacy_policy_layout:
                Intent intent = new Intent(this , PrivacyPolicyActivity.class);
                startActivity(intent);
                break;
            case R.id.bCategories:
                showCategoriesDialog();
                return;

            case R.id.bStart:
                //  pushInfo();
                bStart.setClickable(false);
                startActivity(new Intent(getApplicationContext(), GameActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
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
                showLanguageDialog();
                break;
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String currentLanguage = getSharedPreferences("StartActivity", MODE_PRIVATE).getString("language", language[1]);
        if (currentLanguage.equals(language[1])) {
            Utils.changeLocale(this, languageCodes[1]);
            currentResources = Utils.getLocalizedResources(this , languageCodes[1]);
        } else {
            Utils.changeLocale(this, languageCodes[0]);
            currentResources = Utils.getLocalizedResources(this , languageCodes[0]);
        }
//        Utils.forceLtrIfSupported(this);
        setContentView(R.layout.activity_start);
//        String currentLanguage = getSharedPreferences("StartActivity", MODE_PRIVATE).getString("language", language[1]);
        if (currentLanguage.equals(language[1])) {
            Utils.changeLocale(this, languageCodes[1]);
        } else {
            Utils.changeLocale(this, languageCodes[0]);
        }
        Utils.forceLtrIfSupported(this);
        init();

        tableNames = DatabaseHelper.getAllTables(this);

        ibSound.setOnClickListener(this);
        if (prefs.getBoolean("sound", true)) {
            ibNoSound.setVisibility(View.INVISIBLE);
        }

        privacyPolicyLayout.setOnClickListener(this);
        bCategories.setOnClickListener(this);
        bLanguage.setOnClickListener(this);
//            bDifficulty.setOnClickListener(this);
        bStart.setOnClickListener(this);
        bMultiplayer.setOnClickListener(this);
        bSettings.setOnClickListener(this);
        selectedCategory = prefs.getString("category", tableNames.get(0));


        bCategories.setText(categoryNames[tableNames.indexOf(selectedCategory)]);
        selectedLanguage = prefs.getString("language", language[1]);
        bLanguage.setText(selectedLanguage);
    }

    private void init() {
        DatabaseHelper databaseHelper = new DatabaseHelper(this);
        privacyPolicyLayout = findViewById(R.id.privacy_policy_layout);
        persianNumber = new PersianNumber(this);
        categoryNames = getResources().getStringArray(R.array.categories);
        prefs = getSharedPreferences("StartActivity", 0);
        bCategories = findViewById(R.id.bCategories);
        bLanguage = findViewById(R.id.bLanguage);
        bCategories.setText(categoryNames[0]);
        bStart = findViewById(R.id.bStart);
        bMultiplayer = findViewById(R.id.bMultiplayer);
        bSettings = findViewById(R.id.bSettings);
        ibSound = findViewById(R.id.ibSound);
        ibNoSound = findViewById(R.id.ibClose);
        tvPoints = findViewById(R.id.tvPoints);

        initAdMob();
    }

    AdView mAdView;

    private void initAdMob() {
        MobileAds.initialize(getApplicationContext(), getString(R.string.admob_app_id));
        mAdView = findViewById(R.id.adView);
        //if (isVisibleToUser) {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setLayerType(AdView.LAYER_TYPE_SOFTWARE, null); //instead of LAYER_TYPE_HARDWARE
    }
}
