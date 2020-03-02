package com.example.fileio01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    Button btnWrite, btnRead;
    Button btnSDcard;
    EditText editText, editText2;
    Button btnMkdir, btnRmdir;
    Button btnRaw;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnWrite = findViewById(R.id.btnWrite);
        btnRead = findViewById(R.id.btnRead);
        btnSDcard = findViewById(R.id.btnSD);
        editText = findViewById(R.id.editText);
        btnMkdir = findViewById(R.id.btnMkdir);
        btnRmdir = findViewById(R.id.btnRmdir);
        btnRaw = findViewById(R.id.btnRaw);
        editText2 = findViewById(R.id.editText2);

        btnRaw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    InputStream rawIS = getResources().openRawResource(R.raw.raw_test);
                    byte[] txt = new byte[rawIS.available()];
                    rawIS.read(txt);
                    editText2.setText(new String(txt));
                    rawIS.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        final String strSDpath = Environment.getExternalStorageDirectory().getAbsolutePath();
        final File myDir = new File(strSDpath+"/mydir");
        btnMkdir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDir.mkdir();
                Toast.makeText(MainActivity.this, "디렉토리 생성 완료!", Toast.LENGTH_SHORT).show();
            }
        });

        btnRmdir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myDir.delete();
                Toast.makeText(MainActivity.this, "디렉토리 삭제 완료!", Toast.LENGTH_SHORT).show();
            }
        });

        // SD CARD 사용을 위한 권한을 사용자에게 요청. 마시멜로부터 적용
        ActivityCompat.requestPermissions(this, new String[] {Manifest.permission.WRITE_EXTERNAL_STORAGE}, MODE_PRIVATE);

        btnSDcard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    //FileInputStream inFs = new FileInputStream("/sdcard/sd.txt");
                    FileInputStream inFs = new FileInputStream(strSDpath+"/sd.txt");
                    Log.d("!!!!!!! SD CARD PATH : ",strSDpath);
                    byte[] txt = new byte[inFs.available()];
                    inFs.read(txt);
                    editText.setText(new String(txt));
                    inFs.close();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
        });

        btnWrite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    FileOutputStream outFs = openFileOutput("file.txt", Context.MODE_PRIVATE);
                    String str = "file output stream test";
                    outFs.write(str.getBytes());
                    outFs.close();
                    Toast.makeText(getApplicationContext(), "Writing done", Toast.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        btnRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try
                {
                    FileInputStream inFs = openFileInput("file.txt");
                    byte[] txt = new byte[30];
                    inFs.read(txt);
                    String str = new String(txt);
                    Toast.makeText(getApplicationContext(), str, Toast.LENGTH_SHORT).show();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });
    }
}
