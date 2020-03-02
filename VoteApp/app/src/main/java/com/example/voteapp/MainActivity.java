package com.example.voteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import javax.xml.transform.Result;

public class MainActivity extends AppCompatActivity {
    ImageView pic01, pic02, pic03;
    Button btnGoResult;
    int rate01 = 0, rate02 = 0, rate03 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        pic01 = findViewById(R.id.pic01);
        pic02 = findViewById(R.id.pic02);
        pic03 = findViewById(R.id.pic03);
        btnGoResult = findViewById(R.id.btnGoResult);

        pic01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate01++;
                Toast.makeText(MainActivity.this, "pic01 총"+rate01+"표", Toast.LENGTH_SHORT).show();
            }
        });

        pic02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate02++;
                Toast.makeText(MainActivity.this, "pic02 총"+rate02+"표", Toast.LENGTH_SHORT).show();
            }
        });

        pic03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rate03++;
                Toast.makeText(MainActivity.this, "pic03 총"+rate03+"표", Toast.LENGTH_SHORT).show();
            }
        });

        btnGoResult.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ResultActivity.class);
                intent.putExtra("rate01", rate01);
                intent.putExtra("rate02", rate02);
                intent.putExtra("rate03", rate03);
                startActivity(intent);
            }
        });
    }
}
