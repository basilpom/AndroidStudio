package com.cookandroid.cookmap;

import androidx.fragment.app.FragmentActivity;

import android.graphics.Camera;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.GroundOverlayOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    private GroundOverlayOptions marker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
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
        mMap = googleMap;
        LatLng tis = new LatLng(37.534456, 126.898538);
        LatLng cgvGN = new LatLng(37.501607, 127.026332);
        LatLng cgvAGJ = new LatLng(37.524498, 127.028849);
        LatLng cgvCD = new LatLng(37.522868, 127.037037);
        mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);

        // Marker 이미지 변경
//        marker = new GroundOverlayOptions().image(BitmapDescriptorFactory.fromResource(R.mipmap.marker)).position(tis, 20f, 20f);
//        MarkerOptions marker = new MarkerOptions();
//        marker.title("Here is TIS!");
//        marker.icon(BitmapDescriptorFactory.fromResource(R.drawable.marker));
//        mMap.addMarker(marker);
//        mMap.addMarker(new MarkerOptions().position(tis).title("TIS!").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
//        mMap.addMarker(new MarkerOptions().position(tis).title("Marker in TIS!"));
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(tis));

        // 지도의 중심 : moveCamera
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(cgvGN, 13));
        // marker 3개 표시
        mMap.addMarker(new MarkerOptions().position(cgvGN).title("CGV Gangnam").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
        mMap.addMarker(new MarkerOptions().position(cgvAGJ).title("CGV Apgujeong").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
        mMap.addMarker(new MarkerOptions().position(cgvCD).title("CGV Cheongdam").icon(BitmapDescriptorFactory.fromResource(R.mipmap.marker)));
    }
}
