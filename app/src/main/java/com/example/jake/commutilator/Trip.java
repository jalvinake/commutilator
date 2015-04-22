package com.example.jake.commutilator;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Trip {



    public static Trip[] getAll() {
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

        return new Trip[]{
                new Trip(new Date(2015, 4, 4, 8, 30, 0), new Date(2015, 4, 4, 8, 56, 0), testRoutePoints, 3.27234, 1.4),
                new Trip(new Date(2015, 4, 4, 10, 40, 0), new Date(2015, 4, 4, 11, 40, 0), testRoutePoints2, 30.34342, 3.45),
        };

    }

    public Trip(Date startTime, Date endTime, List<LatLng> routePoints, Double distance, Double amountSaved){
        this.startTime = startTime;
        this.endTime = endTime;
        this.routePoints = routePoints;
        this.distance = distance;
        this.amountSaved = amountSaved;
    }

    private Date startTime;
    private Date endTime;
    private List<LatLng> routePoints;
    private Double distance;
    private Double amountSaved;

    public Double getDistance() {
        return distance;
    }

    // TODO: modify to calculate distance from route
    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getAmountSaved() {
        return amountSaved;
    }

    // TODO: modify to calculate amount saved?  Or is this better done elsewhere?
    public void setAmountSaved(Double amountSaved) {
        this.amountSaved = amountSaved;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public List<LatLng> getRoutePoints() {
        return routePoints;
    }

    public void setRoutePoints(List<LatLng> routePoints) {
        this.routePoints = routePoints;
    }
}
