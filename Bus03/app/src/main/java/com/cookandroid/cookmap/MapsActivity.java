package com.cookandroid.cookmap;

import androidx.fragment.app.FragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.DoubleBuffer;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
//    Marker marker;
    Button btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        btnRefresh = findViewById(R.id.btnRefresh);

        btnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onMapReady(mMap);
            }
        });
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        // 여기에 asnctask~~~~~~~~~
        mMap = googleMap;


        // 1. Bus Route ID 구해오기
        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList";
        String serviceKey = "Pjc4C%2FUaOhA7X2mii0%2BIhYvanL8YGl72rVqMRQv%2Bfi9%2FrlAElIGa37aXuhdOQwbc%2BZRoSy8t5MdsHrOe6LKAtA%3D%3D";
//        serviceKey = URLEncoder.encode(serviceKey);
        String strSrch = "406";
        String strUrl = serviceUrl+"?ServiceKey="+serviceKey+"&strSrch="+strSrch;

        DownloadWebpageTask1 task1 = new DownloadWebpageTask1();
        task1.execute(strUrl);
    }

    private class DownloadWebpageTask1 extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... urls) {
//            Log.d("TASK1 들어왔슈.....","HERE");
            try
            {
                return downloadUrl(urls[0]);
            }
            catch(IOException e)
            {
                return "다운로드 실패";
            }
        }

        @Override
        protected void onPostExecute(String result) {
            String headerCd = "";
            String busRouteId = "";
            String busRouteNm = "";

            boolean bSet_headerCd = false;
            boolean bSet_busRouteId = false;
            boolean bSet_busRouteNm = false;

//            System.out.println("!!!!!!!!!여기!!!!!!!!");
//            System.out.println(result);

            ///// (1) Bus Route ID /////
            try {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                while (eventType != XmlPullParser.END_DOCUMENT)
                {
                    if(eventType == XmlPullParser.START_DOCUMENT) {}
                    else if(eventType == XmlPullParser.START_TAG)
                    {
                        String tag_name = xpp.getName();
                        if (tag_name.equals("headerCd"))
                            bSet_headerCd = true;
                        if (tag_name.equals("busRouteId"))
                            bSet_busRouteId = true;
                        if (tag_name.equals("busRouteNm"))
                            bSet_busRouteNm = true;
                    }
                    else if(eventType == XmlPullParser.TEXT)
                    {
                        if (bSet_headerCd)
                        {
                            headerCd = xpp.getText();
                            bSet_headerCd = false;
                        }
//                        Log.d("!!!! HeaderCODE : ", headerCd);
                        if (headerCd.equals("0"))
                        {
                            if (bSet_busRouteId) {
                                busRouteId = xpp.getText();
                                bSet_busRouteId = false;
                            }
                            if (bSet_busRouteNm) {
                                busRouteNm = xpp.getText();
                                bSet_busRouteNm = false;
                            }
                        }
                    }
                    else if(eventType == XmlPullParser.END_TAG) {}
                    eventType = xpp.next();
                }
            }
            catch (Exception e) {}

            // 2. Bus Route ID 를 이용하여 Bus Position 구해오기
            String serviceUrl = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid";
            String serviceKey = "Pjc4C%2FUaOhA7X2mii0%2BIhYvanL8YGl72rVqMRQv%2Bfi9%2FrlAElIGa37aXuhdOQwbc%2BZRoSy8t5MdsHrOe6LKAtA%3D%3D";
            String strUrl = serviceUrl+"?ServiceKey="+serviceKey+"&busRouteId="+busRouteId;

            DownloadWebpageTask2 task2 = new DownloadWebpageTask2();
            task2.execute(strUrl);
        }

        private String downloadUrl(String myurl) throws IOException{
            HttpURLConnection conn = null;
            try {
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
            } finally {
                conn.disconnect();
            }
        }
    }

    private class DownloadWebpageTask2 extends DownloadWebpageTask1 {

        @Override
        protected void onPostExecute(String result) {
//            Log.d("TASK2 들어왔슈...", "HERE");
            String headerCd = "";
            String plainNo = "";
            String gpsX = "";
            String gpsY = "";

//            System.out.println("!!!!!!!!!여기 TASK2 !!!!!!!!");
//            System.out.println(result);

            boolean bSet_headerCd = false;
            boolean bSet_gpsX = false;
            boolean bSet_gpsY = false;
            boolean bSet_plainNo = false;

            ///// (2) Bus Positions
            try
            {
                XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
                factory.setNamespaceAware(true);
                XmlPullParser xpp = factory.newPullParser();

                xpp.setInput(new StringReader(result));
                int eventType = xpp.getEventType();

                int count = 0;
                while (eventType != XmlPullParser.END_DOCUMENT) {
                    // 문서 시작 : 아무 작업 X
                    if(eventType == XmlPullParser.START_DOCUMENT) {}
                    // 원하는 tag 찾기
                    else if(eventType == XmlPullParser.START_TAG)
                    {
                        String tag_name = xpp.getName();
//                        Log.d("TASK2 HEADERCODE : ", headerCd);
                        if (tag_name.equals("headerCd"))
                            bSet_headerCd = true;
                        if (tag_name.equals("gpsX"))
                            bSet_gpsX = true;
                        if (tag_name.equals("gpsY"))
                            bSet_gpsY = true;
                        if (tag_name.equals("plainNo"))
                            bSet_plainNo = true;
                    }
                    else if(eventType == XmlPullParser.TEXT)
                    {
                        if (bSet_headerCd)
                        {
                            headerCd = xpp.getText();
                            bSet_headerCd = false;
                        }

                        if (headerCd.equals("0"))
                        {
                            if (bSet_gpsX)
                            {
                                count++;
                                gpsX = xpp.getText();
                                bSet_gpsX = false;
                            }
                            if (bSet_gpsY)
                            {
                                gpsY = xpp.getText();
                                bSet_gpsY = false;
                            }
                            if (bSet_plainNo)
                            {
                                plainNo = xpp.getText();
                                bSet_plainNo = false;
                                // 버스마다 지도에 마커 추가
                                displayBus(gpsX, gpsY, plainNo);
//                                Log.d("지도에 마커 추가!!!!!!!!", plainNo);
                            }
                        }
                    }
                    // 끝나는 태그 : 아무 작업 X
                    else if(eventType == XmlPullParser.END_TAG) {}
                    eventType = xpp.next();
                }
            } catch (Exception e) {
            }
        }

        private void displayBus(String gpsX, String gpsY, String plainNo){
            double latitude;
            double longitude;
            LatLng LOC;

            latitude = Double.parseDouble(gpsY);
            longitude = Double.parseDouble(gpsX);
            LOC = new LatLng(latitude, longitude);
            mMap.addMarker(new MarkerOptions().position(LOC).title(plainNo).icon(BitmapDescriptorFactory.fromResource(R.mipmap.bus)));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(LOC));
            mMap.moveCamera(CameraUpdateFactory.zoomTo(13));

        }
    }
}
