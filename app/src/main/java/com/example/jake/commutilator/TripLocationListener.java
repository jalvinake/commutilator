package com.example.jake.commutilator;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Seth on 4/21/2015.
 */
public class TripLocationListener implements LocationListener {

    private Trip myTrip;

    public TripLocationListener(Trip trip) {
        myTrip = trip;
    }

    @Override
    public void onLocationChanged(Location location) {
        LatLng point = new LatLng(location.getLatitude(), location.getLongitude());

        myTrip.addRoutePoint(point);
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