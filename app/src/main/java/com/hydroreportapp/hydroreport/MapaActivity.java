package com.hydroreportapp.hydroreport;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

@EActivity(R.layout.activity_mapa2)
public class MapaActivity extends FragmentActivity implements OnMapReadyCallback {

    double cord_x = 0;
    double cord_y = 0;

    GoogleMap map;

    MarkerOptions myPosition;

    LocationManager locationManager;

    MapFragment mapFragment;
    LatLng latLng;

    @ViewById
    FloatingActionButton addReport;

    @AfterViews
    void afterViews() {

        initializeLocationManager();

        if (!isLocationEnabled())
            showLocationAlert();
        initializeLocationListener();

        mapFragment = (MapFragment) getFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Click
    void addReport() {
        Intent it = new Intent(getApplicationContext(), ReportActivity_.class);
        it.putExtra(Constants.COORD_X, cord_x);
        it.putExtra(Constants.COORD_Y, cord_y);
        startActivity(it);
    }

    private void initializeLocationManager() {
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
    }

    private boolean isLocationEnabled() {
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void showLocationAlert() {
        final AlertDialog.Builder dialog = new AlertDialog.Builder(this);
        dialog.setTitle(getText(R.string.gps_alert_title))
                .setMessage(getText(R.string.gps_alert_msg))
                .setPositiveButton(getText(R.string.gps_alert_positive_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                        Intent myIntent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                        startActivity(myIntent);
                    }
                })
                .setNegativeButton(getText(R.string.gps_alert_negative_button), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface paramDialogInterface, int paramInt) {
                    }
                });
        dialog.show();
    }

    private void initializeLocationListener() {

        LocationListener locationListener = new LocationListener() {
            public void onLocationChanged(Location location) {
                // Called when a new location is found by the network location provider.
                setCoordinates(location);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
            }

            public void onProviderEnabled(String provider) {
            }

            public void onProviderDisabled(String provider) {
            }
        };

        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 0, 0, locationListener);
    }


    @Override
    public void onMapReady(GoogleMap map) {
        this.map = map;
        latLng = new LatLng(cord_x, cord_y);
        myPosition = new MarkerOptions().position(latLng).title(getText(R.string.map_my_pin).toString());
        map.addMarker(myPosition);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.moveCamera(CameraUpdateFactory.zoomTo(19f));

    }

    private void updatePosition() {
        map.clear();
        myPosition = new MarkerOptions().position(latLng).title(getText(R.string.map_my_pin).toString());
        map.addMarker(myPosition);
        map.moveCamera(CameraUpdateFactory.newLatLng(latLng));
        map.moveCamera(CameraUpdateFactory.zoomTo(19f));
    }

    private void setCoordinates(Location location) {

        cord_x = location.getLatitude();
        cord_y = location.getLongitude();
        latLng = new LatLng(cord_x, cord_y);
        updatePosition();
    }
}
