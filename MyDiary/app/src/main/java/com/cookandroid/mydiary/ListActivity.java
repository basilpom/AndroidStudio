package com.cookandroid.mydiary;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class ListActivity extends AppCompatActivity {
    ListView diaryListView;
    ArrayList<String> diaryDate;
    ListViewAdapter adapter;
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;
//    Button btnWrite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        this.setTitle("모아보기");

        diaryListView = findViewById(R.id.diaryListView);
//        btnWrite = findViewById(R.id.btnWrite);

        diaryDate = new ArrayList<String>();
        adapter = new ListViewAdapter();
        diaryListView.setAdapter(adapter);

        // DB에서 일기 목록 불러오기
        myHelper = new MyDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        final Cursor cursor;
        cursor = sqlDB.rawQuery("select * from diaryTBL order by date desc", null);
        diaryDate.clear();
        while (cursor.moveToNext())
        {
            Drawable icon = null;
            if(cursor.getString(1).equals("1"))
            {
                icon = ContextCompat.getDrawable(this, R.drawable.excited);
            }
            else if(cursor.getString(1).equals("2"))
            {
                icon = ContextCompat.getDrawable(this, R.drawable.party);
            }
            else if(cursor.getString(1).equals("3"))
            {
                icon = ContextCompat.getDrawable(this, R.drawable.sad);
            }
            else if(cursor.getString(1).equals("4"))
            {
                icon = ContextCompat.getDrawable(this, R.drawable.poo);
            }
            String title = cursor.getString(2);
            String date = cursor.getString(0);
            SimpleDateFormat format = new SimpleDateFormat("yyyy.MM.dd");
            title = subStrTitle(title);
            diaryDate.add(date);
            adapter.addItem(icon, title, date);
        }

        diaryListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Log.d("!!! DIARYLIST !!!", diaryList.get(position));
                Intent intent = new Intent(getApplicationContext(), ViewActivity.class);
                intent.putExtra("date", diaryDate.get(position));
                startActivity(intent);
            }
        });

//        // 일기 쓰기 버튼. 오늘 일기가 있으면 바로 수정페이지로 이동
//        btnWrite.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
    }

    // 제목이 너무 길면 잘라서 리스트에 출력
    // 한글 2바이트 아닌기ㅏ...?왜이랴;;;;
    public String subStrTitle(String title){
        int cutlen = 25;
        if(!title.isEmpty())
        {
            title = title.trim();
            if(title.getBytes().length <= cutlen)
            {
                return title;
            }
            else
            {
                StringBuffer bufferTitle = new StringBuffer(cutlen);
                int nCnt = 0;
                for(char ch : title.toCharArray())
                {
                    nCnt += String.valueOf(ch).getBytes().length;
                    if(nCnt > cutlen) break;
                    bufferTitle.append(ch);
                }
                return bufferTitle.toString()+"...";
            }
        }
        else
        {
            return "";
        }
    }

//    public class MyDBHelper extends SQLiteOpenHelper {
//        // Constructor
//        public MyDBHelper(@Nullable Context context) {
//            // DB 생성
//            super(context, "diaryDB", null, 1);
//        }
//
//        @Override
//        public void onCreate(SQLiteDatabase db) {
//            // Table 생성. Table이 이미 존재하면 생성하지 않음. error도 안 남
//            db.execSQL("create table diaryTBL(date sysdate primary key, emotion int, title char(50), content char(300))");
//        }
//
//        @Override
//        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
//            // Table 초기화
//            db.execSQL("drop table if exists diaryTBL");
//            onCreate(db);
//        }
//    }
}
