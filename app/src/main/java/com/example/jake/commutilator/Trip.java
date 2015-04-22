package com.example.jake.commutilator;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polyline;
import com.google.android.gms.maps.model.PolylineOptions;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Trip {

    public Trip(Date startTime, Date endTime, List<LatLng> routePoints, Double distance, Double amountSaved, Double gallonsSaved){
        this.startTime = startTime;
        this.endTime = endTime;
        this.routePoints = routePoints;
        this.distance = distance;
        this.amountSaved = amountSaved;
        this.gallonsSaved = gallonsSaved;
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
    private List<LatLng> routePoints;
    private Double distance;
    private Double amountSaved;
    private Double gallonsSaved;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    private UUID id;

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

    public List<LatLng> getRoutePoints() { return routePoints; }

    public void addRoutePoint(LatLng point){
        routePoints.add(point);
    }

    public void setRoutePoints(List<LatLng> routePoints) {
        this.routePoints = routePoints;
    }

    public Double getGallonsSaved() { return gallonsSaved; }

    public void setGallonsSaved(Double gallonsSaved) {
        this.gallonsSaved = gallonsSaved;
    }
}
