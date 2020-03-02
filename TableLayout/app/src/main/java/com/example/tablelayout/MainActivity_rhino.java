package com.example.tablelayout;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Iterator;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;


public class MainActivity_rhino extends AppCompatActivity {
    public float calculator(String exp){
        float result = 0;
        String expression = exp;
        String[] splits = exp.split(" ");    // space를 기준으로 나눈 것들
        ArrayList<Float> nums = new ArrayList<>();
        ArrayList<String> cals = new ArrayList<>();

        for(int i=0; i < splits.length; i++){
            System.out.println(splits[i]);
            try
            {
                nums.add(Float.parseFloat(splits[i]));
                System.out.println("숫자!");
            }
            catch(NumberFormatException e)
            {
                cals.add(splits[i]);
                System.out.println("연산자!");
            }
        }

        // 곱셈, 나눗셈 먼저
        Iterator<String> it = cals.iterator();
        int j = 0;
        while(it.hasNext())
        {
            String cal = it.next();
            if(cal.equals("*"))
            {
                result = nums.get(j) * nums.get(j+1);

                nums.remove(j);
                nums.remove(j);
                nums.add(j, result);
            }
            else if(cal.equals("/"))
            {
                result = nums.get(j) / nums.get(j+1);
                nums.remove(j);
                nums.remove(j);
                nums.add(j, result);
            }
            else
            {
                j++;
            }
        }

        // 덧셈, 뺄셈
        int k = 0;
        Iterator<String> it2 = cals.iterator();
        while(it2.hasNext())
        {
            String cal = it2.next();
            if(cal.equals("+"))
            {
                result = nums.get(k) + nums.get(k+1);
                nums.remove(k);
                nums.remove(k);
                nums.add(k, result);
            }
            else if(cal.equals("-"))
            {
                result = nums.get(k) - nums.get(k+1);
                nums.remove(k);
                nums.remove(k);
                nums.add(k, result);
            }
            else
            {
                k++;
            }
        }
        return result;
    }

    EditText expressionText;
    String expression;
    Button[] numButtons = new Button[10];
    int[] numBtnIDs = {R.id.BtnNum0, R.id.BtnNum1, R.id.BtnNum2, R.id.BtnNum3, R.id.BtnNum4,
                       R.id.BtnNum5, R.id.BtnNum6, R.id.BtnNum7, R.id.BtnNum8, R.id.BtnNum9};
    Button[] calButtons = new Button[4];
    int[] calBtnIDs = {R.id.BtnAdd, R.id.BtnSub, R.id.BtnMul, R.id.BtnDiv};
    Button btnEnter, btnClear;
    TextView textResult;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example);
        // ID로 모든 View 찾아오기
        // 계산기 숫자 찾아오고, Click Event 등록
        expressionText = findViewById(R.id.Edit1);

        for(int i=0; i < 10; i++)
        {
            final int index = i;

            numButtons[i] = findViewById(numBtnIDs[i]);
            numButtons[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(expressionText.isFocused())
                    {
                        expression = expressionText.getText().toString()+numButtons[index].getText().toString();
                        expressionText.setText(expression);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "숫자를 입력할 곳을 선택하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        // 계산기 연산자 찾아오고, Click Event 등록
        for(int i=0; i < 4; i++)
        {
            final int index = i;

            calButtons[i] = findViewById(calBtnIDs[i]);
            calButtons[index].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(expressionText.isFocused())
                    {
                        // 연산자만 연속으로 입력되지 않도록 체크
                        String lastString = expression.substring(expression.length()-1, expression.length());
                        try {
                            // 식의 마지막 글자가 숫자라면 아무 처리 X
                            Float.parseFloat(lastString);
//                            Integer.parseInt(lastString);
                        }
                        catch(NumberFormatException e)
                        {
                            // 식의 마지막 글자가 연산자라면 지우기
                            expression = expression.substring(0, expression.length()-1);
                        }
                        expression = expression.concat(" ").concat(calButtons[index].getText().toString()).concat(" ");
                        expressionText.setText(expression);
                    }
                    else
                    {
                        Toast.makeText(getApplicationContext(), "연산자를 입력할 곳을 선택하세요.", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        btnEnter = findViewById(R.id.btnEnter);
        btnClear = findViewById(R.id.btnClear);
        textResult = findViewById(R.id.TextResult);

        btnEnter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String expression = expressionText.getText().toString();
                ScriptEngine engine = new ScriptEngineManager().getEngineByName("rhino");
                try {
                    Object result = engine.eval(expression);
                    System.out.println("**********"+expression+"="+result);
                    expressionText.setText("계산 결과 : "+result);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                expression = "";
                expressionText.setText(expression);
            }
        });

    }
}
