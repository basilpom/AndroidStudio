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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    MyDBHelper myHelper;
    EditText edtName, edtNumber;
    Button btnInit, btnInsert, btnSelect, btnUpdate, btnDelete;
    SQLiteDatabase sqlDB;
    ArrayList<String> list = new ArrayList<String>();
    ArrayAdapter<String> adapter;
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        myHelper = new MyDBHelper(this);    // DB생성

        edtName = findViewById(R.id.edtName);
        edtNumber = findViewById(R.id.edtNumber);
        btnInit = findViewById(R.id.btnInit);
        btnInsert = findViewById(R.id.btnInsert);
        btnSelect = findViewById(R.id.btnSelect);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);
        listView = findViewById(R.id.listView);

        adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, list);
        listView.setAdapter(adapter);

        // 클릭하면 상세페이지로 이동
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, list.get(position), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("gName", list.get(position));
                startActivity(intent);
            }
        });
        // 길게 클릭하면 수정/삭제 할 수 있도록
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                edtName.setText(list.get(position));
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("select gNumber from groupTBL where gName='"+list.get(position)+"'", null);
                cursor.moveToFirst();
                String gNumber = cursor.getString(0);
                edtNumber.setText(gNumber);
                cursor.close();
                sqlDB.close();
                return false;
            }
        });

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
                btnSelect.performClick();
                Toast.makeText(MainActivity.this, "입력 완료", Toast.LENGTH_SHORT).show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getReadableDatabase();
                Cursor cursor;
                cursor = sqlDB.rawQuery("select * from groupTBL", null);
                // arraylist 초기화
                list.clear();
                while(cursor.moveToNext())
                {
                    list.add(cursor.getString(0));  // group name
                }
                adapter.notifyDataSetChanged(); // 새로고침
                cursor.close();
                sqlDB.close();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("update groupTBL set gNumber='"+edtNumber.getText()+"' where gName='"+edtName.getText()+"'");
                sqlDB.close();
                btnSelect.performClick();
                Toast.makeText(MainActivity.this, "수정완료", Toast.LENGTH_SHORT).show();
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sqlDB = myHelper.getWritableDatabase();
                sqlDB.execSQL("delete from groupTBL where gName='"+edtName.getText()+"'");
                sqlDB.close();
                btnSelect.performClick();
                Toast.makeText(MainActivity.this, "삭제완료", Toast.LENGTH_SHORT).show();
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
