package com.cookandroid.mydiary;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

public class WriteActivity extends AppCompatActivity {
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;
    RadioGroup rdoGroupIcon;
    EditText inputTitle, inputContent;
    Button btnSave;
    String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_write);
        this.setTitle("일기 작성");

        myHelper = new MyDBHelper(this);

        rdoGroupIcon = findViewById(R.id.rdoGroupIcon);
        inputTitle = findViewById(R.id.inputTitle);
        inputContent = findViewById(R.id.inputContent);
        btnSave = findViewById(R.id.btnSave);

        Intent dateGet = getIntent();
        date = dateGet.getStringExtra("date");
        Log.d("!!! WRITE DATE !!!", date);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 모든 항목 입력하도록 체크
                int emotion = rdoGroupIcon.getCheckedRadioButtonId();
                String title = inputTitle.getText().toString();
                String content = inputContent.getText().toString();
                if(emotion == -1)
                {
                    Toast.makeText(WriteActivity.this, "기분을 선택해주세요!", Toast.LENGTH_SHORT).show();
                }
                else if(title.equals(""))
                {
                    Toast.makeText(WriteActivity.this, "제목을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
                else if(content.equals(""))
                {
                    Toast.makeText(WriteActivity.this, "내용을 입력해주세요!", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    // re-visit 시 번호 늘어나는 것 보정
                    emotion = (emotion + 3) % 4 + 1;

                    sqlDB = myHelper.getWritableDatabase();
                    // 테스트용. 날짜 수정 필!
                    Log.d("!!! WRITE DATE !!!", date);
                    sqlDB.execSQL("insert into diaryTBL values('"+date+"', "+emotion+", '"+title+"', '"+content+"')");
                    sqlDB.close();
                    // 등록 후 목록으로 이동
                    Intent intent = new Intent(getApplicationContext(), ListActivity.class);
                    startActivity(intent);
                    // 목록에서 뒤로가기 시 다시 쓰기 화면으로 오지 않도록
                    finish();
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder dlg = new AlertDialog.Builder(WriteActivity.this);
        dlg.setTitle("작성 취소");
        dlg.setMessage("작성한 내용이 저장되지 않았습니다!\n정말 뒤로 가시겠습니까?");
        dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                WriteActivity.super.onBackPressed();
            }
        });
        dlg.setNegativeButton("아니오", null);
        dlg.show();
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
//            db.execSQL("create table diaryTBL(date sysdate primary key default(datetime('now','localtime')), emotion int, title char(50), content char(300))");
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
