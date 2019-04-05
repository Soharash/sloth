package com.soha.hangman.Helper;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.view.View;

import java.util.Locale;

public class Utils {

    public static String font1 = "Roboto_Mono/RobotoMono-Medium.ttf";
    public static String font2 = "Amatic/AmaticSC-Bold.ttf";

    public static int getStringResourceID(Context mContext , String param)
    {
        String packageName = mContext.getPackageName();
        try {
            int id = mContext.getResources().getIdentifier(param, "string", packageName);
            return id;
        } catch (Exception e) {
            return mContext.getResources().getIdentifier("dummy_content", "string", packageName);

        }
    }
    public static void forceRtlIfSupported(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ((Activity) context).getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_RTL);
        }
    }

    public static void forceLtrIfSupported(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            ((Activity) context).getWindow().getDecorView().setLayoutDirection(View.LAYOUT_DIRECTION_LTR);
        }
    }
    public static void changeLocale(Context context, String language) {

        Locale locale = new Locale(language);
        Locale.setDefault(locale);
        Configuration config = new Configuration();
        config.locale = locale;
        context.getResources().updateConfiguration(config,
                context.getResources().getDisplayMetrics());
    }
}
