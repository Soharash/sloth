package com.soharash.hangman;

import android.content.ActivityNotFoundException;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.soharash.hangman.Helper.PersianNumber;


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
    String word;
    PersianNumber persianNumber;
    ImageView statusImage;

    private void nextPuzzle() {
        startActivity(new Intent(getApplicationContext(), GameActivity.class));
        finish();
        overridePendingTransition( R.anim.slide_out_right , R.anim.slide_in_left  );
    }

    public void onBackPressed() {
        AlertDialog.Builder localBuilder = new AlertDialog.Builder(this);
        localBuilder.setMessage("Are you sure you want to exit?").setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.dismiss();
                finish();
                overridePendingTransition(R.anim.tooltip_enter, R.anim.tooltip_exit);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface paramAnonymousDialogInterface, int paramAnonymousInt) {
                paramAnonymousDialogInterface.cancel();
            }
        });
        localBuilder.create().show();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish);
        Intent intent = getIntent();
        try {

            init(intent);

            if (correct) {
                tvStatus.setText(getString(R.string.correct));
                statusImage.setVisibility(View.VISIBLE);
                tvHint.setVisibility(View.VISIBLE);
                if (((no_correct > 5) && (prefs.getBoolean("rate", true))) || (prefs.getInt("rate_delay", -1) == no_correct)) {
                    llRate.setVisibility(View.VISIBLE);
                }
            }
            else
            {
                statusImage.setVisibility(View.INVISIBLE);
                tvStatus.setText(getString(R.string.wrong));
                tvHint.setVisibility(View.GONE);
            }

            tvAnswer.setText(persianNumber.toPersianNumber(getString(R.string.word) + " " + word));
            tvSolved.setText(persianNumber.toPersianNumber(getString(R.string.solved_puzzles) + " " + no_correct));
            tvScore.setText(persianNumber.toPersianNumber(getString(R.string.score)+ " " + score));
            bNextPuzzle.setOnClickListener(new View.OnClickListener() {
                public void onClick(View paramAnonymousView) {
                    nextPuzzle();
                }
            });
//                bFindMore.setOnClickListener(new View.OnClickListener()
//                {
//                    public void onClick(View paramAnonymousView)
//                    {
//                        paramAnonymousView = new Intent(FinishActivity.getApplicationContext(), FindMore.class);
//                        paramAnonymousView.putExtra("word", FinishActivity.word);
//                        FinishActivity.startActivity(paramAnonymousView);
//                        FinishActivity.overridePendingTransition(2130771979, 2130771980);
//                    }
//                });
//                bRate1.setOnClickListener(new View.OnClickListener()
//                {
//                    public void onClick(View paramAnonymousView)
//                    {
//                        if (FinishActivity.rating == 0)
//                        {
//                            FinishActivity.rating = 1;
//                            FinishActivity.tvRate.setText("Would you mind giving us some feedback?");
//                            FinishActivity.bRate1.setText("No, thanks");
//                            FinishActivity.bRate2.setText("Ok, sure");
//                        }
//                        do
//                        {
//                            return;
//                            if (FinishActivity.rating == 1)
//                            {
//                                FinishActivity.llRate.setVisibility(8);
//                                FinishActivity.editor = FinishActivity.getSharedPreferences("Start", 0).edit();
//                                FinishActivity.editor.putInt("rate_delay", FinishActivity.no_correct + 10);
//                                FinishActivity.editor.putBoolean("rate", false);
//                                FinishActivity.editor.apply();
//                                return;
//                            }
//                        } while (FinishActivity.rating != 11);
//                        FinishActivity.llRate.setVisibility(8);
//                        FinishActivity.editor = FinishActivity.getSharedPreferences("Start", 0).edit();
//                        FinishActivity.editor.putInt("rate_delay", FinishActivity.no_correct + 10);
//                        FinishActivity.editor.putBoolean("rate", false);
//                        FinishActivity.editor.apply();
//                    }
//                });
//                bRate2.setOnClickListener(new View.OnClickListener()
//                {
//                    public void onClick(View paramAnonymousView)
//                    {
//                        if (FinishActivity.rating == 0)
//                        {
//                            FinishActivity.rating = 11;
//                            FinishActivity.tvRate.setText("How about a rating on the Play Store, then?");
//                            FinishActivity.bRate1.setText("No, thanks");
//                            FinishActivity.bRate2.setText("Ok, sure");
//                        }
//                        do
//                        {
//                            return;
////                            if (FinishActivity.rating == 11)
////                            {
////                                FinishActivity.editor = FinishActivity.getSharedPreferences("Start", 0).edit();
////                                FinishActivity.editor.putBoolean("rate", false);
////                                FinishActivity.editor.apply();
////                                paramAnonymousView = FinishActivity.getPackageName();
////                                try
////                                {
////                                    FinishActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("market://details?id=" + paramAnonymousView)));
////                                    return;
////                                }
////                                catch (ActivityNotFoundException localActivityNotFoundException)
////                                {
////                                    FinishActivity.startActivity(new Intent("android.intent.action.VIEW", Uri.parse("https://play.google.com/store/apps/details?id=" + paramAnonymousView)));
////                                    return;
////                                }
////                            }
//                        } while (FinishActivity.rating != 1);
//                        FinishActivity.editor = FinishActivity.getSharedPreferences("Start", 0).edit();
//                        FinishActivity.editor.putBoolean("rate", false);
//                        FinishActivity.editor.apply();
////                        paramAnonymousView = new Intent("android.intent.action.SEND");
////                        paramAnonymousView.setType("message/rfc822");
////                        paramAnonymousView.putExtra("android.intent.extra.EMAIL", new String[] { "klikappdev@gmail.com" });
////                        paramAnonymousView.putExtra("android.intent.extra.SUBJECT", "Hangman Free Feedback");
////                        try
////                        {
////                            FinishActivity.startActivity(Intent.createChooser(paramAnonymousView, "Send mail..."));
////                            FinishActivity.llRate.setVisibility(8);
////                            return;
////                        }
////                        catch (ActivityNotFoundException paramAnonymousView)
////                        {
////                            Toast.makeText(FinishActivity.getApplicationContext(), "There are no email clients installed!", 0).show();
////                        }
//                    }
//                });
//                mAdView = ((AdView)findViewById(2131165210));
//                paramBundle = new AdRequest.Builder().addTestDevice("041422AA685448ADC53C7E51B28FC5D8").build();
//                mAdView.loadAd(paramBundle);
            //  return;


        } catch (Exception p) {

        }

    }


    private void init(Intent intent)
    {
        persianNumber = new PersianNumber();
        word = intent.getExtras().getString("word");
        correct = intent.getExtras().getBoolean("correct");
        score = intent.getExtras().getInt("score");
        prefs = getSharedPreferences("StartActivity", 0);
        no_score = prefs.getInt("no_score", 0);
        no_correct = prefs.getInt("no_correct", 0);
        no_played = prefs.getInt("no_played", 0);
        bNextPuzzle = ((Button) findViewById(R.id.bNextPuzzle));
        bFindMore = ((Button) findViewById(R.id.bFindMore));
        llRate = ((LinearLayout) findViewById(R.id.llRate));
        tvStatus = ((TextView) findViewById(R.id.tvStatus));
        tvAnswer = ((TextView) findViewById(R.id.tvAnswer));
        tvSolved = ((TextView) findViewById(R.id.tvSolved));
        tvScore = ((TextView) findViewById(R.id.tvScore));
        tvHint = ((TextView) findViewById(R.id.tvHint));
        statusImage = findViewById(R.id.status_image);
        statusImage.setColorFilter(Color.WHITE , PorterDuff.Mode.SRC_IN);
        tvHint.setText(persianNumber.toPersianNumber(getString(R.string.new_hint)));
    }
}

