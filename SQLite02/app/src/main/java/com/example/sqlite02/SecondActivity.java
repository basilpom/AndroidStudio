package com.example.sqlite02;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.util.ArrayList;

public class SecondActivity extends AppCompatActivity {
    MyDBHelper myHelper;
    TextView txtGName, txtGNumber;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        myHelper = new MyDBHelper(this);

        txtGName = findViewById(R.id.txtGName);
        txtGNumber = findViewById(R.id.txtGNumber);

        Intent intent = getIntent();
        String gName = intent.getStringExtra("gName");
        Log.d("!!!!!! gNAME : ",gName);
        txtGName.setText(gName);

        // gName으로 검색
        sqlDB = myHelper.getReadableDatabase();
        Cursor cursor;
        cursor = sqlDB.rawQuery("select gNumber from groupTBL where gName='"+gName+"'", null);
        cursor.moveToFirst();
        String gNumber = cursor.getString(0);
        txtGNumber.setText(gNumber);
        cursor.close();
        sqlDB.close();
    }

    public class MyDBHelper extends SQLiteOpenHelper {
        // Constructor
        public MyDBHelper(@Nullable Context context) {
            // DB 생성
            super(context, "groupDB", null, 1);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            // Table 생성. Table이 이미 존재하면 생성하지 않음. error도 안 남
            db.execSQL("create table groupTBL(gName char(20) primary key, gNumber integer)");
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            // Table 초기화
            db.execSQL("drop table if exists groupTBL");
            onCreate(db);
        }
    }
}
