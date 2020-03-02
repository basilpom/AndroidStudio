package com.example.autocomplete01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

public class MainActivity extends AppCompatActivity {
    AutoCompleteTextView auto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auto = findViewById(R.id.autoCompleteTextView);
        String[] items = {"CSI-NewYork", "CSI-Miami", "CSI-LasVegas", "Friends", "Fringe", "Lost"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, items);
        auto.setAdapter(adapter);

    }
}
