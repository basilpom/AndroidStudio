package com.cookandroid.weather;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listWeather;
    ArrayList<String> weatherList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listWeather = findViewById(R.id.listWeather);

        new JsoupAsyncTask().execute();
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, weatherList);
        listWeather.setAdapter(adapter);
    }

    private class JsoupAsyncTask extends AsyncTask<Void, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc = Jsoup.connect("https://m.kma.go.kr/m/nation/current.jsp?ele=2").get();
                Elements regions = doc.select("#index.nation_map dl dt");
                for (Element region : regions) {
//                    Log.d("!!!! REGION !!!!", region.text());
                    weatherList.add(region.text());
                }
                Elements celsiusL = doc.select("#index.nation_map dl dd span");
                int i = 0;
                for (Element celsius : celsiusL)
                {
                    String result = weatherList.get(i) + " : " + celsius.text() + "°C";
                    weatherList.set(i, result);
                    i++;
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
