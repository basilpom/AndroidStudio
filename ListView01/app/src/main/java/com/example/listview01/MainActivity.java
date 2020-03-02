package com.example.listview01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listView);

        // 1. 배열 만들기
        final String[] mid = {"히어로즈", "24시", "로스트", "로스트룸", "스몰빌",
                "탐정몽크", "빅뱅이론", "프렌즈", "덱스터", "글리", "슈퍼내츄럴"};
        // 2. ArrayAdapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_multiple_choice, mid);
        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        // 3. setAdapter 를 사용하여 listView 에 적용
        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, mid[position], Toast.LENGTH_SHORT).show();
            }
        });
    }
}
