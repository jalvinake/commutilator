package com.example.jake.commutilator.Utilities;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.IBinder;

/**
 * Created by Seth on 4/19/2015.
 */
public class LocationTracker extends Service implements LocationListener{

    private final Context _context;
    private boolean isGPSEnabled = false;
    private boolean isNetworkEnabled = false;
    private boolean canGetLocation = false;

    Location location;

    protected LocationManager _locManager;

    public LocationTracker(Context context) {
        this._context = context;
        getLocation();
    }

    public Location getLocation() {  //Maybe should be called initTracker?

        String bestProvider;
        LocationCriteria myCrit = new LocationCriteria();

        try {
            _locManager = (LocationManager) _context.getSystemService(LOCATION_SERVICE);

            bestProvider = _locManager.getBestProvider(myCrit, true);

            _locManager.requestLocationUpdates(bestProvider, 6000, 10, this);

            location = _locManager.getLastKnownLocation(bestProvider);

            //Make sure we aren't passing back a null object
            if (location == null) {
                location = new Location(LocationManager.GPS_PROVIDER);
            }

        }
        catch (Exception e) {
            e.printStackTrace();
        }

        return location;
    }


    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {
        //Do nothing
    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
