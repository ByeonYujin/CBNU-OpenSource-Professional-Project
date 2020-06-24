package com.cookandroid.catchnoteproject.db.service;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.cookandroid.catchnoteproject.db.helper.NoteBookDBHelper;
import com.cookandroid.catchnoteproject.db.model.NoteBook;
import com.cookandroid.catchnoteproject.db.model.NoteBookPurpose;

import java.util.ArrayList;
import java.util.List;

public class NoteBookService {



    private SQLiteDatabase sqLiteDatabase;

    private NoteBookDBHelper noteBookDBHelper;


    //private final String selectQuery = "SELECT * FROM notebooklist";

    public NoteBookService(Context context){
        noteBookDBHelper = new NoteBookDBHelper(context);
        sqLiteDatabase = noteBookDBHelper.getReadableDatabase();
    }

    public List<NoteBook> getNoteBookList(String mf, String pur, String size){


        List<NoteBook> list = new ArrayList<>();
        String selectQuery = "SELECT * FROM notebooklist WHERE manufacturer = '" + mf + "'";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, null);
        while (cursor.moveToNext()){
            NoteBook noteBook = new NoteBook();
            noteBook.setName(cursor.getString(0));
            noteBook.setPrice(cursor.getString(2));
            noteBook.setManufacture(cursor.getString(3));
            noteBook.setPurpose(cursor.getString(4));
            noteBook.setSize(cursor.getInt(5));
            noteBook.setSpec(cursor.getString(6));

            list.add(noteBook);
        }
        return list;
    }

    public List getSubList(String mf, String pur, String size, int col){
        List sublist = new ArrayList<>();

        Log.d("mf",mf);
        Log.d("pur",pur);
        Log.d("size",size);

        NoteBookPurpose purpose = NoteBookPurpose.getNoteBookPurposeByName(pur);
        if(purpose == null){
            purpose = NoteBookPurpose.DESIGN;
        }

        Integer sizeNumber = getIntegerFromStringSize(size);
        Integer sizeNumberMinusONe = sizeNumber - 1;

        String selectQuery = "SELECT * FROM notebooklist WHERE manufacturer = ? AND purpose = ? AND size > ? AND size <= ? ";
        Cursor cursor = sqLiteDatabase.rawQuery(selectQuery, new String[] {mf, purpose.getCode(), String.valueOf(sizeNumberMinusONe), String.valueOf(sizeNumber)});
        while(cursor.moveToNext()){
            sublist.add(cursor.getString(col));
        }

        return sublist;
    }

    private Integer getIntegerFromStringSize(String size){
        String replacedSize = size.replaceAll("\\D+","");
        try {
            return Integer.valueOf(replacedSize);
        }catch (NumberFormatException e){
            return 13;
        }
    }
}
