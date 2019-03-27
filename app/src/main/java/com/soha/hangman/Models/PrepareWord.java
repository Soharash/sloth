package com.soha.hangman.Models;

import android.content.Context;
import android.content.SharedPreferences;

import com.soha.hangman.Data.DatabaseHelper;
import com.soha.hangman.Models.Word;
import com.soha.hangman.StartActivity;

import java.util.ArrayList;
import java.util.Random;

import static android.content.Context.MODE_PRIVATE;

public class PrepareWord {
    private static Context context;

    public static String getWord(Context mContext, String tableName) {
        context = mContext;
        String temp = tableName.toLowerCase().replace(" ", "_");
        SharedPreferences localSharedPreferences = context.getSharedPreferences("PrepareWord", 0);
        SharedPreferences.Editor localEditor = context.getSharedPreferences("PrepareWord", 0).edit();


        int i = localSharedPreferences.getInt(temp, 0);
        // String[] array = readText(paramString).split(";");
        ArrayList<Word> words = DatabaseHelper.getAll(context, tableName);
        if (words.size() > 0) {
            if (i < words.size() - 1) {
                i += 1;
                localEditor.putInt(temp, i);
                localEditor.apply();
                return getWordByLanguage(words.get(i));
            }
            localEditor.putInt(temp, 0);
            localEditor.apply();
            return getWordByLanguage(words.get(0));
        } else
            return null;
    }

    public static String getWordByLanguage(Word word)
    {
        SharedPreferences prefs = context.getSharedPreferences("StartActivity" , MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        String language = prefs.getString("language", StartActivity.language[1]);
        editor.putString("random_language" , language).apply();
        if(language.equals(StartActivity.language[0]))
            return word.word;
        else if(language.equals(StartActivity.language[1]))
            return word.meaning;
        else
        {
            Random random = new Random();
            int i = random.nextInt(2);
            if(i == 1) {
                editor.putString("random_language" , StartActivity.language[0]).apply();
                return word.word;
            }
            else {
                editor.putString("random_language" , StartActivity.language[1]).apply();
                return word.meaning;
            }
        }
    }

    public static String prepare(String theWord) {
        StringBuilder stringBuilder = new StringBuilder();

        for(char ch: theWord.toCharArray() )
        {
            if(ch == ' ')
                stringBuilder.append(ch);
            else
                stringBuilder.append("_");
        }
        return stringBuilder.toString();


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
