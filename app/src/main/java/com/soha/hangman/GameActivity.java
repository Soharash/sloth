package com.soha.hangman;

import android.app.AlertDialog;
import android.app.AppComponentFactory;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.soha.hangman.Data.DataContract;
import com.soha.hangman.Helper.PersianNumber;
import com.soha.hangman.Helper.Utils;
import com.soha.hangman.Models.PrepareWord;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    Button bHint;

    String[] categories = DataContract.categories;
    String[] categoriesPersianNames;
    String category = "";
    int counter = 0;
    SharedPreferences.Editor editor;
    boolean endState = true;

    Handler h;
    String hiddenWord = "";
    String hint;
    List<String> hintList;
    ImageView ivHangman;
    MediaPlayer key;
    MediaPlayer lose;
    //    private AdView mAdView;
//    private InterstitialAd mInterstitialAd;
    int no_correct;
    int no_hint;
    int no_played;
    int no_score;
    SharedPreferences prefs;
    Runnable r;
    boolean rate = false;
    int score = 0;
    int secret = 0;
    int share = 0;
    int showAds = 3;
    boolean sound;
    int time = 0;
    TextView tvCategory;
    TextView tvScore;
    TextView tvTime;
    TextView tvWord;
    MediaPlayer win;
    String word = "";
    String TAG = "GameActivity";
    String selectedLanguage;
    PersianNumber persianNumber;
    LinearLayout keyboard;
    private void checkLetter(String letter) {
        Log.i(TAG, "checkLetter: word " + word);
        int i = -1;
        for (; ; ) {
            i = word.indexOf(letter, i + 1);
            if (i == -1) {
                break;
            }
            hiddenWord = (hiddenWord.substring(0, i) + letter + hiddenWord.substring(i + 1));
        }
        if (word.contains(letter)) {
            score += 10;
            hint = hint.replace(letter, "");
            prepareHint();
            if (sound) {
                key.start();
            }
            tvScore.setText("" + score);
            tvWord.setText(hiddenWord);
            if (word.equals(hiddenWord)) {
                if (120 - time > 0) {
                    score += 120 - time;
                }
                score += 30;
                endState = true;
//                if (
////                        (!mInterstitialAd.isLoaded()) ||
//                                (no_played % showAds != 0)) {
                finishGame(true);
                // break ;
                return;
//                }
//                mInterstitialAd.show();
            }
//            tvScore.setText("" + score);
//            tvWord.setText(hiddenWord);
            return;
        }
//    for (;;)
        //   {

        counter += 1;
        displayHangman(counter);
        Log.i(TAG, "checkLetter: display hangman " + counter);
        if (!sound) {
            return;
        }
        key.start();
        return;

//    }
    }

    private void displayHangman(int paramInt) {
        switch (paramInt) {
            default:
                ivHangman.setImageResource(R.drawable.h02_1);
                break;
            case 1:
                ivHangman.setImageResource(R.drawable.h02_2);
                break;
            case 2:
                ivHangman.setImageResource(R.drawable.h02_3);
                break;
            case 3:
                ivHangman.setImageResource(R.drawable.h02_4);
                break;
            case 4:
                ivHangman.setImageResource(R.drawable.h02_5);
                break;
            case 5:
                ivHangman.setImageResource(R.drawable.h02_6);
                break;
            case 6:
                ivHangman.setImageResource(R.drawable.h02_7);
                endState = false;
                //  ivHangman.setImageResource(R.drawable.h02_holder);
                finishGame(false);
                break;
//                if (
////                (mInterstitialAd.isLoaded()) &&
//                        (no_played % showAds == 0)) {
////            mInterstitialAd.show();
//                    return;
//                }
//            case 7:
//                ivHangman.setImageResource(R.drawable.h02_8);
//                endState = false;
//              //  ivHangman.setImageResource(R.drawable.h02_holder);
//                if (
////                (mInterstitialAd.isLoaded()) &&
//                        (no_played % showAds == 0)) {
////            mInterstitialAd.show();
//                    return;
//                }
//                finishGame(false);
//                return;
        }
        ivHangman.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
//        endState = false;
//        ivHangman.setImageResource(R.drawable.h02_holder);
//        if (
////                (mInterstitialAd.isLoaded()) &&
//                (no_played % showAds == 0)) {
////            mInterstitialAd.show();
//            return;
//        }
//        finishGame(false);
    }

    Intent localIntent;

    private void finishGame(boolean paramBoolean) {
        Log.i(TAG, "finishGame: " + paramBoolean);
        if (paramBoolean) {

            if (sound) {
                win.start();
            }
            pushInfo("correct");
            editor = getSharedPreferences("StartActivity", 0).edit();
            editor.putInt("no_played", no_played + 1);
            editor.putInt("no_correct", no_correct + 1);
            editor.putInt("no_score", no_score + score);
            editor.putInt("no_hint", no_hint + 1);
            editor.apply();
            h.removeCallbacks(r);
            localIntent = new Intent(getApplicationContext(), FinishActivity.class);
            localIntent.putExtra("word", word);
            localIntent.putExtra("score", score);
            localIntent.putExtra("solved", no_correct + 1);
            localIntent.putExtra("correct", true);
            Log.i(TAG, "finishGame: " + score + " " + (score + no_score));
            startActivity(localIntent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            return;
        } else {
            Log.i(TAG, "finishGame: false ");
            if (sound) {
                lose.start();
            }
            pushInfo("wrong");
            editor = getSharedPreferences("StartActivity", 0).edit();
            editor.putInt("no_played", no_played + 1);
            editor.putInt("no_score", no_score + score);
            editor.putInt("no_hint", no_hint);
            editor.apply();
            h.removeCallbacks(r);
            Intent localIntent = new Intent(getApplicationContext(), FinishActivity.class);
            localIntent.putExtra("word", word);
            localIntent.putExtra("correct", false);
            localIntent.putExtra("score", score);
            Log.i(TAG, "finishGame: " + score + " " + (score + no_score));
            startActivity(localIntent);
            finish();
            overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
        }
    }

    private void hideLetter(String paramString) {
        Log.i(TAG, "hideLetter: keyboard ");
       boolean found = false;
        for(int j = 0 ;j < keyboard.getChildCount();j++)
        {

            if(keyboard.getChildAt(j) instanceof LinearLayout)
            {
                LinearLayout row = (LinearLayout) keyboard.getChildAt(j);
                for( int i = 0 ;i < row.getChildCount() ; i++ ) {
                    if (row.getChildAt(i) instanceof Button && ((Button) row.getChildAt(i)).getText().toString().equals(paramString)) {
                        Log.i(TAG, "hideLetter: " + ((Button) row.getChildAt(i)).getText());
                        row.getChildAt(i).setEnabled(false);
                        row.getChildAt(i).setBackgroundResource(R.drawable.keyboard_off);
                        found = true;
                        break;

                    }
                }
                if(found)
                    break;
            }

        }

//
    }

    private void hintLogic() {
        try {
            if (no_hint > 0) {
                no_hint -= 1;
                int i = hintList.size();
                i = new Random().nextInt(i - 1 - 1 + 1) + 1;
                hideLetter((String) hintList.get(i));
                checkLetter((String) hintList.get(i));
                bHint.setText(persianNumber.toPersianNumber(getString(R.string.hint) + " (" + no_hint + ")"));
                if (no_hint == 0) {
                    hintPopup();
                }
            } else {
                Toast.makeText(this, "There is no more hints!", Toast.LENGTH_SHORT).show();
                hintPopup();
                return;
            }
        } catch (Exception localException) {
        }
    }

    AlertDialog.Builder localBuilder;

    private void hintPopup() {
        if (!rate) {
            localBuilder = new AlertDialog.Builder(this);
            localBuilder.setMessage("Rate us with 5 stars and you will get 50 free hints!").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                    share = 1;
                    paramAnonymousDialogInterface.dismiss();
                    //   paramAnonymousDialogInterface = new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=com.klikapp.hangman2"));
                    //  GameActivity.startActivity(paramAnonymousDialogInterface);
                }
            }).setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                    paramAnonymousDialogInterface.cancel();
                }
            });
            localBuilder.create().show();
            return;
        }
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setMessage("Share with friends and get 15 free hints!").setCancelable(false).setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                share = 1;
                paramAnonymousDialogInterface.dismiss();
                //  paramAnonymousDialogInterface = new Intent("android.intent.action.SEND");
