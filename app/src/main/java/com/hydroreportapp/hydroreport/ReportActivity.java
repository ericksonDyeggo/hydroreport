package com.hydroreportapp.hydroreport;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.hydroreportapp.hydroreport.models.Report;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by erickson on 27/11/16.
 */
@EActivity(R.layout.activity_report)
public class ReportActivity extends AppCompatActivity {

    DatabaseReference mDatabase;

    LocationManager locationManager;

    @ViewById
    CheckBox chkBoxOdor;
    @ViewById
    CheckBox chkBoxCor;
    @ViewById
    CheckBox chkBoxVazamento;
    @ViewById
    CheckBox chkBoxGosto;
    @ViewById
    CheckBox chkBoxFalta;
    @ViewById
    EditText edtDescricao;
    @ViewById
    Button btnReportar;


    double cord_x = 0;
    double cord_y = 0;

    @AfterViews
    void afterViews() {
        if (!isLocationEnabled())
            showLocationAlert();
        initializeFirebase();
        initializeLocationManager();
        initializeLocationListener();
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
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
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
                Log.i("TaAqui", "Passou");
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

    private void initializeLocationManager() {
        locationManager = (LocationManager)
                getSystemService(Context.LOCATION_SERVICE);
    }

    private void initializeFirebase() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
    }

    @Click
    void btnReportar() {
        submitReport();
        clearData();
    }

    private void clearData() {
        edtDescricao.setText("");
        chkBoxOdor.setChecked(false);
        chkBoxCor.setChecked(false);
        chkBoxVazamento.setChecked(false);
        chkBoxGosto.setChecked(false);
        chkBoxFalta.setChecked(false);
    }

    private void submitReport() {

        String descricao = edtDescricao.getText().toString();

        Report report = new Report(cord_x, cord_y, descricao, chkBoxOdor.isChecked(),
                chkBoxCor.isChecked(), chkBoxVazamento.isChecked(), chkBoxGosto.isChecked()
                , chkBoxFalta.isChecked());

        mDatabase.push().setValue(report);
    }

    private void setCoordinates(Location location) {
        cord_x = location.getLatitude();
        cord_y = location.getLongitude();
    }
}
