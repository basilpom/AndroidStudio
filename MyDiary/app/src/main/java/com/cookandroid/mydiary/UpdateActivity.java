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
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class UpdateActivity extends AppCompatActivity {
    RadioGroup updateRdoGroupIcon;
    EditText updateTitle, updateContent;
    Button btnUpdateSave, btnUpdateCancel;
    MyDBHelper myHelper;
    SQLiteDatabase sqlDB;
    int emotion;
    String date, title, content;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        this.setTitle("일기 수정");

        myHelper = new MyDBHelper(this);

        updateRdoGroupIcon = findViewById(R.id.updateRdoGroupIcon);
        updateTitle = findViewById(R.id.updateTitle);
        updateContent = findViewById(R.id.updateContent);
        btnUpdateSave = findViewById(R.id.btnUpdateSave);
        btnUpdateCancel = findViewById(R.id.btnUpdateSave);

        final Intent intent = getIntent();
        emotion = intent.getIntExtra("emotion", 0);
        date = intent.getStringExtra("date");
        title = intent.getStringExtra("title");
        content = intent.getStringExtra("content");

        Log.d("!! UPDATE EMOTION !!", Integer.toString(emotion));

        ((RadioButton)updateRdoGroupIcon.getChildAt(emotion-1)).setChecked(true);
        updateTitle.setText(title);
        updateContent.setText(content);

        btnUpdateSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 수정한 내용 DB에 반영
                sqlDB = myHelper.getWritableDatabase();
                emotion = updateRdoGroupIcon.getCheckedRadioButtonId();
                emotion = (emotion + 3) % 4 + 1;
                //emotion = emotion - 4;  // calibration
                title = updateTitle.getText().toString();
                content = updateContent.getText().toString();
                Log.d("!!!! EMOTION !!!!", Integer.toString(emotion));
                Log.d("!!!! TITLE !!!!", title);
                Log.d("!!!! CONTENT !!!!", content);
                Log.d("!!!! DATE !!!!", date);

                sqlDB.execSQL("update diaryTBL set emotion="+emotion+", title='"+title+"', content='"+content+"' where date='"+date+"'");
                sqlDB.close();
                Intent goListIntent = new Intent(getApplicationContext(), ListActivity.class);
                startActivity(goListIntent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        AlertDialog.Builder dlg = new AlertDialog.Builder(UpdateActivity.this);
        dlg.setTitle("수정 취소");
        dlg.setMessage("수정한 내용이 저장되지 않았습니다!\n정말 뒤로 가시겠습니까?");
        dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UpdateActivity.super.onBackPressed();
            }
        });
        dlg.setNegativeButton("아니오", null);
        dlg.show();
    }
}