//                paramAnonymousDialogInterface.setType("text/plain");
//                paramAnonymousDialogInterface.putExtra("android.intent.extra.SUBJECT", "Try Hangman Free");
//                paramAnonymousDialogInterface.putExtra("android.intent.extra.TEXT", "Check out my new favorite Hangman GameActivity! Try it for free http://bit.ly/GetHangmanFree");
//                GameActivity.startActivity(Intent.createChooser(paramAnonymousDialogInterface, "Share using..."));
            }
        }).setNegativeButton("No, thanks", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.cancel();
            }
        });
        localBuilder.create().show();
    }



    private void prepareHint() {
        hint = hint.replace(" ", "");
        hintList = Arrays.asList(hint.split(""));
    }

    private void pushInfo(String paramString) {
    }

    public void onBackPressed() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setMessage(getString(R.string.exit_message)).setCancelable(false).setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                editor = getSharedPreferences("StartActivity", 0).edit();
                editor.putInt("no_hint", no_hint);
                editor.apply();
                paramAnonymousDialogInterface.dismiss();
                finish();
                overridePendingTransition(R.anim.slide_in_left, R.anim.slide_out_right);
            }
        }).setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.cancel();
            }
        });
        localBuilder.create().show();
    }

    public void onClick(View v) {

        if (v.getId() == R.id.bHint)
            hintLogic();
        else {
            Button button = (Button) v;
            checkLetter(button.getText().toString());
            button.setEnabled(false);
            button.setBackgroundResource(R.drawable.keyboard_off);
        }

    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        init();
//        tvCategory.setOnClickListener(this);
    }

    private void init() {
        keyboard = findViewById(R.id.fa_keyboard);

        ((ImageView) findViewById(R.id.ivHolder)).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_IN);
        categoriesPersianNames = getResources().getStringArray(R.array.categories);
        win = MediaPlayer.create(this, R.raw.sound_win);
        lose = MediaPlayer.create(this, R.raw.sound_lose);
        key = MediaPlayer.create(this, R.raw.sound_correct);
        persianNumber = new PersianNumber();
        prefs = getSharedPreferences("StartActivity", 0);
        selectedLanguage = prefs.getString("language", StartActivity.language[1]);
        no_score = prefs.getInt("no_score", 0);
        no_correct = prefs.getInt("no_correct", 0);
        no_played = prefs.getInt("no_played", 0);
        no_hint = prefs.getInt("no_hint", 20);
        sound = prefs.getBoolean("sound", true);
        rate = prefs.getBoolean("rate", false);
        showAds = ((int) prefs.getLong("display_number_free", 4L));
        ivHangman = ((ImageView) findViewById(R.id.ivHangman));
        bHint = ((Button) findViewById(R.id.bHint));
        bHint.setOnClickListener(this);
        bHint.setText(persianNumber.toPersianNumber(getString(R.string.hint) + " (" + no_hint + ")"));
        tvWord = ((TextView) findViewById(R.id.tvWord));

        tvCategory = ((TextView) findViewById(R.id.tvCategory));
        tvScore = ((TextView) findViewById(R.id.tvScore));
        tvTime = ((TextView) findViewById(R.id.tvTime));

        category = prefs.getString("category", categories[0]);

        word = PrepareWord.getWord(getApplicationContext(), category).toUpperCase().trim();

        hiddenWord = PrepareWord.prepare(word);
        tvCategory.setText(Utils.getStringResourceID(getApplicationContext(), category.toLowerCase()));
        tvWord.setText(hiddenWord);
        h = new Handler();
        r = new Runnable() {
            public void run() {
                time++;
                tvTime.setText("" + time);
                h.postDelayed(this, 1000L);
            }
        };
        r.run();
        hint = word;
        prepareHint();

