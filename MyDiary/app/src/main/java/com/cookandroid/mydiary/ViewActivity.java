package com.cookandroid.mydiary;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class ViewActivity extends AppCompatActivity {
    RadioGroup viewRdoGroupIcon;
    EditText viewTitle, viewContent;
    Button btnUpdate, btnDelete;
    int emotion;
    String date, title, content;
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view);
        this.setTitle("일기 보기");

        viewRdoGroupIcon = findViewById(R.id.viewRdoGroupIcon);
        viewTitle = findViewById(R.id.viewTitle);
        viewContent = findViewById(R.id.viewContent);
        btnUpdate = findViewById(R.id.btnUpdate);
        btnDelete = findViewById(R.id.btnDelete);

        Intent intent = getIntent();
        date = intent.getStringExtra("date");
        Log.d("!!! VIEW DATE !!!", date);

        myHelper = new MyDBHelper(this);
        sqlDB = myHelper.getReadableDatabase();
        final Cursor cursor;
        cursor = sqlDB.rawQuery("select * from diaryTBL where date='"+date+"'", null);
        // 커서 처음으로 이동
        cursor.moveToFirst();
        // SQL문 실행 결과 불러오기
        emotion = cursor.getInt(1);
        title = cursor.getString(2);
        content = cursor.getString(3);

        // 불러온 값 view 에 반영
        ((RadioButton)viewRdoGroupIcon.getChildAt(emotion-1)).setChecked(true);
        viewTitle.setText(title);
        viewContent.setText(content);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentUp = new Intent(getApplicationContext(), UpdateActivity.class);
                intentUp.putExtra("date", date);
                intentUp.putExtra("emotion", emotion);
                intentUp.putExtra("title", title);
                intentUp.putExtra("content", content);
                startActivity(intentUp);
                Log.d("!!!! VIEW EMOTION !!!!", Integer.toString(emotion));
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(ViewActivity.this);
                dlg.setTitle("일기 삭제");
                dlg.setMessage("정말 삭제하시겠습니까?");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 삭제 처리 후 목록으로 이동
                        Log.d("!!!!!!! DELETE !!!!!", date);
                        sqlDB = myHelper.getWritableDatabase();
                        sqlDB.execSQL("delete from diaryTBL where date='"+date+"'");
                        Toast.makeText(ViewActivity.this, "삭제되었습니다", Toast.LENGTH_SHORT).show();
                        Intent goList = new Intent(getApplicationContext(), ListActivity.class);
                        startActivity(goList);
                        finish();
                    }
                });
                dlg.setNegativeButton("아니오", null);
                dlg.show();
            }
        });
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
