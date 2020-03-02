package com.example.menuexample;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MainActivity extends AppCompatActivity {
    View dialogView;
    RadioGroup rdoGrp;
    RadioButton rdoRed, rdoBlue, rdoGreen;
    LinearLayout mainLayout;
    EditText editID, editPW, editName, editEmail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mainLayout = findViewById(R.id.mainLayout);
        try 
        {
            FileInputStream inFs = openFileInput("config.txt");
            byte[] txt = new byte[6];
            inFs.read(txt);
            String color = new String(txt);
            mainLayout.setBackgroundColor(Color.parseColor("#"+color));
            Toast.makeText(getApplicationContext(), "배경색 불러오기 완료", Toast.LENGTH_SHORT).show();
            inFs.close();
        }
        catch(Exception e)
        {
            Toast.makeText(getApplicationContext(), "저장된 배경색 없음", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        MenuInflater mInflater = getMenuInflater();
        mInflater.inflate(R.menu.menu01, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            // 연습문제 1-3 번
            case R.id.menuSetting:
                dialogView = View.inflate(MainActivity.this, R.layout.dialog, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("배경 색상 선택");
                dlg.setView(dialogView);
                dlg.setPositiveButton("저장", new DialogInterface.OnClickListener() {
                    // 저장을 누르면 config.txt file 에 색상 저장
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        rdoGrp = dialogView.findViewById(R.id.rdoGrp);
                        rdoRed = dialogView.findViewById(R.id.rdoRed);
                        rdoBlue = dialogView.findViewById(R.id.rdoBlue);
                        rdoGreen = dialogView.findViewById(R.id.rdoGreen);
                        try
                        {
                            FileOutputStream outFs = openFileOutput("config.txt", Context.MODE_PRIVATE);
//                            int color = 0;
                            String color = "";
                            switch(rdoGrp.getCheckedRadioButtonId())
                            {
                                case R.id.rdoRed :
                                    color = "FF0000";
                                    break;
                                case R.id.rdoBlue :
                                    color = "0000FF";
                                    break;
                                case R.id.rdoGreen :
                                     color = "008000";
                                    break;
                            }
                            outFs.write(color.getBytes());
                            outFs.close();
                            Toast.makeText(getApplicationContext(), "색상 저장 완료", Toast.LENGTH_SHORT).show();
                        }
                        catch(Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                });
                dlg.setNegativeButton("닫기", null);
                dlg.show();
                return true;
            // 연습문제 4번
            case R.id.menuJoin :
                dialogView = View.inflate(MainActivity.this, R.layout.dialog_join, null);
                AlertDialog.Builder dlgJoin = new AlertDialog.Builder(MainActivity.this);
                dlgJoin.setTitle("회원 가입");
                dlgJoin.setView(dialogView);

                dlgJoin.setPositiveButton("가입", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        editID = dialogView.findViewById(R.id.editID);
                        editPW = dialogView.findViewById(R.id.editPW);
                        editName = dialogView.findViewById(R.id.editName);
                        editEmail = dialogView.findViewById(R.id.editEmail);
                        Log.d("ID : ", editID.getText().toString());
                        Log.d("PW : ", editPW.getText().toString());
                        Log.d("Name : ", editName.getText().toString());
                        Log.d("E-mail :", editEmail.getText().toString());
                    }
                });
                dlgJoin.setNegativeButton("취소", null);
                dlgJoin.show();
        }
        return false;
    }
}
