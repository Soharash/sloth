package com.soharash.hangman;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewStub;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.soharash.hangman.Data.DataContract;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {
    Button bA;
    Button bB;
    Button bC;
    Button bD;
    Button bE;
    Button bF;
    Button bG;
    Button bH;
    Button bHint;
    Button bI;
    Button bJ;
    Button bK;
    Button bL;
    Button bM;
    Button bN;
    Button bO;
    Button bP;
    Button bQ;
    Button bR;
    Button bS;
    Button bT;
    Button bU;
    Button bV;
    Button bW;
    Button bX;
    Button bY;
    Button bZ;
    String[] categories = DataContract.categories;
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

    private void checkLetter(String letter)
    {
        int i = -1;
        for (;;)
        {
            i = word.indexOf(letter, i + 1);
            if (i == -1) {
                break;
            }
            hiddenWord = (hiddenWord.substring(0, i) + letter + hiddenWord.substring(i + 1));
        }
        if (word.contains(letter))
        {
            score += 10;
            hint = hint.replace(letter, "");
            prepareHint();
            if (sound) {
                key.start();
            }
            if (word.equals(hiddenWord))
            {
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
            tvScore.setText("" + score);
            tvWord.setText(hiddenWord);
            return;
        }
//    for (;;)
        //   {

        counter += 1;
        displayHangman(counter);
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
                return;
            case 1:
                ivHangman.setImageResource(R.drawable.h02_2);
                return;
            case 2:
                ivHangman.setImageResource(R.drawable.h02_3);
                return;
            case 3:
                ivHangman.setImageResource(R.drawable.h02_4);
                return;
            case 4:
                ivHangman.setImageResource(R.drawable.h02_5);
                return;
            case 5:
                ivHangman.setImageResource(R.drawable.h02_6);
                return;
            case 6:
                ivHangman.setImageResource(R.drawable.h02_7);
                return;
            case 7:
                ivHangman.setImageResource(R.drawable.h02_8);
                return;
        }
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
            startActivity(localIntent);
            finish();
            overridePendingTransition( R.anim.slide_in_left , R.anim.slide_out_right );
            return;
        }
        else {
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
            startActivity(localIntent);
            finish();
            overridePendingTransition(  R.anim.slide_in_left,  R.anim.slide_out_right  );
        }
    }

    private void hideLetter(String paramString) {
        switch (paramString) {
            case "A":
                bA.setEnabled(false);
                bA.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "B":
                bB.setEnabled(false);
                bB.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "C":
                bC.setEnabled(false);
                bC.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "D":
                bD.setEnabled(false);
                bD.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "E":
                bE.setEnabled(false);
                bE.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "F":
                bF.setEnabled(false);
                bF.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "G":
                bG.setEnabled(false);
                bG.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "H":
                bH.setEnabled(false);
                bH.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "I":
                bI.setEnabled(false);
                bI.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "J":
                bJ.setEnabled(false);
                bJ.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "K":
                bK.setEnabled(false);
                bK.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "L":
                bL.setEnabled(false);
                bL.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "M":
                bM.setEnabled(false);
                bM.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "N":
                bN.setEnabled(false);
                bN.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "O":
                bO.setEnabled(false);
                bO.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "P":
                bP.setEnabled(false);
                bP.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "Q":
                bQ.setEnabled(false);
                bQ.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "R":
                bR.setEnabled(false);
                bR.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "S":
                bS.setEnabled(false);
                bS.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "T":
                bT.setEnabled(false);
                bT.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "U":
                bU.setEnabled(false);
                bU.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "V":
                bV.setEnabled(false);
                bV.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "W":
                bW.setEnabled(false);
                bW.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "X":
                bX.setEnabled(false);
                bX.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "Y":
                bY.setEnabled(false);
                bY.setBackgroundResource(R.drawable.keyboard_off);
                break;
            case "Z":
                bZ.setEnabled(false);
                bZ.setBackgroundResource(R.drawable.keyboard_off);
                break;

        }
    }

    private void hintLogic() {
        try {
            if (no_hint > 0) {
                no_hint -= 1;
                int i = hintList.size();
                i = new Random().nextInt(i - 1 - 1 + 1) + 1;
                hideLetter((String) hintList.get(i));
                checkLetter((String) hintList.get(i));
                bHint.setText("Hint (" + no_hint + ")");
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

    private void initKeyboard() {
        String random = prefs.getString("random_language" , StartActivity.language[0]);
        ViewStub stub = (ViewStub) findViewById(R.id.include);
        if(random.equals(StartActivity.language[0]))
        stub.setLayoutResource(R.layout.keyboard);
        else
            stub.setLayoutResource(R.layout.keyboard_fa);
       stub.inflate();

        bQ = ((Button) findViewById(R.id.bQ));
        bW = ((Button) findViewById(R.id.bW));
        bE = ((Button) findViewById(R.id.bE));
        bR = ((Button) findViewById(R.id.bR));
        bT = ((Button) findViewById(R.id.bT));
        bY = ((Button) findViewById(R.id.bY));
        bU = ((Button) findViewById(R.id.bU));
        bI = ((Button) findViewById(R.id.bI));
        bO = ((Button) findViewById(R.id.bO));
        bP = ((Button) findViewById(R.id.bP));
        bA = ((Button) findViewById(R.id.bA));
        bS = ((Button) findViewById(R.id.bS));
        bD = ((Button) findViewById(R.id.bD));
        bF = ((Button) findViewById(R.id.bF));
        bG = ((Button) findViewById(R.id.bG));
        bH = ((Button) findViewById(R.id.bH));
        bJ = ((Button) findViewById(R.id.bJ));
        bK = ((Button) findViewById(R.id.bK));
        bL = ((Button) findViewById(R.id.bL));
        bZ = ((Button) findViewById(R.id.bZ));
        bX = ((Button) findViewById(R.id.bX));
        bC = ((Button) findViewById(R.id.bC));
        bV = ((Button) findViewById(R.id.bV));
        bB = ((Button) findViewById(R.id.bB));
        bN = ((Button) findViewById(R.id.bN));
        bM = ((Button) findViewById(R.id.bM));
        bQ.setOnClickListener(this);
        bW.setOnClickListener(this);
        bE.setOnClickListener(this);
        bR.setOnClickListener(this);
        bT.setOnClickListener(this);
        bY.setOnClickListener(this);
        bU.setOnClickListener(this);
        bI.setOnClickListener(this);
        bO.setOnClickListener(this);
        bP.setOnClickListener(this);
        bA.setOnClickListener(this);
        bS.setOnClickListener(this);
        bD.setOnClickListener(this);
        bF.setOnClickListener(this);
        bG.setOnClickListener(this);
        bH.setOnClickListener(this);
        bJ.setOnClickListener(this);
        bK.setOnClickListener(this);
        bL.setOnClickListener(this);
        bZ.setOnClickListener(this);
        bX.setOnClickListener(this);
        bC.setOnClickListener(this);
        bV.setOnClickListener(this);
        bB.setOnClickListener(this);
        bN.setOnClickListener(this);
        bM.setOnClickListener(this);
    }

    private void prepareHint() {
        hint = hint.replace(" ", "");
        hintList = Arrays.asList(hint.split(""));
    }

    private void pushInfo(String paramString) {
    }

    public void onBackPressed() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                editor = getSharedPreferences("StartActivity", 0).edit();
                editor.putInt("no_hint", no_hint);
                editor.apply();
                paramAnonymousDialogInterface.dismiss();
                finish();
                overridePendingTransition(   R.anim.slide_in_left , R.anim.slide_out_right );
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.cancel();
            }
        });
        localBuilder.create().show();
    }

    public void onClick(View paramView) {
        switch (paramView.getId()) {
            default:
                return;
            case R.id.bQ:
                checkLetter("Q");
                bQ.setEnabled(false);
                bQ.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bW:
                checkLetter("W");
                bW.setEnabled(false);
                bW.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bE:
                checkLetter("E");
                bE.setEnabled(false);
                bE.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bR:
                checkLetter("R");
                bR.setEnabled(false);
                bR.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bT:
                checkLetter("T");
                bT.setEnabled(false);
                bT.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bY:
                checkLetter("Y");
                bY.setEnabled(false);
                bY.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bU:
                checkLetter("U");
                bU.setEnabled(false);
                bU.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bI:
                checkLetter("I");
                bI.setEnabled(false);
                bI.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bO:
                checkLetter("O");
                bO.setEnabled(false);
                bO.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bP:
                checkLetter("P");
                bP.setEnabled(false);
                bP.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bA:
                checkLetter("A");
                bA.setEnabled(false);
                bA.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bS:
                checkLetter("S");
                bS.setEnabled(false);
                bS.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bD:
                checkLetter("D");
                bD.setEnabled(false);
                bD.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bF:
                checkLetter("F");
                bF.setEnabled(false);
                bF.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bG:
                checkLetter("G");
                bG.setEnabled(false);
                bG.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bH:
                checkLetter("H");
                bH.setEnabled(false);
                bH.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bJ:
                checkLetter("J");
                bJ.setEnabled(false);
                bJ.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bK:
                checkLetter("K");
                bK.setEnabled(false);
                bK.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bL:
                checkLetter("L");
                bL.setEnabled(false);
                bL.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bZ:
                checkLetter("Z");
                bZ.setEnabled(false);
                bZ.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bX:
                checkLetter("X");
                bX.setEnabled(false);
                bX.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bC:
                checkLetter("C");
                bC.setEnabled(false);
                bC.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bV:
                checkLetter("V");
                bV.setEnabled(false);
                bV.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bB:
                checkLetter("B");
                bB.setEnabled(false);
                bB.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bN:
                checkLetter("N");
                bN.setEnabled(false);
                bN.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bM:
                checkLetter("M");
                bM.setEnabled(false);
                bM.setBackgroundResource(R.drawable.keyboard_off);
                return;
            case R.id.bHint:
                hintLogic();
                return;
        }
//        if (secret == 10) {
//            Toast.makeText(this, "" + word, 0).show();
//            return;
//        }
//        secret += 1;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        init();
        tvCategory.setOnClickListener(this);
    }

    private void init()
    {
        win = MediaPlayer.create(this, R.raw.sound_win);
        lose = MediaPlayer.create(this, R.raw.sound_lose);
        key = MediaPlayer.create(this, R.raw.sound_correct);

        prefs = getSharedPreferences("StartActivity", 0);
        selectedLanguage = prefs.getString("language" , StartActivity.language[0]);
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
        bHint.setText("Hint (" + no_hint + ")");
        tvWord = ((TextView) findViewById(R.id.tvWord));

        tvCategory = ((TextView) findViewById(R.id.tvCategory));
        tvScore = ((TextView) findViewById(R.id.tvScore));
        tvTime = ((TextView) findViewById(R.id.tvTime));

        category = prefs.getString("category", categories[0]);

        word = PrepareWord.getWord(getApplicationContext(), category).toUpperCase().trim();
        initKeyboard();
        hiddenWord = PrepareWord.prepare(word);
        tvCategory.setText(category.toUpperCase());
        tvWord.setText(hiddenWord);
        h = new Handler();
        r = new Runnable() {
            public void run() {
                tvTime.setText("" + time);
                h.postDelayed(this, 1000L);
            }
        };
        r.run();
        hint = word;
        prepareHint();

//        initAdMob();
    }

    private void initAdMob()
    {
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
                bHint.setText("Hint (" + no_hint + ")");
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
        bHint.setText("Hint (" + no_hint + ")");
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (share == 1) {
            share = 2;
        }
    }
}