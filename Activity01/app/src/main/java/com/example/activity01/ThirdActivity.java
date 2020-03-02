package com.example.activity01;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class ThirdActivity extends AppCompatActivity {
    Button btnGoFirst;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        int age = intent.getIntExtra("age", 0);
        System.out.println("HERE IS THIRD ACTIVITY!"+name+age);

        // 넘어온 값 처리 후 message를 다시 보냄
        Intent intent2 = new Intent(getApplicationContext(), MainActivity.class);
        intent2.putExtra("message", "Success!");
        setResult(RESULT_OK, intent2);

        finish();   // 현재 Activity 종료
    }
}