//        initAdMob();
    }

    private void initAdMob() {
        //        mAdView = ((AdView)findViewById(2131165210));
//        paramBundle = new AdRequest.Builder().addTestDevice("041422AA685448ADC53C7E51B28FC5D8").build();
//        mAdView.loadAd(paramBundle);
//        mInterstitialAd = new InterstitialAd(this);
//        mInterstitialAd.setAdUnitId("ca-app-pub-9359804865113945/3779161280");
//        mInterstitialAd.loadAd(new AdRequest.Builder().addTestDevice("041422AA685448ADC53C7E51B28FC5D8").build());
//        mInterstitialAd.setAdListener(new AdListener()
//        {
//            public void onAdClosed()
//            {
//                GameActivity.finishGame(GameActivity.endState);
//            }
//        });
    }


    @Override
    protected void onResume() {
        super.onResume();
        if (share == 2) {
            if (!rate) {
                Toast.makeText(this, "Thank you! You recieved +50 new hints!", Toast.LENGTH_SHORT).show();
                share = 0;
                no_hint += 50;
                editor = getSharedPreferences("StartActivity", 0).edit();
                editor.putInt("no_hint", no_hint);
                editor.putBoolean("rate", true);
                editor.apply();
                bHint.setText(persianNumber.toPersianNumber(getString(R.string.hint) + " (" + no_hint + ")"));
            }
        } else {
            return;
        }
        Toast.makeText(this, "Thank you! You received +10 new hints!", Toast.LENGTH_SHORT).show();
        share = 0;
        no_hint += 15;
        editor = getSharedPreferences("StartActivity", 0).edit();
        editor.putInt("no_hint", no_hint);
        editor.apply();
        bHint.setText(persianNumber.toPersianNumber(getString(R.string.hint) + " (" + no_hint + ")"));
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (share == 1) {
            share = 2;
        }
    }
}