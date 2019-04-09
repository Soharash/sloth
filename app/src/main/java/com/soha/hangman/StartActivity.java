package com.soha.hangman;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ListView;
import android.widget.TextView;

import com.soha.hangman.Data.DataContract;
import com.soha.hangman.Helper.PersianNumber;
import com.soha.hangman.Helper.Utils;

import org.w3c.dom.Text;

import java.util.ArrayList;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class StartActivity extends AppCompatActivity implements View.OnClickListener {
    Button bCategories;
    //    Button bDifficulty;
    Button bMultiplayer;
    Button bSettings;
    Button bStart;
    Button bLanguage;
    String[] categories = DataContract.categories;
    String[] categoriesPersianNames;
    public static String[] language = new String[]{"english", "فارسی"};
    public static String[] languageCodes = new String[]{"en", "fa"};

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
        textView.setText(persianNumber.toPersianNumber(categories.length + " " + getString(R.string.category)));
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.list_item, categoriesPersianNames);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedCategory = categoriesPersianNames[position];
                bCategories.setText(selectedCategory);
                editor = getSharedPreferences("StartActivity", 0).edit();
                editor.putString("category", categories[position]);
                editor.apply();
                editor.putString("item_category", categories[position]);
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
        textView.setText(getString(R.string.language));
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
    }

    public void onClick(View view) {
        switch (view.getId()) {
            default:
                return;
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
        String beforeLanguage = getSharedPreferences("StartActivity", MODE_PRIVATE).getString("language", language[1]);
        if (beforeLanguage.equals(language[1])) {
//
            Utils.changeLocale(this, languageCodes[1]);
        } else {
//
            Utils.changeLocale(this, languageCodes[0]);
        }
        Utils.forceLtrIfSupported(this);
        setContentView(R.layout.activity_start);
        init();

        ibSound.setOnClickListener(this);
        if (prefs.getBoolean("sound", true)) {
            ibNoSound.setVisibility(View.INVISIBLE);
        }

        bCategories.setOnClickListener(this);
        bLanguage.setOnClickListener(this);
//            bDifficulty.setOnClickListener(this);
        bStart.setOnClickListener(this);
        bMultiplayer.setOnClickListener(this);
        bSettings.setOnClickListener(this);
        selectedCategory = prefs.getString("category", categories[0]);
        bCategories.setText(Utils.getStringResourceID(this, selectedCategory.toLowerCase()));
        selectedLanguage = prefs.getString("language", language[1]);
        bLanguage.setText(selectedLanguage);
    }

    private void init() {
        persianNumber = new PersianNumber(this);
        categoriesPersianNames = getResources().getStringArray(R.array.categories);
        prefs = getSharedPreferences("StartActivity", 0);
        bCategories = findViewById(R.id.bCategories);
        bLanguage = findViewById(R.id.bLanguage);
        bCategories.setText(categoriesPersianNames[0]);
        bStart = findViewById(R.id.bStart);
        bMultiplayer = findViewById(R.id.bMultiplayer);
        bSettings = findViewById(R.id.bSettings);
        ibSound = findViewById(R.id.ibSound);
        ibNoSound = findViewById(R.id.ibClose);


    }
}
