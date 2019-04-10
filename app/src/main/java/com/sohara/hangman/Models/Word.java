package com.sohara.hangman.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Word implements Parcelable {


    public int id;
    public String word;
    public String meaning;
    public String image;

    protected Word(Parcel in) {
        id = in.readInt();
        word = in.readString();
        meaning = in.readString();
        image = in.readString();
    }
    public Word()
    {}

    public static final Creator<Word> CREATOR = new Creator<Word>() {
        @Override
        public Word createFromParcel(Parcel in) {
            return new Word(in);
        }

        @Override
        public Word[] newArray(int size) {
            return new Word[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeString(word);
        dest.writeString(meaning);
        dest.writeString(image);
    }
}
