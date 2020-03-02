package com.example.thread01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.SystemClock;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;

public class MainActivity extends AppCompatActivity {
    SeekBar seek01, seek02;
    Button btnStart, btnAsync;

    class MyAsyncTask extends AsyncTask<String, String, Void>{
        @Override                       // 가변 parameter
        protected void onProgressUpdate(String... values) {
            // view 변경 코드
            // UI Thread 에 요청하여 SeekBar 변경
            seek01.setProgress(Integer.parseInt(values[0]));
            seek02.setProgress(Integer.parseInt(values[1]));
        }

        @Override
        protected Void doInBackground(String... strings) {
            //주작업은 여기에 코딩
            Log.d("=============","doInBackground()실행됨:");

            for(int i=0;i<=100;i++) {
                //onProgressUpdate호출. 직접 호출X. publishProgress로
                publishProgress(Integer.toString(2*i), Integer.toString(i));
                SystemClock.sleep(100);//0.1초 지연
            }
            return null;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        seek01 = findViewById(R.id.seek01);
        seek02 = findViewById(R.id.seek02);
        btnStart = findViewById(R.id.btnStart);
        btnAsync = findViewById(R.id.btnAsync);

        // RunonUiThread
        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 익명 thread 클래스
                new Thread(){
                    public void run(){
                        for(int i = seek01.getProgress(); i < 100; i += 2)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    seek01.setProgress(seek01.getProgress() + 2);
                                }
                            });
                            // 너무 빨리 실행되는 것을 막기 위해 100ms 지연
                            SystemClock.sleep(100);
                        }
                    }
                }.start();

                new Thread(){
                    public void run(){
                        for(int i = seek02.getProgress(); i < 100; i += 1)
                        {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    seek02.setProgress(seek02.getProgress() + 1);
                                }
                            });
                            // 너무 빨리 실행되는 것을 막기 위해 100ms 지연
                            SystemClock.sleep(100);
                        }
                    }
                }.start();
            }
        });

        //AsyncTask
        btnAsync.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAsyncTask().execute();
            }
        });
    }

}
