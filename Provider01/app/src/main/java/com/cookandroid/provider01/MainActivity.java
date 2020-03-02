package com.cookandroid.provider01;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.CallLog;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity {
    Button btnLog;
    EditText edtLog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 권한 요청
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CALL_LOG}, MODE_PRIVATE);

        btnLog = findViewById(R.id.btnLog);
        edtLog = findViewById(R.id.edtLog);

        btnLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edtLog.setText(getCallHistory());
            }
        });
    }

    public String getCallHistory(){
        String[] callSet = new String[]{ CallLog.Calls.DATE,
                                         CallLog.Calls.TYPE,
                                         CallLog.Calls.NUMBER,
                                         CallLog.Calls.DURATION };

        Cursor c = getContentResolver().query(CallLog.Calls.CONTENT_URI, callSet, null, null, null);
        if(c == null)
        {
            return "통화기록 없음";
        }

        StringBuffer callBuff = new StringBuffer();
        callBuff.append("\n날짜   :   구분  :   전화번호    :   통화시간\n\n");
        c.moveToFirst();
        do
        {
            long callDate = c.getLong(0);
            SimpleDateFormat datePattern = new SimpleDateFormat("yyyy-MM-dd");
            String date_str = datePattern.format(new Date(callDate));
            callBuff.append(date_str+"  :   ");
            if(c.getInt(1) == CallLog.Calls.INCOMING_TYPE)
                callBuff.append("수신");
            else
                callBuff.append("발신");

            callBuff.append(c.getString(2)+"    :   ");
            callBuff.append(c.getString(3)+"    :   ");

        }
        while(c.moveToNext());

        return callBuff.toString();
    }
}
