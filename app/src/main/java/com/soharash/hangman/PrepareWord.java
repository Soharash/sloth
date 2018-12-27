package com.soharash.hangman;

import android.content.Context;
import android.content.SharedPreferences;

import com.soharash.hangman.Data.DatabaseHelper;

import java.io.InputStreamReader;
import java.util.ArrayList;

public class PrepareWord
{
    private static Context context;

    public static Word getWord(Context mContext, String tableName)
    {
        context = mContext;
        String temp = tableName.toLowerCase().replace(" ", "_");
        SharedPreferences localSharedPreferences = context.getSharedPreferences("PrepareWord", 0);
        SharedPreferences.Editor localEditor = context.getSharedPreferences("PrepareWord", 0).edit();
        int i = localSharedPreferences.getInt(temp, 0);
       // String[] array = readText(paramString).split(";");
        ArrayList<Word> words = DatabaseHelper.getAll(context , tableName);
        if(words.size() > 0) {
            if (i < words.size() - 1) {
                i += 1;
                localEditor.putInt(temp, i);
                localEditor.apply();
                return words.get(i);
            }
            localEditor.putInt(temp, 0);
            localEditor.apply();
            return words.get(0);
        }
        else
            return null;
    }

    public static String prepare(String paramString)
    {
        SharedPreferences localSharedPreferences = context.getSharedPreferences("PrepareWord", 0);
        SharedPreferences.Editor localEditor = context.getSharedPreferences("PrepareWord", 0).edit();
        String language = localSharedPreferences.getString("language" , "en");
        return paramString.toUpperCase().replace("Q", "_").replace("W", "_").replace("E", "_").replace("R", "_").replace("T", "_").replace("Y", "_").replace("U", "_").replace("I", "_").replace("O", "_").replace("P", "_").replace("A", "_").replace("S", "_").replace("D", "_").replace("F", "_").replace("G", "_").replace("H", "_").replace("J", "_").replace("K", "_").replace("L", "_").replace("Z", "_").replace("X", "_").replace("C", "_").replace("V", "_").replace("B", "_").replace("N", "_").replace("M", "_");
    }

//    public static String readText(String paramString)
//    {
//        Object localObject2 = "";
//        Object localObject1 = localObject2;
//        try
//        {
//            InputStreamReader localInputStreamReader = new InputStreamReader(context.openFileInput(paramString.toLowerCase().replace(" ", "_") + ".txt"));
//            localObject1 = localObject2;
//            localObject2 = new char[' '];
//            String str;
//            for (paramString = "";; paramString = paramString + str)
//            {
//                localObject1 = paramString;
//                int i = localInputStreamReader.read((char[])localObject2);
//                if (i <= 0) {
//                    break;
//                }
//                localObject1 = paramString;
//                str = String.copyValueOf((char[])localObject2, 0, i);
//                localObject1 = paramString;
//            }
//            localObject1 = paramString;
//            localInputStreamReader.close();
//            return paramString;
//        }
//        catch (Exception e)
//        {
//            e.printStackTrace();
//        }
//        return (String)localObject1;
//    }
}
