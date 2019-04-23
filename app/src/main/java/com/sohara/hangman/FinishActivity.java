package com.sohara.hangman;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;

import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.sohara.hangman.Data.DataContract;
import com.sohara.hangman.Helper.PersianNumber;
import com.sohara.hangman.Helper.Utils;
import com.sohara.hangman.Models.PrepareWord;
import com.sohara.hangman.Models.Word;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class FinishActivity
        extends AppCompatActivity {
    Button bFindMore;
    Button bNextPuzzle;
    Button bRate1;
    Button bRate2;
    boolean correct;
    SharedPreferences.Editor editor;
    Typeface font;
    LinearLayout llRate;
    // private AdView mAdView;
    int no_correct;
    int no_played;
    int no_score;
    SharedPreferences prefs;
    int rating = 0;
    int score;
    int showAds = 3;
    TextView tvAnswer;
    TextView tvHint;
    TextView tvRate;
    TextView tvScore;
    TextView tvSolved;
    TextView tvStatus;
    TextView tvTranslation;
    Word word;
    PersianNumber persianNumber;
    ImageView statusImage;
    String selectedLanguage;
    String category;

    private void nextPuzzle() {
        startActivity(new Intent(getApplicationContext(), GameActivity.class));
        finish();
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void onBackPressed() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setMessage(getString(R.string.exit_message)).setCancelable(false).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.dismiss();
                finish();
                overridePendingTransition(R.anim.tooltip_enter, R.anim.tooltip_exit);
            }
        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.cancel();
            }
        });
        localBuilder.create().show();
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        selectedLanguage = getSharedPreferences("StartActivity", MODE_PRIVATE).getString("language", StartActivity.language[1]);
        if (selectedLanguage.equals(StartActivity.language[1])) {
            Utils.changeLocale(this, StartActivity.languageCodes[1]);
        } else {
            Utils.changeLocale(this, StartActivity.languageCodes[0]);
        }
        Utils.forceLtrIfSupported(this);
        setContentView(R.layout.activity_finish);
        Intent intent = getIntent();

        init(intent);


        if (correct) {
            tvStatus.setText(getString(R.string.correct));
            statusImage.setVisibility(View.VISIBLE);
            tvHint.setVisibility(View.VISIBLE);
            if (((no_correct > 5) || (prefs.getInt("rate_delay", -1) == no_correct)) && !prefs.getBoolean("rate", false)) {
                llRate.setVisibility(View.VISIBLE);
            }
        } else {
            statusImage.setVisibility(View.INVISIBLE);
            tvStatus.setText(getString(R.string.wrong));
            tvHint.setVisibility(View.GONE);
        }

        tvAnswer.setText(persianNumber.toPersianNumber(getString(R.string.word) + " " + PrepareWord.getWordByLanguage(word)));
        tvTranslation.setText(persianNumber.toPersianNumber(getString(R.string.translate) + ": " + PrepareWord.getTranslation(word).toLowerCase()));
        tvSolved.setText(persianNumber.toPersianNumber(getString(R.string.solved_puzzles) + " " + no_correct));
        tvScore.setText(persianNumber.toPersianNumber(getString(R.string.score) + " " + score));
        bNextPuzzle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                bNextPuzzle.setClickable(false);
                nextPuzzle();
            }
        });

        bRate1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {

                llRate.setVisibility(View.INVISIBLE);
                editor = getSharedPreferences("Start", 0).edit();
                editor.putInt("rate_delay", no_correct + 10);
                editor.apply();

            }
        });
        bRate2.setOnClickListener(new View.OnClickListener() {
            public void onClick(View paramAnonymousView) {
                if (rating == 0) {
                    rating = 11;
                    tvRate.setText(getString(R.string.rate_if_enjoy));
                    bRate1.setText(getString(R.string.no_thanks));
                    bRate2.setText(getString(R.string.ok));
                } else if (rating == 11) {
                    try {
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(
                                "https://play.google.com/store/apps/details?id=" + getPackageName()));
                        intent.setPackage("com.android.vending");
                        startActivity(intent);
                    } catch (Exception e) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + getPackageName())));
                    }
                    editor = getSharedPreferences("Start", 0).edit();
                    editor.putBoolean("rate", true);
                    editor.apply();
                }

            }
        });
//                mAdView = ((AdView)findViewById(2131165210));
//                paramBundle = new AdRequest.Builder().addTestDevice("041422AA685448ADC53C7E51B28FC5D8").build();
//                mAdView.loadAd(paramBundle);
        //  return;


    }

    @Override
    protected void onResume() {
        super.onResume();
        bNextPuzzle.setClickable(true);
    }

    private void init(Intent intent) {
        persianNumber = new PersianNumber(this);
        word = intent.getParcelableExtra("word");
        correct = intent.getExtras().getBoolean("correct");
        score = intent.getExtras().getInt("score");
        prefs = getSharedPreferences("StartActivity", MODE_PRIVATE);
        no_score = prefs.getInt("no_score", 0);
        no_correct = prefs.getInt("no_correct", 0);
        no_played = prefs.getInt("no_played", 0);
        bNextPuzzle = findViewById(R.id.bNextPuzzle);
        bFindMore = findViewById(R.id.bFindMore);
        llRate = findViewById(R.id.llRate);
        tvStatus = findViewById(R.id.tvStatus);
        tvAnswer = findViewById(R.id.tvAnswer);
        tvSolved = findViewById(R.id.tvSolved);
        tvScore = findViewById(R.id.tvScore);
        tvTranslation = findViewById(R.id.tvTranslation);
        tvHint = findViewById(R.id.tvHint);
        bRate1 = findViewById(R.id.bRate1);
        bRate2 = findViewById(R.id.bRate2);
        tvRate = findViewById(R.id.tvRate);
        statusImage = findViewById(R.id.status_image);
        statusImage.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        tvHint.setText(persianNumber.toPersianNumber(getString(R.string.new_hint)));
        initAdMob();
    }

    AdView mAdView;
    private void initAdMob() {

        mAdView = findViewById(R.id.adView);
        //if (isVisibleToUser) {
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        mAdView.setLayerType(AdView.LAYER_TYPE_SOFTWARE, null); //instead of LAYER_TYPE_HARDWARE
    }
}

