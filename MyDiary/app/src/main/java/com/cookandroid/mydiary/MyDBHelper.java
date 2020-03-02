package com.cookandroid.mydiary;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class MyDBHelper extends SQLiteOpenHelper {
    // Constructor
    public MyDBHelper(@Nullable Context context) {
        // DB 생성
        super(context, "diaryDB", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Table 생성. Table이 이미 존재하면 생성하지 않음. error도 안 남
        db.execSQL("create table diaryTBL(date sysdate primary key, emotion int, title char(50), content char(300))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Table 초기화
        db.execSQL("drop table if exists diaryTBL");
        onCreate(db);
    }
}