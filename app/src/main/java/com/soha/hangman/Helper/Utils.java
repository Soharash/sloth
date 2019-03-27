package com.soha.hangman.Helper;

import android.content.Context;

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
}
