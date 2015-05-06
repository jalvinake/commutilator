package com.example.jake.commutilator;

import android.location.Location;
import android.location.LocationListener;
import android.os.Bundle;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Seth on 4/21/2015.
 */
public class TripLocationListener implements LocationListener {

    private TripManager myTripManager;
    private Location lastLocation;

    private static final int ONE_MINUTE = 1000 * 60;

    public TripLocationListener(TripManager tripManager) {
        myTripManager = tripManager;
    }

    @Override
    public void onLocationChanged(Location currLocation) {
        boolean canAddPoint = false;

        LatLng point;

        //If this is the start of a trip
        if (lastLocation == null) {
            lastLocation = currLocation;
            canAddPoint = true;
        }

        //If the accuracy of the current location is good enough to be sure
        //we have left the last location, add the point to the trip
        if (currLocation.distanceTo(lastLocation) > currLocation.getAccuracy()) {
            canAddPoint = true;
        }

        //If it has been more than a minute since the last update, add the point
        //to the trip, regardless of accuracy
        if (currLocation.getTime() - lastLocation.getTime() >= ONE_MINUTE){
            canAddPoint = true;
        }

        if (canAddPoint) {
            point = new LatLng(currLocation.getLatitude(), currLocation.getLongitude());
            lastLocation = currLocation;
        } else {
            point = new LatLng(lastLocation.getLatitude(), lastLocation.getLongitude());
        }

        myTripManager.AddNewPoint(point);
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
