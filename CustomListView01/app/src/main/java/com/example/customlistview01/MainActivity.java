package com.example.customlistview01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.os.Bundle;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ListView listview ;
        ListViewAdapter adapter;

        // Adapter 생성
        adapter = new ListViewAdapter() ;

        // 리스트뷰 참조 및 Adapter달기
        listview = findViewById(R.id.listView01);
        listview.setAdapter(adapter);

        // ArrayList에 첫 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.baseline_account_box_black_18dp),
                "Box", "Account Box Black 36dp") ;
        // 두 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.baseline_account_circle_black_18dp),
                "Circle", "Account Circle Black 36dp") ;
        // 세 번째 아이템 추가.
        adapter.addItem(ContextCompat.getDrawable(this, R.mipmap.baseline_face_black_18dp),
                "Face", "Face Black 36dp") ;
    }
}
