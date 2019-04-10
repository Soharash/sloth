package com.sohara.hangman.Data;

import android.provider.BaseColumns;

/**
 * Created by S.Farrokhi on 11/13/2018.
 */

public final class DataContract {
    public static String[] tableNames = {"Animals",  "Countries","Food", "Sport", "Kitchen", "Jobs" , "Fruit" , "Colors" , "Body" , "Flowers"};
    private DataContract()
    {}
    public static final class DataEntry implements BaseColumns
    {

        public final static String _ID = "ID";
        public final static String COLUMN_WORD = "word";
        public final static String COLUMN_MEANING ="meaning";
        public final static String COLUMN_IMAGE = "image";

    }
    public static final class TableEntry implements BaseColumns
    {
        public final static String COLUMN_NAME = "name";
        public final static String COLUMN_SEQ ="seq";

    }
}