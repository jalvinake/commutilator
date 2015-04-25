package com.example.jake.commutilator;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by Jake on 4/24/2015.
 */
public interface TripUpdateObserver {
    void HandleNewPointAdded(Double totalMoneySaved, Double totalDistanceTravelled, Double totalGallonsSaved, LatLng newPoint, Trip updatedTrip);
}
