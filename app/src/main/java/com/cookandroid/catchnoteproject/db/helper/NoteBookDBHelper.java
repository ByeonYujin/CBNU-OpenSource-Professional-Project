package com.cookandroid.catchnoteproject.db.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class NoteBookDBHelper extends SQLiteOpenHelper {

    public NoteBookDBHelper(Context context) {
        super(context, "CATCHNOTE.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
