package com.example.jake.commutilator;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.UUID;


public class TripDetail extends Activity {
    public final static String CURRENT_TRIP_ID = "TripDetail.CurrentTripId";
    private TripManager tripManager;
    private Trip trip;
    private UUID tripId;

    TextView startTime;
    TextView distance;
    TextView amountSaved;
    TextView duration;
    TextView gallonsSaved;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        tripManager = TripManager.getInstance();
        Intent intent = getIntent();
        tripId = UUID.fromString(intent.getStringExtra(CURRENT_TRIP_ID));
        setContentView(R.layout.activity_trip_detail);
        populateTripDetails(tripId);
    }

    public void populateTripDetails(UUID tripId) {
        trip = tripManager.getTrip(tripId);
        startTime = (TextView) findViewById(R.id.start_time_text);
        distance = (TextView) findViewById(R.id.distance_text);
        amountSaved = (TextView) findViewById(R.id.amount_saved_text);
        duration = (TextView) findViewById(R.id.duration_text);
        gallonsSaved = (TextView) findViewById(R.id.gallons_saved_text);

        startTime.setText(new SimpleDateFormat("EEE MMM dd hh:mm a").format(trip.getStartTime()));
        distance.setText(new DecimalFormat("0.00 miles").format(trip.getDistance()));
        amountSaved.setText(new DecimalFormat("$0.00").format(trip.getAmountSaved()));
        //duration.setText(trip.getDuration);
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
