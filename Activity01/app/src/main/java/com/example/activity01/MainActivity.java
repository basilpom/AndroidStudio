package com.example.activity01;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    Button btnGoSecond, btnGoThird;
    TextView txtResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnGoSecond = findViewById(R.id.btnGoSecond);
        btnGoThird = findViewById(R.id.btnGoThird);
        txtResult = findViewById(R.id.txtResult);

        btnGoSecond.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("age", 26);
                intent.putExtra("name", "taegyu");
                startActivity(intent);
            }
        });

        btnGoThird.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("age", 26);
                intent.putExtra("name", "taegyu");
                startActivityForResult(intent, 0);
            }
        });

        Intent intent = getIntent();
        txtResult.setText(intent.getStringExtra("message"));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode == RESULT_OK)
        {
            txtResult.setText(data.getStringExtra("message"));
//            System.out.println("!!!!!!! RESULT : " + data.getStringExtra("message"));
        }
    }

}
