package com.example.voteapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Rating;
import android.os.Bundle;
import android.widget.RatingBar;

public class ResultActivity extends AppCompatActivity {
    int rate01, rate02, rate03;
    RatingBar rating01, rating02, rating03;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        rating01 = findViewById(R.id.rating01);
        rating02 = findViewById(R.id.rating02);
        rating03 = findViewById(R.id.rating03);

        Intent intent = getIntent();
        rate01 = intent.getIntExtra("rate01", 0);
        rate02 = intent.getIntExtra("rate02", 0);
        rate03 = intent.getIntExtra("rate03", 0);

        rating01.setRating(rate01);
        rating02.setRating(rate02);
        rating03.setRating(rate03);
    }
}
