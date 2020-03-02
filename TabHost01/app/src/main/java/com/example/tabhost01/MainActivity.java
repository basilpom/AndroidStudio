package com.example.tabhost01;

import androidx.appcompat.app.AppCompatActivity;

import android.app.TabActivity;
import android.os.Bundle;
import android.widget.TabHost;

public class MainActivity extends TabActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // TabHost : Container 역할
        TabHost tabHost = getTabHost();

        // TabSpec : tab
        TabHost.TabSpec tabSpec1 = tabHost.newTabSpec("tab1").setIndicator("음악별");
        tabSpec1.setContent(R.id.tabSong);
        tabHost.addTab(tabSpec1);

        TabHost.TabSpec tabSpec2 = tabHost.newTabSpec("tab2").setIndicator("가수별");
        tabSpec2.setContent(R.id.tabArtist);
        tabHost.addTab(tabSpec2);

        TabHost.TabSpec tabSpec3 = tabHost.newTabSpec("tab3").setIndicator("앨범별");
        tabSpec3.setContent(R.id.tabAlbum);
        tabHost.addTab(tabSpec3);
    }
}
