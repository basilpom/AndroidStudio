package com.example.actionbar01;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements ActionBar.TabListener{
    ActionBar.Tab tabSong, tabArtist, tabAlbum;
    MyTabFragment myFrags[] = new MyTabFragment[3]; // tab 별로 화면(fragment)을 저장하는 배열

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar bar = getSupportActionBar();
        bar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);

        tabSong = bar.newTab();
        tabSong.setText("음악별");
        tabSong.setTabListener(this);
        bar.addTab(tabSong);

        tabArtist = bar.newTab();
        tabArtist.setText("가수별");
        tabArtist.setTabListener(this);
        bar.addTab(tabArtist);

        tabAlbum = bar.newTab();
        tabAlbum.setText("앨범별");
        tabAlbum.setTabListener(this);
        bar.addTab(tabAlbum);
//        setContentView(R.layout.activity_main);
    }
    // Cutsom View
    public static class MyTabFragment extends Fragment{
        String tabName;

        // tab을 선택하면 onCreateView 생성자 호출
        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            Bundle data = getArguments();
            tabName = data.getString("tabName");    // get the tab name
        }

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            LinearLayout baseLayout = new LinearLayout(super.getActivity());
            baseLayout.setOrientation(LinearLayout.VERTICAL);
            baseLayout.setLayoutParams(params);

            // tab 에 따라 다른 내용 보여주도록
            if(tabName.equals("음악별"))
            {
                baseLayout.setBackgroundColor(Color.RED);
                TextView textView = new TextView(super.getActivity());
                textView.setText("음악별");
            }
            if(tabName.equals("가수별"))
            {
                baseLayout.setBackgroundColor(Color.GREEN);
                TextView textView = new TextView(super.getActivity());
                textView.setText("가수별");
            }
            if(tabName.equals("앨범별"))
            {
                baseLayout.setBackgroundColor(Color.BLUE);
                TextView textView = new TextView(super.getActivity());
                textView.setText("앨범별");
            }

            return baseLayout;
        }
    }
    @Override
    public void onTabSelected(ActionBar.Tab tab, FragmentTransaction ft) {
        MyTabFragment myTabFrag = null;
        // tab이 처음 선택된 경우 : fragment 생성
        if(myFrags[tab.getPosition()] == null)
        {
            myTabFrag = new MyTabFragment();
            // Bundle : data 생성 시 사용하는 class. map처럼 되어있음(key and value)
            Bundle data = new Bundle();
            data.putString("tabName", tab.getText().toString());  // key, value
            myTabFrag.setArguments(data);
            myFrags[tab.getPosition()] = myTabFrag;     // 생성된 fragment를 배열에 저장
        }
        // tab을 선택한 적이 있는 경우 : 배열에 저장된 값 불러오기
        else
        {
            myTabFrag = myFrags[tab.getPosition()];
        }

        ft.replace(android.R.id.content, myTabFrag);
    }


    @Override
    public void onTabUnselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }

    @Override
    public void onTabReselected(ActionBar.Tab tab, FragmentTransaction ft) {

    }
}
