package com.example.sqlite01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    MyDBHelper myHelper;
    EditText edtName, edtNumber, edtNameResult, edtNumberResult;
    Button btnInit, btnInsert, btnSelect;
    SQLiteDatabase sqlDB;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        myHelper = new MyDBHelper(this);    // DB생성
        
        edtName = findViewById(R.id.edtName);
        edtNumber = findViewById(R.id.edtNumber);
        edtNameResult = findViewById(R.id.edtNameResult);
        edtNumberResult = findViewById(R.id.edtNumberResult);
        btnInit = findViewById(R.id.btnInit);
        btnInsert = findViewById(R.id.btnInsert);
        btnSelect = findViewById(R.id.btnSelect);
        
        btnInit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase(); // 쓰기모드로 DB open
                myHelper.onUpgrade(sqlDB, 1, 2);
                sqlDB.close();
            }
        });
        
        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("insert into groupTBL values('"+edtName.getText().toString()+"', "+edtNumber.getText().toString()+")");
                sqlDB.close();
                edtName.setText("");
                edtNumber.setText("");
                Toast.makeText(MainActivity.this, "입력 완료", Toast.LENGTH_SHORT).show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("select * from groupTBL", null);

                String strNames = "그룹 이름" + "\r\n" + "__________" + "\r\n";
                String strNumbers = "인원" + "\r\n" + "__________" + "\r\n";

                while(cursor.moveToNext())
                {
                    // listview로 만드려면 이 값들을 arraylist에 넣어...
                    strNames += cursor.getString(0) + "\r\n";
                    strNumbers += cursor.getString(1) + "\r\n";
                }
                edtNameResult.setText(strNames);
                edtNumberResult.setText(strNumbers);
                cursor.close();
                sqlDB.close();
            }
        });
        
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
