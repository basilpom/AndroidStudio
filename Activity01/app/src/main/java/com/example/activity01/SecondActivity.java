package com.example.activity01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    Button btnGoBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        btnGoBack = findViewById(R.id.btnGoBack);

        btnGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart Main Activity
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("message", "Success");
                finish();
            }
        });
        Intent intent = getIntent();
        Log.d("!!!!!!! name : ", intent.getStringExtra("name"));
        Log.d("!!!!!!! age : ", Integer.toString(intent.getIntExtra("age", 0)));

    }
}
