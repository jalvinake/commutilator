package com.example.jake.commutilator;

import android.content.Context;
import android.location.LocationManager;

import com.example.jake.commutilator.Utilities.JSONSerializer;
import com.example.jake.commutilator.Vehicles.Vehicle;
import com.google.android.gms.maps.model.LatLng;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.lang.reflect.Type;

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

    private final String _trip_history_file_name = "trip_history";

    public Double getTotalDistanceTravelled() {
        return totalDistanceTravelled;
    }

    public void setTotalDistanceTravelled(Double totalDistanceTravelled) {
        this.totalDistanceTravelled = totalDistanceTravelled;
    }

    public Double getTotalGallonsSaved() {
        return totalGallonsSaved;
    }

    public void setTotalGallonsSaved(Double totalGallonsSaved) {
        this.totalGallonsSaved = totalGallonsSaved;
    }

    public Double getTotalMoneySaved() {
        return totalMoneySaved;
    }

    public void setTotalMoneySaved(Double totalMoneySaved) {
        this.totalMoneySaved = totalMoneySaved;
    }

    Double totalDistanceTravelled = 0.0;
    Double totalGallonsSaved = 0.0;
    Double totalMoneySaved = 0.0;

    Double totalDistanceTravelledForCompletedTrips = 0.0;
    Double totalGallonsSavedForCompletedTrips = 0.0;
    Double totalMoneySavedForCompletedTrips = 0.0;

    private void calculateTripMetricsForTripHistory() {
        totalDistanceTravelledForCompletedTrips = 0.0;
        totalGallonsSavedForCompletedTrips = 0.0;
        totalMoneySavedForCompletedTrips = 0.0;

        for(Trip trip : getTripHistory()) {
            updateTripMetricsWithNewTrip(trip);
        }
    }

    private void updateTripMetricsWithNewTrip(Trip trip){
        totalMoneySavedForCompletedTrips = totalMoneySavedForCompletedTrips + trip.getAmountSaved();
        totalDistanceTravelledForCompletedTrips = totalDistanceTravelledForCompletedTrips + trip.getDistance();
        totalGallonsSavedForCompletedTrips = totalGallonsSavedForCompletedTrips + trip.getGallonsSaved();

        totalMoneySaved = totalMoneySavedForCompletedTrips;
        totalDistanceTravelled = totalDistanceTravelledForCompletedTrips;
        totalGallonsSaved = totalGallonsSavedForCompletedTrips;
    }

    private void updateTripMetricsForActiveTrip(Trip trip){
        totalMoneySaved = totalMoneySavedForCompletedTrips + trip.getAmountSaved();
        totalDistanceTravelled = totalDistanceTravelledForCompletedTrips + trip.getDistance();
        totalGallonsSaved = totalGallonsSavedForCompletedTrips + trip.getGallonsSaved();
    }

    public Trip getTrip(UUID id) {
        if (currentTrip != null) {
            if(currentTrip.getId().equals(id)){
                return currentTrip;
            }
        }

        if (tripHistory.containsKey(id)) {
            return tripHistory.get(id);
        } else {
            return null;
        }
    }

    public void AddNewPoint(LatLng point){
        Trip currentTrip = getCurrentTrip();

        if(currentTrip != null){
            currentTrip.addRoutePoint(point);

            updateTripMetricsForActiveTrip(currentTrip);

            UpdateTripObservers(point);
        }

    }

    public void StartTrip(Double gasPrice, Vehicle vehicle){
        tripIsActive = true;
        currentTrip = new Trip();
        currentTrip.setGasPriceInDollars(gasPrice);
        currentTrip.setId(UUID.randomUUID());
        currentTrip.setVehicleId(vehicle.getVehicleId());
        currentTrip.setMilesPerGallon(vehicle.getFuelData().CombinedMPG);
        currentTrip.StartTrip();

        startLocationUpdates();
    }

    private void startLocationUpdates() {
        tripLocationListener = new TripLocationListener(this);
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, tripLocationListener);
    }

    private void stopLocationUpdates() {
        locationManager.removeUpdates(tripLocationListener);
    }

    public void EndTrip(){
        tripIsActive = false;
        stopLocationUpdates();
        currentTrip.EndTrip();
        updateTripMetricsWithNewTrip(currentTrip);
        tripHistory.put(currentTrip.getId(), currentTrip);
        currentTrip = null;

    }

    public void LoadTrips(Context context){
        locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        calculateTripMetricsForTripHistory();
    }

    private void populateTestTrips() {
        tripHistory = new HashMap<UUID, Trip>();

        Trip trip1 = new Trip(new Date(2015, 4, 4, 8, 30, 0), new Date(2015, 4, 4, 8, 56, 0), 3.12, 18.3);
        Trip trip2 = new Trip(new Date(2015, 4, 4, 10, 40, 0), new Date(2015, 4, 4, 11, 40, 0), 2.56, 22.1);

        trip1.addRoutePoint(new LatLng(44.974144, -93.232254));
        trip1.addRoutePoint(new LatLng(44.973593, -93.232157));
        trip1.addRoutePoint(new LatLng(44.973552, -93.223907));
        trip1.addRoutePoint(new LatLng(44.967267, -93.222528));
        trip1.addRoutePoint(new LatLng(44.956757, -93.192460));
        trip1.addRoutePoint(new LatLng(44.953279, -93.192439));
        trip1.addRoutePoint(new LatLng(44.952976, -93.19117));

        trip2.addRoutePoint(new LatLng(44.974144, -93.232254));
        trip2.addRoutePoint(new LatLng(44.973593, -93.232157));
        trip2.addRoutePoint(new LatLng(44.973552, -93.223907));
        trip2.addRoutePoint(new LatLng(44.967267, -93.222528));
        trip2.addRoutePoint(new LatLng(44.965077, -93.287754));
        trip2.addRoutePoint(new LatLng(44.975157, -93.288011));
        trip2.addRoutePoint(new LatLng(44.970603, -93.340969));
        trip2.addRoutePoint(new LatLng(44.963832, -93.343930));
        trip2.addRoutePoint(new LatLng(44.963802, -93.360968));
        trip2.addRoutePoint(new LatLng(44.966140, -93.362169));

        trip1.setId(UUID.randomUUID());
        trip2.setId(UUID.randomUUID());

        tripHistory.put(trip1.getId(),trip1);
        tripHistory.put(trip2.getId(),trip2);
    }

    List<TripUpdateObserver> tripUpdateObservers = new ArrayList<TripUpdateObserver>();

    public void AddTripUpdateObserver(TripUpdateObserver tripUpdateObserver){
        tripUpdateObservers.add(tripUpdateObserver);
    }

    public void RemoveTripUpdateObserver(TripUpdateObserver tripUpdateObserver){
        tripUpdateObservers.remove(tripUpdateObserver);
    }

    public void UpdateTripObservers(LatLng newPoint){
        for(TripUpdateObserver tripUpdateObserver : tripUpdateObservers){
            tripUpdateObserver.HandleNewPointAdded(getTotalMoneySaved(), getTotalDistanceTravelled(), getTotalGallonsSaved(), newPoint, getCurrentTrip());
        }
    }

    public void SaveTripHistoryToFile(Context context) {
        JSONSerializer jsonIzer = new JSONSerializer();
        jsonIzer.Serialize(tripHistory, _trip_history_file_name, context);
    }

    public void LoadTripHistoryFromFile(Context context) {
        JSONSerializer jsonIzer = new JSONSerializer();
        Type tripHistoryDictionaryType = new TypeToken<Map<UUID, Trip>>() {}.getType();
        //tripHistory = jsonIzer.Deserialize(tripHistoryDictionaryType, _trip_history_file_name, context);
    }
}
