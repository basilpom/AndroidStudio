package com.cookandroid.bus01;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Xml;
import android.widget.TextView;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = findViewById(R.id.data);

        // 노선 ID 추출을 위한 공공 DB API 의 URL
        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList";
        // 자신의 공공DB API KEY
        String serviceKey = "Pjc4C%2FUaOhA7X2mii0%2BIhYvanL8YGl72rVqMRQv%2Bfi9%2FrlAElIGa37aXuhdOQwbc%2BZRoSy8t5MdsHrOe6LKAtA%3D%3D";
        // 버스 번호
        String strSrch = "406";
        // 공공 DB API 호출을 위한 URL
        String strUrl = serviceUrl + "?ServiceKey=" + serviceKey + "&strSrch=" + strSrch;

        // URL에 해당하는 문서 추출을 위한 객체 생성
        new DownloadWebpageTask().execute(strUrl);
    }

    private class DownloadWebpageTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
            try
            {
                // url에서 return 된 값을 onPostExecute로 다시 return
                return downloadUrl(urls[0]);
            }
            catch(IOException e)
            {
                e.printStackTrace();
                return "다운로드 실패";
            }
        }
        @Override
        protected void onPostExecute(String result) {
            //            tv.setText(result);
            // xml parsing
            try
            {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                String headerCd = "";
                String busRouteId = "";
                String busRouteNm = "";

                boolean bSet_headerCd = false;
                boolean bSet_busRouteId = false;
                boolean bSet_busRouteNm = false;

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    // 문서의 시작 : 아무 작업 X
                    if(eventType == XmlPullParser.START_DOCUMENT){}
                    // 시작 태그가 원하는 값이면 flag 를 true 로
                    else if (eventType == XmlPullParser.START_TAG)
                    {
                        String tag_name = xpp.getName();
                        if(tag_name.equals("headerCd"))
                        {
                            bSet_headerCd = true;
                        }
                        if(tag_name.equals("busRouteId"))
                        {
                            bSet_busRouteId = true;
                        }
                        if(tag_name.equals("busRouteNm"))
                        {
                            bSet_busRouteNm = true;
                        }
                    }
                    // 원하는 태그의 텍스트 값 가져오기. 위에서 설정한 flag 로 구분
                    else if (eventType == XmlPullParser.TEXT)
                    {
                        if(bSet_headerCd)
                        {
                            headerCd = xpp.getText();
                            tv.append("headerCd : "+headerCd+"\n");
                            bSet_headerCd = false;
                        }
                        if(headerCd.equals("0"))
                        {
                            if(bSet_busRouteId)
                            {
                                busRouteId = xpp.getText();
                                tv.append("busRouteId : "+busRouteId+"\n");
                                bSet_busRouteId = false;
                            }
                            if(bSet_busRouteNm)
                            {
                                busRouteNm = xpp.getText();
                                tv.append(("busRouteNm : "+busRouteNm+"\n"));
                                bSet_busRouteNm = false;
                            }
                        }
                    }
                    // 끝나는 태그 : 아무 작업 X
                    else if (eventType == XmlPullParser.END_TAG){}
                    eventType = xpp.next();
                }
            }
            catch(Exception e)
            {
                tv.append(e.getMessage());
            }


        }

        private String downloadUrl(String myurl) throws IOException {
            HttpURLConnection conn = null;
            try
            {
                URL url = new URL(myurl);
                conn = (HttpURLConnection) url.openConnection();
                BufferedInputStream buf = new BufferedInputStream(conn.getInputStream());
                BufferedReader bufreader = new BufferedReader(new InputStreamReader(buf, "utf-8"));
                String line = null;
                String page = "";
                while((line = bufreader.readLine()) != null)
                {
                    page += line;
                }
                return page;
            }
            finally {
                conn.disconnect();
            }
        }
    }
}
