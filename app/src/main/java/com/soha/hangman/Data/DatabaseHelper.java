package com.soha.hangman.Data;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.soha.hangman.BuildConfig;
import com.soha.hangman.Models.Word;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;


/**
 * Created by S.Farrokhi on 7/16/2018.
 */

public class DatabaseHelper extends SQLiteOpenHelper {


    public static final String TAG = DatabaseHelper.class.getSimpleName();
    public static final String DATABASE_NAME ="hangman.db";
    private static final int DATABASE_VERSION = 1;
    private Context mContext;
    @SuppressLint("SdCardPath")
    private static String DB_PATH;

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        mContext = context;

            DB_PATH = context.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;

        Log.v(TAG, "is database empty? " + isDatabaseEmpty());
        if(isDatabaseEmpty())
            createDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//
    }
    //Create a empty database on the system
    private void createDatabase()
    {

        if(isDatabaseEmpty()){
            this.getReadableDatabase(); // create database in data/data/application_id/databases
            copyDatabase();
        }

    }
    private boolean isDatabaseEmpty(){
        File databaseFile = new File(DB_PATH);
        return !databaseFile.exists();
    }



    private void copyDatabase(){
        try{
            InputStream input = mContext.getAssets()
                    .open(DATABASE_NAME); //Open your local mDatabase as the input stream
            OutputStream output = new FileOutputStream(
                    DB_PATH); //Open the empty mDatabase as the output stream

            // transfer byte to inputfile to outputfile
            byte[] buffer = new byte[1024];
            int length;
            while((length = input.read(buffer)) > 0){
                output.write(buffer, 0, length);
            }

            //Close the streams
            output.flush();
            output.close();
            input.close();
        } catch(IOException e){
            // TODO: add firebase
            Log.e(TAG, "exception in copy database" , e);
        }
    }

    public static ArrayList<Word> getAll(Context context , String tableName)
    {
        ArrayList<Word> words = new ArrayList<>();
        DatabaseHelper mDbHelper = new DatabaseHelper(context);
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String query = "SELECT * FROM " + tableName;
        Cursor cursor = db.rawQuery(query , null );
        Log.i(TAG, "getAll: table name " + tableName + " count " + cursor.getCount());
        cursor.moveToFirst();
        while (!cursor.isAfterLast())
        {
            Word word = new Word();

            word.id = cursor.getInt(cursor.getColumnIndex(DataContract.DataEntry._ID));
            word.word = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_WORD));
            word.meaning = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_MEANING));
            word.image = cursor.getString(cursor.getColumnIndex(DataContract.DataEntry.COLUMN_IMAGE));

            words.add(word);
            cursor.moveToNext();
        }
        cursor.close();
        return words;
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < newVersion){
            copyDatabase();
        }
    }
}
