package com.cookandroid.login;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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
import java.util.Enumeration;
import java.util.Properties;

public class MainActivity extends AppCompatActivity {
    EditText edtID, edtPW;
    Button btnLogin, btnJoin;
    TextView msg;
    DownloadWebpageTask myTask;

    // parameter 처럼 주소에 나오도록 변환
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtID = findViewById(R.id.edtID);
        edtPW = findViewById(R.id.edtPW);
        btnLogin = findViewById(R.id.btnLogin);
        btnJoin = findViewById(R.id.btnJoin);
        msg = findViewById(R.id.msg);

        btnLogin.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String url = "http://10.0.2.2:8181/AndroidConn01/Login.jsp";
                // AsyncTask 실행
                myTask = new DownloadWebpageTask();
                myTask.execute(url);
            }
        });
    }

    class DownloadWebpageTask extends AsyncTask<String, Void, String>{
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
                prop.setProperty("id", edtID.getText().toString());
                prop.setProperty("pw", edtPW.getText().toString());


//                    prop.setProperty("pw", JoinActivity.getHash(et2.getText().toString()));
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
                JSONArray jArr = json.getJSONArray("result");

                for (int i=0; i<jArr.length(); i++) {
                    json = jArr.getJSONObject(i);
                    // json 에서 받아온 값이 여러개라면 ArrayList 에 넣어서!
                    // file 을 server 로 보내고 싶다면 cos.jar library 그대로 사용.(JSP Movie 참고)
                    final String debugging_msg = json.getString("msg");
                    Log.d("디버깅 msg : ",debugging_msg);
                    if(debugging_msg.equals("success")){
                        msg.setText("로그인 성공");
                        // SecondActivity 로 이동하는 코드 작성
                        Intent intent = new Intent(getApplicationContext(), SecondActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        msg.setText("로그인 실패");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
