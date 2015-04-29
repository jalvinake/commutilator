package com.example.jake.commutilator;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.jake.commutilator.Vehicles.VehicleManager;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.PolylineOptions;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;


public class CurrentTrip extends FragmentActivity {
    public final static String CURRENT_TRIP_ID = "CurrentTrip.CurrentTripId";
    private Trip trip;
    private UUID tripId;
    private GoogleMap mMap;

    TextView startTime;
    TextView distance;
    TextView amountSaved;
    TextView durationText;
    TextView gallonsSaved;
    TripManager tripManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripManager = TripManager.getInstance();

        Intent intent = getIntent();
        tripId = UUID.fromString(intent.getStringExtra(CURRENT_TRIP_ID));
        trip = tripManager.getTrip(tripId);
        setContentView(R.layout.activity_current_trip);

        CurrentTripUpdater currentTripUpdater = new CurrentTripUpdater();
        tripManager.AddTripUpdateObserver(currentTripUpdater);

        //populateTripDetails(trip);
        //setupMap(trip);

    }

    private class CurrentTripUpdater implements TripUpdateObserver{

        @Override
        public void HandleNewPointAdded(Double totalMoneySaved, Double totalDistanceTravelled, Double totalGallonsSaved, LatLng newPoint, Trip updatedTrip) {
            populateTripDetails(updatedTrip);
            setupMap(trip);
        }
    }

    private void setupMap(Trip trip) {
        if (mMap == null) {
            mMap = ((SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.route_map)).getMap();
        }

        if (mMap != null) {

            List<LatLng> routePoints = trip.getRoutePoints();

            if (routePoints.size() > 0) {
                PolylineOptions polylineOptions = new PolylineOptions();
                polylineOptions
                        .addAll(routePoints)
                        .width(10)
                        .color(Color.BLUE)
                        .geodesic(true);
                mMap.addPolyline(polylineOptions);
                zoomToTrip(routePoints);
            }
        }
    }

    private void zoomToTrip(List<LatLng> routePoints) {
        LatLngBounds.Builder bc = new LatLngBounds.Builder();

        for (LatLng pnt : routePoints) {
            bc.include(pnt);
        }

        mMap.moveCamera(CameraUpdateFactory.newLatLngBounds(bc.build(),360,360,50));
    }

    public void populateTripDetails(Trip trip) {
        startTime = (TextView) findViewById(R.id.start_time_text);
        distance = (TextView) findViewById(R.id.distance_text);
        amountSaved = (TextView) findViewById(R.id.amount_saved_text);
        durationText = (TextView) findViewById(R.id.duration_text);
        gallonsSaved = (TextView) findViewById(R.id.gallons_saved_text);

        startTime.setText(new SimpleDateFormat("EEE MMM dd hh:mm a").format(trip.getStartTime()));
        distance.setText(new DecimalFormat("0.00 miles").format(trip.getDistance()));
        amountSaved.setText(new DecimalFormat("$0.00").format(trip.getAmountSaved()));
        Date now = new Date();
        Double duration = (now.getTime() - trip.getStartTime().getTime()) / 60000.;
        durationText.setText(new DecimalFormat("0.00 minutes").format(duration));
        gallonsSaved.setText(new DecimalFormat("0.0 gallons").format(trip.getGallonsSaved()));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_trip_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
