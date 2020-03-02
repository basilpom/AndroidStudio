package com.example.tablelayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity_original extends AppCompatActivity {
    EditText et1, et2;
//    Button btn0, btn1, btn2, btn3, btn4, btn5, btn6, btn7, btn8, btn9;
    Button[] numButtons = new Button[10];
    int[] numBtnIDs = {R.id.BtnNum0, R.id.BtnNum1, R.id.BtnNum2, R.id.BtnNum3, R.id.BtnNum4,
                       R.id.BtnNum5, R.id.BtnNum6, R.id.BtnNum7, R.id.BtnNum8, R.id.BtnNum9};
    Button btnAdd, btnSub, btnMul, btnDiv;
    TextView textResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sub);

        et1 = findViewById(R.id.Edit1);
        et2 = findViewById(R.id.Edit2);
        for(int i=0; i < 10; i++)
        {
            final int index = i;

            numButtons[i] = findViewById(numBtnIDs[i]);
            numButtons[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(et1.isFocused())
                    {
                        String num1 = et1.getText().toString()+numButtons[index].getText().toString();
                        et1.setText(num1);
                    }
                    else if(et2.isFocused())
                    {
                        String num2 = et2.getText().toString()+numButtons[index].getText().toString();
                        et2.setText(num2);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "숫자를 입력할 곳을 선택하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        btnAdd = findViewById(R.id.BtnAdd);
        btnSub = findViewById(R.id.BtnSub);
        btnMul = findViewById(R.id.BtnMul);
        btnDiv = findViewById(R.id.BtnDiv);
        textResult = findViewById(R.id.TextResult);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "첫번째 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(et2.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "두번째 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int num1 = Integer.parseInt(et1.getText().toString());
                    int num2 = Integer.parseInt(et2.getText().toString());
                    int result = num1 + num2;
                    textResult.setText("계산 결과 : "+result);
                }
            }
        });
        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "첫번째 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(et2.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "두번째 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int num1 = Integer.parseInt(et1.getText().toString());
                    int num2 = Integer.parseInt(et2.getText().toString());
                    int result = num1 - num2;
                    textResult.setText("계산 결과 : " + result);
                }
            }
        });
        btnMul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "첫번째 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(et2.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "두번째 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int num1 = Integer.parseInt(et1.getText().toString());
                    int num2 = Integer.parseInt(et2.getText().toString());
                    int result = num1 * num2;
                    textResult.setText("계산 결과 : " + result);
                }
            }
        });
        btnDiv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(et1.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "첫번째 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else if(et2.getText().toString().equals(""))
                {
                    Toast.makeText(getApplicationContext(), "두번째 숫자를 입력하세요.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    int num1 = Integer.parseInt(et1.getText().toString());
                    int num2 = Integer.parseInt(et2.getText().toString());
                    float result = ((float) num1 / num2);
                    textResult.setText("계산 결과 : " + result);
                }
            }
        });
    }
}
