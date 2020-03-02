package com.cookandroid.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.Properties;

public class ThirdActivity extends AppCompatActivity {
    String num;
    TextView tvTitle, tvName, tvEmail, tvWritedate, tvContent;
    DownloadWebpageTask myTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        tvTitle = findViewById(R.id.tvTitle);
        tvName = findViewById(R.id.tvName);
        tvEmail = findViewById(R.id.tvEmail);
        tvWritedate = findViewById(R.id.tvWritedate);
        tvContent = findViewById(R.id.tvContent);

        Intent intent = getIntent();
        num = intent.getStringExtra("num");
        Toast.makeText(this, num+"번째글!", Toast.LENGTH_SHORT).show();

        String url = "http://10.0.2.2:8181/AndroidConn01/BoardView.jsp";
        // AsyncTask 실행
        myTask = new DownloadWebpageTask();
        myTask.execute(url);

    }

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

    class DownloadWebpageTask extends AsyncTask<String, Void, String> {
        private String downloadUrl(String myurl) throws IOException {

            HttpURLConnection conn = null;
            try {

                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();

                /* post방식으로 처리 시작 ==========================*/
                conn.setDoInput(true);
                conn.setDoOutput(true);

                conn.setUseCaches(false);
                // form tag 처리하듯 하라고 서버에 말해줌
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");

                DataOutputStream out = null;

                out = new DataOutputStream(conn.getOutputStream());
                Properties prop = new Properties();
                ///////////////////////////// server 에 전달할 값 설정.
                prop.setProperty("num", num);

                String encodedString = encodeString(prop);

                out.writeBytes(encodedString);
                out.flush();
                /* post방식으로 처리 끝 ===============================*/

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
                JSONArray jArr = json.getJSONArray("board");

                for (int i=0; i<jArr.length(); i++) {
                    json = jArr.getJSONObject(i);
                    String title = json.getString("title");
                    String name = json.getString("name");
                    String email = json.getString("email");
                    String writedate = json.getString("writedate");
                    String content = json.getString("content");

                    Log.d("!!! TITLE !!!", title);
                    tvTitle.setText(title);
                    tvName.setText(name);
                    tvEmail.setText(email);
                    tvWritedate.setText(writedate);
                    tvContent.setText(content);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
