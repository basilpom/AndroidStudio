package com.cookandroid.json01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.InputStream;

public class MainActivity extends AppCompatActivity {
    TextView data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        data = findViewById(R.id.data);
        String file = "customers.json";
        String result = "";
        try
        {
            InputStream is = getAssets().open(file);
            int size = is .available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            result = new String(buffer, "utf-8");

            JSONObject json = new JSONObject(result);
            JSONArray jArr = json.getJSONArray("customers");

            for(int i = 0; i < jArr.length(); i++)
            {
                json = jArr.getJSONObject(i);
                String name = json.getString("name");
                String address = json.getString("address");
                // chaining 방식으로도 가능!
                // String name = jArr.getJSONObject(i).getString("name");

                Log.d("!!! customer !!!", name+address);
                data.append("name : "+name+" address : "+address+"\n");
            }
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

    }
}
