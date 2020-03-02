package com.cookandroid.battery01;

import androidx.appcompat.app.AppCompatActivity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {
    ImageView ivBattery;
    EditText edtBattery;
    BroadcastReceiver br;

    @Override
    protected void onPause() {
        super.onPause();
        unregisterReceiver(br);
    }

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter iFilter = new IntentFilter();
        // 배터리 변경 시에만 메세지 수신
        iFilter.addAction(Intent.ACTION_BATTERY_CHANGED);
        registerReceiver(br, iFilter);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivBattery = findViewById(R.id.ivBattery);
        edtBattery = findViewById(R.id.edtBattery);

        br = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if(action.equals(Intent.ACTION_BATTERY_CHANGED))
                {
                    // 배터리 잔량
                    int remain = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
                    edtBattery.setText("현재 충전량 : "+remain+"%\n");

                    if(remain >= 90)
                        ivBattery.setImageResource(R.mipmap.battery_100);
                    else if(remain >= 80)
                        ivBattery.setImageResource(R.mipmap.battery_80);
                    else if(remain >= 60)
                        ivBattery.setImageResource(R.mipmap.battery_60);
                    else if(remain >= 20)
                        ivBattery.setImageResource(R.mipmap.battery_20);
                    else
                        ivBattery.setImageResource(R.mipmap.battery_0);

                    // 전원 연결 상태
                    int plug = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
                    switch (plug)
                    {
                        case 0 :
                            edtBattery.append("전원 연결 : 안됨");
                            break;
                        case BatteryManager.BATTERY_PLUGGED_AC :
                            edtBattery.append("전원 연결 : 어댑터 연결됨");
                            break;
                        case BatteryManager.BATTERY_PLUGGED_USB :
                            edtBattery.append("전원 연결 : USB 연결됨");
                            break;
                    }
                }
            }
        };

    }
}
