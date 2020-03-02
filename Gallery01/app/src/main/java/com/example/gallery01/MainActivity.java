package com.example.gallery01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    CheckBox cb01;
    TextView tv01;
    RadioGroup rg01;
    RadioButton rdoDog, rdoCat, rdoRabbit;
    Button btn01;
    ImageView img01;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        cb01 = findViewById(R.id.checkbox01);
        tv01 = findViewById(R.id.text01);
        rg01 = findViewById(R.id.rdoGrp01);
        btn01 = findViewById(R.id.btn01);
        img01 = findViewById(R.id.img01);
        rdoDog = findViewById(R.id.rdoDog);
        rdoCat = findViewById(R.id.rdoCat);
        rdoRabbit = findViewById(R.id.rdoRabbit);

        // 체크박스 선택되면 아래 내용 보이도록
        cb01.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(cb01.isChecked())
                {
                    tv01.setVisibility(View.VISIBLE);
                    rg01.setVisibility(View.VISIBLE);
                    btn01.setVisibility(View.VISIBLE);
                    img01.setVisibility(View.VISIBLE);
                }
                else
                {
                   tv01.setVisibility(View.INVISIBLE);
                   rg01.setVisibility(View.INVISIBLE);
                   btn01.setVisibility(View.INVISIBLE);
                   img01.setVisibility(View.INVISIBLE);
                }
            }
        });
        // radiobutton 선택된 항목에 따라 image 보여주기
        btn01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (rg01.getCheckedRadioButtonId())
                {
                    case R.id.rdoDog:
                        img01.setImageResource(R.drawable.dog);
//                        img01.setImageResource(R.drawable.dog);
                        break;
                    case R.id.rdoCat:
                        img01.setImageResource(R.drawable.cat);
                        break;
                    case R.id.rdoRabbit:
                        img01.setImageResource(R.drawable.rabbit);
                        break;
                    default:
                        Toast.makeText(getApplicationContext(), "동물을 먼저 선택하세요", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
