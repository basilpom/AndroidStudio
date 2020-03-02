package com.cookandroid.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.Properties;

public class SecondActivity extends AppCompatActivity {
    ListView listView01;
    ArrayList<String> numList, titleList;
    ArrayAdapter adapter;
    DownloadWebpageTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        listView01 = findViewById(R.id.listView01);

        numList = new ArrayList<String>();
        titleList = new ArrayList<String>();

        String url = "http://10.0.2.2:8181/AndroidConn01/BoardList.jsp";
        // AsyncTask 실행
        myTask = new DownloadWebpageTask();
        myTask.execute(url);

        // item click 하면 글 상세보기
        listView01.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), ThirdActivity.class);
                intent.putExtra("num", numList.get(position));
                startActivity(intent);
                //finish();   // 갱신되는 내용이 있다면 finish!!!!!!!
            }
        });

    }
    // Parameter 를 인코딩하여 문자열로 리턴
    public static String encodeString(Properties params) {
        StringBuffer sb = new StringBuffer(256);
        Enumeration names = params.propertyNames();

        while (names.hasMoreElements()) {
            String name = (String) names.nextElement();
            String value = params.getProperty(name);
            try{
                sb.append(URLEncoder.encode(name) + "=" + URLEncoder.encode(value,"utf-8") );
            }catch(Exception e){
                e.printStackTrace();
            }

            if (names.hasMoreElements()) sb.append("&");
        }
        return sb.toString();
    }

    // Download AsyncTask
    class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        private String downloadUrl(String myurl) throws IOException {

            HttpURLConnection conn = null;
            try {
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();

                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
                String line = null;
                String page = "";
                while((line = bufreader.readLine()) != null) {
                    page += line;
                }
                return page;
            } finally {
                conn.disconnect();
            }
        }
        @Override
        protected String doInBackground(String... urls) {
            // server에 요청
            try {
                return downloadUrl(urls[0]);
            } catch (IOException e) {
                return "다운로드 실패";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject json = new JSONObject(result);
                JSONArray jArr = json.getJSONArray("list");

                for (int i=0; i<jArr.length(); i++) {
                    json = jArr.getJSONObject(i);
                    // json 에서 받아온 값이 여러개라면 ArrayList 에 넣어서!
                    // 값을 VO에 넣어서 그 값을 ArrayList 에 추가
                    String num = json.getString("num");
                    String title = json.getString("title");
                    numList.add(num);
                    titleList.add(title);
                    // file 을 server 로 보내고 싶다면 cos.jar library 그대로 사용.(JSP Movie 참고)
                }
                adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1, titleList);
                // view 변경 코드는 onPostExecute 에!!!
                listView01.setAdapter(adapter);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
