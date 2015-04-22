package com.example.jake.commutilator;

import android.content.Context;
import android.location.LocationManager;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by Jake on 4/21/2015.
 */
public class TripManager {
    private static TripManager ourInstance = new TripManager();

    public static TripManager getInstance() {
        return ourInstance;
    }

    private TripManager() {
        populateTestTrips();
    }

    LocationManager locationManager;
    TripLocationListener tripLocationListener;

    public Trip getCurrentTrip() {
        return currentTrip;
    }

    private Trip currentTrip;

    public Boolean getTripIsActive() {
        return tripIsActive;
    }

    private Boolean tripIsActive = false;

    public ArrayList<Trip> getTripHistory() {
        return new ArrayList<Trip>(tripHistory.values());
    }

    Map<UUID, Trip> tripHistory;

    public Trip getTrip(UUID id) {
        if(currentTrip.getId().equals(id)){
            return currentTrip;
        }

        if (tripHistory.containsKey(id)) {
            return tripHistory.get(id);
        } else {
            return null;
        }
    }


    public void StartTrip(){
        tripIsActive = true;
        currentTrip = new Trip();
        currentTrip.setId(UUID.randomUUID());
        currentTrip.StartTrip();

      startLocationUpdates();
    }

    private void startLocationUpdates() {
        tripLocationListener = new TripLocationListener(getCurrentTrip());
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 20, tripLocationListener);
    }

    private void stopLocationUpdates() {
        locationManager.removeUpdates(tripLocationListener);
    }

    public void EndTrip(){
        tripIsActive = false;
        currentTrip.EndTrip();
        tripHistory.put(currentTrip.getId(), currentTrip);
        currentTrip = null;
    }

    public void LoadTrips(Context context){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
    }

    private void populateTestTrips() {
        tripHistory = new HashMap<UUID, Trip>();
        List<LatLng> testRoutePoints = new ArrayList<LatLng>();
        testRoutePoints.add(new LatLng(37.35, -122.0));
        testRoutePoints.add(new LatLng(37.45, -122.0));
        testRoutePoints.add(new LatLng(37.45, -122.1));
        testRoutePoints.add(new LatLng(37.35, -122.1));
        testRoutePoints.add(new LatLng(37.35, -122.2));

        List<LatLng> testRoutePoints2 = new ArrayList<LatLng>();
        testRoutePoints2.add(new LatLng(37.35, -122.0));
        testRoutePoints2.add(new LatLng(37.45, -122.0));
        testRoutePoints2.add(new LatLng(37.45, -122.1));
        testRoutePoints2.add(new LatLng(37.35, -122.1));
        testRoutePoints2.add(new LatLng(37.35, -122.2));


        Trip trip1 = new Trip(new Date(2015, 4, 4, 8, 30, 0), new Date(2015, 4, 4, 8, 56, 0), testRoutePoints, 3.27234, 1.4, 0.23);
        Trip trip2 = new Trip(new Date(2015, 4, 4, 10, 40, 0), new Date(2015, 4, 4, 11, 40, 0), testRoutePoints2, 30.34342, 3.45, 1.5);

        trip1.setId(UUID.randomUUID());
        trip2.setId(UUID.randomUUID());

        tripHistory.put(trip1.getId(),trip1);
        tripHistory.put(trip2.getId(),trip2);
    }

}
