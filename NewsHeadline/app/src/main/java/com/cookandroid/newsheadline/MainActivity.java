package com.cookandroid.newsheadline;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listNews;
    // 1. ArrayList for ListView
    ArrayList<String> headlines = new ArrayList<String>();
    // link for go to news page
    ArrayList<String> link = new ArrayList<String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listNews = findViewById(R.id.listNews);
        new JsoupAsyncTask().execute();
        // Adapter
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, headlines);
        // setAdapter on ListView
        listNews.setAdapter(adapter);

        // ListView 각 목록 클릭 시 링크로 이동
        listNews.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String url = "https://news.naver.com"+link.get(position);
                Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("http://news.naver.com").get();
                Elements newsHeadlines = doc.select(".hdline_article_tit a");
                for (Element headline : newsHeadlines) {
//                    Log.d("!!!!!!!! HEADLINE : ", headline.text());
                    // 2. parsing 한 데이터 arraylist 에 넣기
                    headlines.add(headline.text());
                    link.add(headline.attr("href"));
                    Log.d("!!!!! LINK !!!!!", headline.attr("href"));
                }
            }
            catch(Exception e){
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
        }
    }
}
