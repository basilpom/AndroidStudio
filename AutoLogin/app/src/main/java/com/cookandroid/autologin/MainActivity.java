package com.cookandroid.autologin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;

public class MainActivity extends AppCompatActivity {
    Button btnLogin;
    CheckBox autoCheck;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Auto Login Check
        SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        String autoLogin = mPref.getString("AutoLogin", null);
        if(autoLogin != null && autoLogin.equals("OK"))
        {
            Intent intent = new Intent(getBaseContext(), SecondActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        setContentView(R.layout.activity_main);

        btnLogin = findViewById(R.id.btnLogin);
        autoCheck = findViewById(R.id.autoCheck);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(autoCheck.isChecked())
                {
                    SharedPreferences mPref = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
                    SharedPreferences.Editor editor = mPref.edit();
                    editor.putString("AutoLogin", "OK");
                    editor.commit();
                }
                Intent intent = new Intent(getBaseContext(), SecondActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
