package com.soha.hangman.Helper;

import android.content.Context;
import android.content.SharedPreferences;

import com.soha.hangman.StartActivity;

public class PersianNumber {
    String language;
    public PersianNumber(Context context)
    {
        SharedPreferences prefs = context.getSharedPreferences("StartActivity" , Context.MODE_PRIVATE);
        language = prefs.getString("language" , StartActivity.language[1]);
    }
    private static String[] persianNumbers = new String[]{"۰", "۱", "۲", "۳", "۴", "۵", "۶", "۷", "۸", "۹"};

    public  String toPersianNumber(String text) {
        if(language.equals(StartActivity.language[0]))
            return text;
        if (text == null || text.isEmpty())
            return "";
        String out = "";
        int length = text.length();
        for (int i = 0; i < length; i++) {
            char c = text.charAt(i);
            if ('0' <= c && c <= '9') {
                int number = Integer.parseInt(String.valueOf(c));
                out += persianNumbers[number];
            } else if (c == '٫') {
                out += '،';
            } else {
                out += c;
            }

        }
        return out;
    }
}