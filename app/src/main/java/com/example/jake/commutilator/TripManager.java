package com.example.jake.commutilator;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

    public Trip getCurrentTrip() {
        return currentTrip;
    }

    private Trip currentTrip;

    public Boolean getTripIsActive() {
        return tripIsActive;
    }

    private Boolean tripIsActive;

    public ArrayList<Trip> getTripHistory() {
        return tripHistory;
    }

    private ArrayList<Trip> tripHistory = new ArrayList<Trip>();

    public void StartTrip(){
        tripIsActive = true;
        currentTrip = new Trip();
        currentTrip.StartTrip();
    }

    public void EndTrip(){
        tripIsActive = false;
        currentTrip.EndTrip();
        tripHistory.add(currentTrip);
        currentTrip = null;
    }



    private void populateTestTrips() {
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


        tripHistory.add( new Trip(new Date(2015, 4, 4, 8, 30, 0), new Date(2015, 4, 4, 8, 56, 0), testRoutePoints, 3.27234, 1.4, 0.23));
        tripHistory.add(new Trip(new Date(2015, 4, 4, 10, 40, 0), new Date(2015, 4, 4, 11, 40, 0), testRoutePoints2, 30.34342, 3.45, 1.5));
    }

}
