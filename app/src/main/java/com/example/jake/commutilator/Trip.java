package com.example.jake.commutilator;

import android.location.Location;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Trip {

    public Trip(Date startTime, Date endTime, Double gasPriceInDollars, Double milesPerGallon){
        this.startTime = startTime;
        this.endTime = endTime;

        this.gasPriceInDollars = gasPriceInDollars;
        this.milesPerGallon = milesPerGallon;
    }

    public Trip(){
    }

    public void StartTrip(){
        setStartTime(new Date());
    }

    public void EndTrip(){
        setEndTime(new Date());
    }

    private Date startTime;
    private Date endTime;
    private List<LatLng> routePoints = new ArrayList<LatLng>();
    private Double distance = 0.0;
    private Double amountSaved = 0.0;
    private Double gallonsSaved = 0.0;
    private Double gasPriceInDollars;
    private Double milesPerGallon;
    private String vehicleId;
    private UUID id;

    public Double getGasPriceInDollars() {
        return gasPriceInDollars;
    }

    public void setGasPriceInDollars(Double gasPriceInDollars) {
        this.gasPriceInDollars = gasPriceInDollars;
    }

    public Double getMilesPerGallon() {
        return milesPerGallon;
    }

    public void setMilesPerGallon(Double milesPerGallon) {
        this.milesPerGallon = milesPerGallon;
    }


    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }


    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
        this.distance = distance;
    }

    public Double getAmountSaved() {
        return amountSaved;
    }

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

    public List<LatLng> getRoutePoints() { return routePoints; }

    public void addRoutePoint(LatLng point){
        updateDistanceWithNewPoint(point);
        routePoints.add(point);

        if(distance > 0.0) {
            setGallonsSaved(calculateGallonsSaved(getDistance()));
            setAmountSaved(calculateAmountSaved(getGallonsSaved()));
        }
    }

    private void updateDistanceWithNewPoint(LatLng newPoint)
    {
        //this method assumes that it is called BEFORE the point added to the local collection

        //get the previous point first if there is one
        int numberOfPoints = routePoints.size();
        if(numberOfPoints > 0) {
            LatLng previous = routePoints.get(numberOfPoints - 1);
            float[] results = new float[3];
            Location.distanceBetween(newPoint.latitude, newPoint.longitude, previous.latitude, previous.longitude, results );

            //add distance between new point and previous point to the total distance
            //distance is in first location
            //convert to miles by multiplying by 0.000621371
            setDistance(getDistance() + results[0] * 0.000621371);
        }
    }

    private Double calculateGallonsSaved(Double distance){
        return distance / getMilesPerGallon();
    }

    private Double calculateAmountSaved(Double gallonsSaved){
        return gallonsSaved * getGasPriceInDollars() ;
    }

    public void setRoutePoints(List<LatLng> routePoints) {
        this.routePoints = routePoints;
    }

    public Double getGallonsSaved() { return gallonsSaved; }

    public void setGallonsSaved(Double gallonsSaved) {
        this.gallonsSaved = gallonsSaved;
    }
}
