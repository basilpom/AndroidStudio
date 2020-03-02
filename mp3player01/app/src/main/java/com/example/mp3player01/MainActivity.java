package com.example.mp3player01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView01;
    Button btnStart, btnStop;
    TextView textView01, textView02;
    SeekBar seekBar01;
    // mp3 목록 구하기
    String mp3Path = Environment.getExternalStorageDirectory().getPath()+"/";
    MediaPlayer mPlayer;
    ArrayList<String> mp3List;
    String selectedMP3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // SD Card 권한 요청
        ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        setContentView(R.layout.activity_main);

        listView01 = findViewById(R.id.listView01);
        btnStart = findViewById(R.id.btnStart);
        btnStop = findViewById(R.id.btnStop);
        textView01 = findViewById(R.id.textView01);
        textView02 = findViewById(R.id.textView02);
        seekBar01 = findViewById(R.id.seekBar01);

        mPlayer = new MediaPlayer();

        // ArrayList 생성
        mp3List = new ArrayList<String>();
        File[] listFiles = new File(mp3Path).listFiles();
        String fileName, extName;
        for(File file : listFiles)
        {
            fileName = file.getName();
            extName = fileName.substring(fileName.length() - 3);
            if(extName.equals("mp3"))
            {
                mp3List.add(fileName);
            }
        }

        // ArrayAdapter 생성
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_single_choice, mp3List);
        // ListView 에 adapter 적용
        listView01.setAdapter(adapter);
        listView01.setChoiceMode(ListView.CHOICE_MODE_SINGLE);
        // listview 에서 선택
        listView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                selectedMP3 = mp3List.get(position);
                try
                {
                    mPlayer.reset();
                    mPlayer.setDataSource(mp3Path + selectedMP3);
                    mPlayer.prepare();  // start 전 준비
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        btnStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {

//                    mPlayer.setLooping(true); // 반복재생
                    mPlayer.start();
                    textView01.setText("실행중인 음악 : "+selectedMP3);
                    btnStart.setClickable(false);
                    btnStop.setClickable(true);
                    new Thread(){
                        // 시간 출력 형식
                        SimpleDateFormat timeFormat = new SimpleDateFormat("mm:ss");
                        public void run(){
                            // media palyer 미생성시 중지
                            if(mPlayer == null) return;
                            // seekbar 의 최대값을 mp3의 플레이 시간으로
                            seekBar01.setMax(mPlayer.getDuration());
                            while(mPlayer.isPlaying())
                            {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        seekBar01.setProgress(mPlayer.getCurrentPosition());
                                        textView02.setText("진행 시간 : "+timeFormat.format(mPlayer.getCurrentPosition()));
                                    }
                                });
                                SystemClock.sleep(200);
                            }
                        }
                    }.start();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    mPlayer.pause();
//                mPlayer.reset();
                    btnStart.setClickable(true);
                    btnStop.setClickable(false);
                    textView01.setText("실행중인 음악 : ");
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

    }
}
