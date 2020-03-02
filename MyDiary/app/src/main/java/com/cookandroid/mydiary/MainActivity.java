package com.cookandroid.mydiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;



public class MainActivity extends AppCompatActivity {
    DatePicker datePicker;
    Button btnWrite, btnGoList;
//    String year, month, day;
    int iYear, iMonth, iDay; // for initialize
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        this.setTitle("날짜 선택");

        Intent splash = new Intent(this, LoadingActivity.class);
        startActivity(splash);

        datePicker = findViewById(R.id.datePicker);
        btnWrite = findViewById(R.id.btnWrite);
        btnGoList = findViewById(R.id.btnGoList);

        // DatePicker 클릭하지 않고 선택된 현재 날짜 그대로 일기쓰기 버튼 누를 경우를 위해 initailize
        iYear = datePicker.getYear();
        iMonth = datePicker.getMonth();
        iDay = datePicker.getDayOfMonth();
        date = dateFormat(iYear, iMonth+1, iDay);

        System.out.println("!!!! MONTH !!!!"+iMonth);
        datePicker.init(iYear, iMonth, iDay, new DatePicker.OnDateChangedListener() {
            @Override
            public void onDateChanged(DatePicker view, int y, int monthOfYear, int dayOfMonth) {
                date = dateFormat(y, monthOfYear, dayOfMonth);
            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 선택한 날짜에 작성한 일기가 있으면 수정페이지로 가도록.
                MyDBHelper myHelper = new MyDBHelper(MainActivity.super.getApplicationContext());
                SQLiteDatabase sqlDB = myHelper.getReadableDatabase();
                Cursor cursor = sqlDB.rawQuery("select * from diaryTBL where date='"+date+"'", null);
//              // 있으면 dialog 에서 물어보기
                Log.d("!!!! DATE !!!!", date);
                //Log.d("!!!! CURSOR !!!", Boolean.toString(cursor.moveToNext()));
                if(cursor.moveToNext())
                {
                    AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                    dlg.setTitle(date+" 일기 작성");
                    dlg.setMessage("작성한 일기가 존재합니다.");
                    dlg.setPositiveButton("보러 가기", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent goUpdate = new Intent(getApplicationContext(), ViewActivity.class);
                            goUpdate.putExtra("date", date);
                            startActivity(goUpdate);
                        }
                    });
                    dlg.setNegativeButton("다른 날 선택", null);
                    dlg.show();
                }
                // 없으면 go to write page
                else
                {
                    Intent intent = new Intent(getApplicationContext(), WriteActivity.class);
                    intent.putExtra("date", date);
                    Log.d("!!! MAIN DATE !!!", date);
                    startActivity(intent);
                }

            }
        });

        btnGoList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(intent);
            }
        });
    }

    // date format : yyyy.MM.dd
    public String dateFormat(int y, int m, int d){
        String dateForm = "";
        String year = Integer.toString(y);
        String month = Integer.toString(m);
        String day = Integer.toString(d);

        if(month.length() < 2)
        {
            month = "0"+month;
        }
        if(day.length() < 2)
        {
            day = "0"+day;
        }
        dateForm = year+"."+month+"."+day;

        return dateForm;
    }
}
