package com.example.reservation;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.Chronometer;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {
    Chronometer chronometer;
    Button btnStart, btnEnd;
    RadioGroup rdoGrp;
    RadioButton rdoCal, rdoTime;
    CalendarView calendarView;
    TimePicker timePicker;
    TextView timeTextView;
    int year, month, day, hour, minute;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        chronometer = findViewById(R.id.chronometer01);
        btnStart = findViewById(R.id.BtnStart);
        btnEnd = findViewById(R.id.BtnEnd);
        rdoGrp = findViewById(R.id.rdoGrp);
        rdoCal = findViewById(R.id.rdoCal);
        rdoTime = findViewById(R.id.rdoTime);
        calendarView = findViewById(R.id.calendarView01);
        timePicker = findViewById(R.id.timePicker01);
        timeTextView = findViewById(R.id.timeTextView);

        // 처음에는 안 보이게
        rdoGrp.setVisibility(View.INVISIBLE);
        calendarView.setVisibility(View.INVISIBLE);
        timePicker.setVisibility(View.INVISIBLE);

        // 시작 버튼 누르면 chronometer 시간 가도록
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.setBase(SystemClock.elapsedRealtime());
                chronometer.start();
                rdoGrp.setVisibility(View.VISIBLE);
            }
        });
        // RadioButton 선택 시 날짜/시간 보이도록
        rdoCal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setVisibility(View.VISIBLE);
                timePicker.setVisibility(View.INVISIBLE);
            }
        });
        rdoTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendarView.setVisibility(View.INVISIBLE);
                timePicker.setVisibility(View.VISIBLE);
            }
        });
        // 날짜/시간 선택
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(@NonNull CalendarView view, int y, int m, int d) {
                year = y;
                month = m + 1;
                day = d;
            }
        });
        timePicker.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @Override
            public void onTimeChanged(TimePicker view, int h, int m) {
                hour = h;
                minute = m;
            }
        });
        // 예약 완료 누르면 멈추도록
        btnEnd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chronometer.stop();
                timeTextView.setText(year+"년 "+month+"월 "+day+"일 "+hour+"시 "+minute+"분");
            }
        });


    }
}
