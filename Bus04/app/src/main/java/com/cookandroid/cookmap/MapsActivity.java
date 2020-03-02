package com.cookandroid.cookmap;

import androidx.fragment.app.FragmentActivity;

import android.os.AsyncTask;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        String serviceUrl = "http://ws.bus.go.kr/api/rest/busRouteInfo/getBusRouteList";
        String serviceKey = "Pjc4C%2FUaOhA7X2mii0%2BIhYvanL8YGl72rVqMRQv%2Bfi9%2FrlAElIGa37aXuhdOQwbc%2BZRoSy8t5MdsHrOe6LKAtA%3D%3D";
        String strSrch = "5714";
        String strUrl = serviceUrl+"?ServiceKey="+serviceKey+"&strSrch="+strSrch;
        DownloadWebpageTask1 task1 = new DownloadWebpageTask1();
        task1.execute(strUrl);
    }

    private class DownloadWebpageTask1 extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            // 1. BusRouteId 구해서 onPostExecute로
            String busRouteId="";
            try
            {
                Document doc = Jsoup.connect(urls[0]).get();
                Elements headerCd_elements = doc.select("busRouteId");
                for (Element item : headerCd_elements)
                {
                    busRouteId =item.text().trim();
                }
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }

            return busRouteId;
        }

        @Override
        protected void onPostExecute(String busRouteId) {
            // 2. Bus Route ID 를 이용하여 Bus Position 구해오기
            String serviceUrl = "http://ws.bus.go.kr/api/rest/buspos/getBusPosByRtid";
            String serviceKey = "Pjc4C%2FUaOhA7X2mii0%2BIhYvanL8YGl72rVqMRQv%2Bfi9%2FrlAElIGa37aXuhdOQwbc%2BZRoSy8t5MdsHrOe6LKAtA%3D%3D";
            String strUrl = serviceUrl+"?ServiceKey="+serviceKey+"&busRouteId="+busRouteId;
            new DownloadWebpageTask2().execute(strUrl);
        }
        private class DownloadWebpageTask2 extends AsyncTask<String,Void,String> {

            Document doc;
            Elements gpsX_elements;
            Elements gpsY_elements;
            Elements plainNo_elements;

            private void displayBus(String gpsX, String gpsY, String plainNo) {

                double latitude;
                double longitude;
                LatLng LOC;

                latitude = Double.parseDouble(gpsY);
                longitude = Double.parseDouble(gpsX);
                LOC = new LatLng(latitude, longitude);
                Marker mk1 = mMap.addMarker(new MarkerOptions()
                        .position(LOC)
                        .title(plainNo)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.bus)));
                mMap.moveCamera(CameraUpdateFactory.newLatLng(LOC));
                mMap.moveCamera(CameraUpdateFactory.zoomTo(12));

            }

            //주요 내용 실행
            @Override
            protected String doInBackground(String... urls) {
                String busRouteId="";
                try {
                    doc = Jsoup.connect(urls[0]).get();
                    gpsX_elements = doc.select("gpsX");
                    gpsY_elements = doc.select("gpsY");
                    plainNo_elements = doc.select("plainNo");
                } catch (IOException e) {
                    e.printStackTrace();
                }

                return null;
            }


            protected void onPostExecute(String result) {

                for (int i=0;i<gpsX_elements.size();i++) {
                    String gpsX = gpsX_elements.get(i).text().trim(); // gpsX
                    String gpsY = gpsY_elements.get(i).text().trim(); //gpsY
                    String plainNo = plainNo_elements.get(i).text().trim(); //plainNo

                    displayBus(gpsX, gpsY, plainNo);
                }
            }
        }

    }
}
