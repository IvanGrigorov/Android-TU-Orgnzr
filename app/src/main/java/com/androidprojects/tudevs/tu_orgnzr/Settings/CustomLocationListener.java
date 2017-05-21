package com.androidprojects.tudevs.tu_orgnzr.Settings;

import android.Manifest;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;

import com.androidprojects.tudevs.tu_orgnzr.Config.Config;

import javax.inject.Inject;
import javax.inject.Named;

public class CustomLocationListener implements LocationListener {
    Criteria criteria;
    LocationManager locationManager;
    private double longitude;
    private double latitude;
    private Activity contextBinded;

    @Inject
    public CustomLocationListener(Criteria criteria, @Named("Location") LocationManager locationManager) {
        this.locationManager = locationManager;

    }

    public void setContext(Activity context) {
        this.contextBinded = context;
    }

    public void onLocationChanged(Location location) {
        longitude = location.getLongitude();
        latitude = location.getLatitude();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {
        registerLocationListeners(criteria);
    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void registerLocationListeners(Criteria criteria) {

        unregisterLocationListeners();

        String bestAvailableProvider = locationManager.getBestProvider(criteria, true);
        String bestProvider = locationManager.getBestProvider(criteria, false);

        if (bestAvailableProvider == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(this.contextBinded)
                    .setTitle("Location detection Problem")
                    .setMessage("There is a problem with the locationDetection. Please restart the application")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            return;
                        }
                    });
            alert.show();
        } else if (bestAvailableProvider.equals(bestProvider)) {

            // Ask for lacation permission if it is not granted
            if (ActivityCompat.checkSelfPermission(this.contextBinded, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this.contextBinded, Manifest.permission.ACCESS_COARSE_LOCATION)
                    != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                ActivityCompat.requestPermissions(this.contextBinded, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Config.LOCATION_PERMISSION);

            }

            locationManager.requestLocationUpdates(bestProvider, 200, 10, this);
        } else {
            // Ask for lacation permission if it is not granted
            if (ActivityCompat.checkSelfPermission(this.contextBinded, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED &&
                    ActivityCompat.checkSelfPermission(this.contextBinded, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling

                ActivityCompat.requestPermissions(this.contextBinded, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Config.LOCATION_PERMISSION);

            }

            locationManager.requestLocationUpdates(bestAvailableProvider, 200, 10, this);

        }
    }

    public void unregisterLocationListeners() {

        // Ask for lacation permission if it is not granted
        if (ActivityCompat.checkSelfPermission(this.contextBinded, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this.contextBinded, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling

            ActivityCompat.requestPermissions(this.contextBinded, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, Config.LOCATION_PERMISSION);

        }

        locationManager.removeUpdates(this);
    }


}
