package com.hydroreportapp.hydroreport;

import android.content.Intent;
import android.location.Location;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mapa2);

        MapFragment mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        FloatingActionButton addReport = (FloatingActionButton) findViewById(R.id.addReport);
        addReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                setContentView(R.layout.activity_report);


                //Intent it = new Intent(this, ReportActivity.class);
                //startActivity(it);
            }
        });

    }

    public void callReport(){
        setContentView(R.layout.activity_report);

    }


    @Override
    public void onMapReady(GoogleMap map) {


        LatLng sydney =  new LatLng(-23.597786, -46.682202);
        map.addMarker(new MarkerOptions().position(sydney).title("Your location"));
        map.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        map.moveCamera(CameraUpdateFactory.zoomTo(19f));



    /*    map.addMarker(new MarkerOptions()
                .position(new LatLng(0, 0))
                .title("Marker"));
        map.setMapType(GoogleMap.MAP_TYPE_NORMAL); */
    }
}
