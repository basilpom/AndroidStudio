package com.example.gallery02;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.File;

public class MainActivity extends AppCompatActivity {
    Button btnPrev, btnNext;
    MyPictureView myPictureView;
    int curNum;         // index
    File[] imageFiles;  // image list
    String imageFname;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        setContentView(R.layout.activity_main);

        btnPrev = findViewById(R.id.btnPrev);
        btnNext = findViewById(R.id.btnNext);
        myPictureView = findViewById(R.id.myPictureView1);

        // image file 목록 구하기
        imageFiles = new File(Environment.getExternalStorageDirectory().getAbsolutePath()+"/Pictures").listFiles();
        imageFname = imageFiles[0].toString();
        myPictureView.imgPath = imageFname;

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curNum <= 0)
                {
                    Toast.makeText(MainActivity.this, "첫번째 그림입니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    curNum--;
                    imageFname = imageFiles[curNum].toString();
                    myPictureView.imgPath = imageFname;
                    myPictureView.invalidate();
                }
            }
        });
        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(curNum >= imageFiles.length - 1)
                {
                    Toast.makeText(MainActivity.this, "마지막 그림입니다.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    curNum++;
                    imageFname = imageFiles[curNum].toString();
                    myPictureView.imgPath = imageFname;
                    myPictureView.invalidate();
                }
            }
        });
    }
}
