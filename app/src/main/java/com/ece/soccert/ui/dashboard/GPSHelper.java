package com.ece.soccert.ui.dashboard;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import static android.content.Context.LOCATION_SERVICE;

public class GPSHelper implements LocationListener {

    private static final String TAG = "GPSHelper";


    public Location getCurrentLocation(Context mContext){
        int MIN_TIME_BW_UPDATES = 1000;
        int MIN_DISTANCE_CHANGE_FOR_UPDATES = 5;
        Location loc = null;
        Double latitude, longitude;
        Log.d("MAAAAP", "getCurrentLocation OK");
        LocationManager locationManager = (LocationManager) mContext
                .getSystemService(LOCATION_SERVICE);

        // getting GPS status
        Boolean checkGPS = locationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // getting network status
        Boolean checkNetwork = locationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!checkGPS && !checkNetwork) {
            Log.d("MAAAAP", "No Service Provider Available");
            Toast.makeText(mContext, "No Service Provider Available", Toast.LENGTH_SHORT).show();
        } else {
            Log.d("MAAAAP", "Service Provider Available");
            //this.canGetLocation = true;
            // First get location from Network Provider
            if (checkNetwork) {
                Log.d("MAAAAP", "Network");
                Toast.makeText(mContext, "Network", Toast.LENGTH_SHORT).show();
                try {
                    locationManager.requestLocationUpdates(
                            LocationManager.NETWORK_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("Network", "Network");
                    if (locationManager != null) {
                        loc = locationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    }

                    if (loc != null) {
                        Log.d(TAG, "getCurrentLocation: " + loc.getLatitude() + ", " + loc.getLongitude());
                        return loc;
                    }
                } catch (SecurityException e) {
                    Log.d("MAAAAP", "ERR Network");
                }
            }
        }
        // if GPS Enabled get lat/long using GPS Services
        if (checkGPS) {
            Toast.makeText(mContext, "GPS", Toast.LENGTH_SHORT).show();
            if (loc == null) {
                try {
                    locationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (locationManager != null) {
                        loc = locationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (loc != null) {
                            latitude = loc.getLatitude();
                            longitude = loc.getLongitude();
                        }
                    }
                } catch (SecurityException e) {
                    Log.d("MAAAAP", "ERR GPS");
                }
            }
        }
        Location locErr = null;
        return loc;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}