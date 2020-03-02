package com.example.dialog01;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button, btnSelect, btnMulti, btnCustom;
    View dialogView;    // View for Custom Dialog
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        button = findViewById(R.id.button);
        btnSelect = findViewById(R.id.btnSelect);
        btnMulti = findViewById(R.id.btnMulti);
        btnCustom = findViewById(R.id.btnCustom);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("Delete");
                dlg.setMessage("삭제하시겠습니까?");
                dlg.setPositiveButton("예", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), "삭제되었습니다", Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.setNegativeButton("아니오", null);
                dlg.show();
            }
        });

        btnSelect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] versionArray = {"롤리팝", "마시멜로", "파이"};
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("선택");
                dlg.setSingleChoiceItems(versionArray, -1, null//new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        Toast.makeText(getApplicationContext(), versionArray[which], Toast.LENGTH_SHORT).show();
//                    }
//                }
                );
                // 보류
                dlg.setPositiveButton("선택", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(getApplicationContext(), versionArray[which], Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.show();
            }
        });

        btnMulti.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String[] versionArray = {"롤리팝", "마시멜로", "파이"};
                final boolean[] checkArray = {false, false, false};
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("다중 선택");
                dlg.setMultiChoiceItems(versionArray, checkArray, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        Toast.makeText(getApplicationContext(), versionArray[which], Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.setPositiveButton("닫기", null);
                dlg.show();
            }
        });
        // Custom Dialog
        btnCustom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogView = View.inflate(MainActivity.this, R.layout.dialog01, null);
                AlertDialog.Builder dlg = new AlertDialog.Builder(MainActivity.this);
                dlg.setTitle("Login");
                dlg.setView(dialogView);
                dlg.setPositiveButton("Login", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // 로그인 처리
//                        Toast.makeText(getApplicationContext(), "로그인처리", Toast.LENGTH_SHORT).show();
                        EditText edtID = dialogView.findViewById(R.id.dlgID);
                        EditText edtPW = dialogView.findViewById(R.id.dlgPW);
                        Log.d("!!!!!!!!!!!!!!! ID : ", edtID.getText().toString());
                        Log.d("!!!!!!!!!!!!!!! PW : ", edtPW.getText().toString());
                        Toast.makeText(getApplicationContext(), edtID.getText().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
                dlg.setNegativeButton("Cancel", null);
                dlg.show();
            }
        });
    }
}
