package com.example.jake.commutilator;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jake.commutilator.Vehicles.Vehicle;
import com.example.jake.commutilator.Vehicles.VehicleManager;
import com.example.jake.commutilator.Vehicles.FuelPriceData;
import com.example.jake.commutilator.Vehicles.FuelPriceDataRetriever;
import com.google.android.gms.maps.model.LatLng;

import java.text.DecimalFormat;


public class CommutilatorHome extends ActionBarActivity {
    VehicleManager vehicleManager;
    TripManager tripManager;
    double currentFuelPrice;
    boolean currentFuelPriceWasLoadedFromCache;
    TextView totalDistanceTravelledTextView;
    TextView totalGallonsSavedTextView;
    TextView totalMoneySavedTextView;
    TextView currentFuelPriceTextView;
    Button configVehicle;
    Button startStopButton;
    Button tripButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commutilator_home);
        vehicleManager = VehicleManager.getInstance();
        vehicleManager.LoadVehicle(this);

        tripManager = TripManager.getInstance();
        tripManager.LoadTrips(getApplicationContext());

        currentFuelPriceTextView = (TextView) findViewById(R.id.current_fuel_price);
        totalDistanceTravelledTextView = (TextView) findViewById(R.id.total_distance_travelled);
        totalGallonsSavedTextView = (TextView) findViewById(R.id.total_gallons_saved);
        totalMoneySavedTextView = (TextView) findViewById(R.id.total_money_saved);

        TripMetricsTextViewUpdater tripMetricsUpdater = new TripMetricsTextViewUpdater();
        tripManager.AddTripUpdateObserver(tripMetricsUpdater);

        configVehicle = (Button) findViewById(R.id.configure_vehicle_button);
        configVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!tripManager.getTripIsActive()){
                    Intent vehicleConfigurationIntent  = new Intent(CommutilatorHome.this, VehicleConfiguration.class);
                    startActivity(vehicleConfigurationIntent);
                }
            }


        });



        startStopButton = (Button) findViewById(R.id.start_stop_button);
        tripButton = (Button) findViewById(R.id.trip_button);

        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tripManager.getTripIsActive() == true) {
                    tripManager.EndTrip();
                    tripManager.SaveTrips(getApplicationContext());
                } else {
                    tripManager.StartTrip(currentFuelPrice, vehicleManager.getCurrentVehicle());
                }

                updateTripStartStopButton();
                updateConfigVehicleButton();
            }
        });

        tripButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tripManager.getTripIsActive() == true) {
                    StartCurrentTripActivity();
                }
                else {
                    Intent tripHistoryIntent = new Intent(CommutilatorHome.this, TripHistory.class);
                    startActivity(tripHistoryIntent);
                }
            }
        });
    }


    private void updateConfigVehicleButton()
    {
        if(vehicleManager.getVehicleIsConfigured() == true) {
            Vehicle v = vehicleManager.getCurrentVehicle();
            configVehicle.setText(v.getMake() + " " + v.getModel() + " " + v.getYear());
        }
        else {
            configVehicle.setText("Configure Vehicle");
        }

        configVehicle.setEnabled(!tripManager.getTripIsActive());
    }

    private void updateTripStartStopButton()
    {
        if (tripManager.getTripIsActive() == false) {
            startStopButton.setText("START TRIP");
            startStopButton.getBackground().setColorFilter(0xFF00FF00, PorterDuff.Mode.MULTIPLY);
            tripButton.setText("Trip History");
        }
        else {
            startStopButton.setText("STOP TRIP");
            startStopButton.getBackground().setColorFilter(0xFFFF0000, PorterDuff.Mode.MULTIPLY);
            tripButton.setText("Current Trip");
        }
        startStopButton.setEnabled(vehicleManager.getVehicleIsConfigured());
    }

    private void updateFuelPriceText()
    {
        String appendedNote = "";

        if (currentFuelPriceWasLoadedFromCache) {
            appendedNote = " (cached)";
        }
        currentFuelPriceTextView.setText("$" + String.valueOf(currentFuelPrice) + appendedNote);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        tripManager.SaveTrips(getApplicationContext());
    }

    @Override
    protected void onResume() {
        super.onResume();

        if(vehicleManager.getVehicleIsConfigured() == true) {
            new FuelPriceDataUpdaterTask(vehicleManager.getCurrentVehicle()).execute();
        }

        setTripMetricTextViews(tripManager.getTotalMoneySaved(), tripManager.getTotalDistanceTravelled(), tripManager.getTotalGallonsSaved());

        updateConfigVehicleButton();
        updateTripStartStopButton();
        updateFuelPriceText();
    }

    private void setTripMetricTextViews(Double totalMoneySaved, Double totalDistanceTravelled, Double totalGallonsSaved){
        totalDistanceTravelledTextView.setText(new DecimalFormat("0.00 miles").format(totalDistanceTravelled));
        totalMoneySavedTextView.setText(new DecimalFormat("$0.00").format(totalMoneySaved));
        totalGallonsSavedTextView.setText(new DecimalFormat("0.00 gal").format(totalGallonsSaved));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commutilator_home, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem vehicleItem = menu.findItem(R.id.action_vehicle_configuration);
        //vehicleItem.setVisible(!tripManager.getTripIsActive()); -- let's just disable it for now so user knows about it
        vehicleItem.setEnabled(!tripManager.getTripIsActive());

        MenuItem currentTripItem = menu.findItem(R.id.action_current_trip);
        currentTripItem.setEnabled(tripManager.getTripIsActive());

        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_vehicle_configuration) {
            Intent vehicleConfigurationIntent = new Intent(CommutilatorHome.this, VehicleConfiguration.class);
            startActivity(vehicleConfigurationIntent);
        }

        if (id == R.id.action_trip_history) {
            Intent tripHistoryIntent = new Intent(CommutilatorHome.this, TripHistory.class);
            startActivity(tripHistoryIntent);
        }

        if (id == R.id.action_current_trip) {
            StartCurrentTripActivity();
        }


        return super.onOptionsItemSelected(item);
    }

    private void StartCurrentTripActivity(){
        Intent currentTripIntent = new Intent(CommutilatorHome.this, CurrentTrip.class);
        currentTripIntent.putExtra(CurrentTrip.CURRENT_TRIP_ID, tripManager.getCurrentTrip().getId().toString());
        startActivity(currentTripIntent);
    }

    private class TripMetricsTextViewUpdater implements TripUpdateObserver{

        @Override
        public void HandleNewPointAdded(Double totalMoneySaved, Double totalDistanceTravelled, Double totalGallonsSaved, LatLng newPoint, Trip updatedTrip) {
            setTripMetricTextViews(totalMoneySaved, totalDistanceTravelled, totalGallonsSaved);
        }
    }

    private class FuelPriceDataUpdaterTask extends AsyncTask<Void, Void, FuelPriceData> {
        private Vehicle vehicle;

        public FuelPriceDataUpdaterTask(Vehicle currentVehicle) {
            vehicle = currentVehicle;
        }

        @Override
        protected FuelPriceData doInBackground(Void... params) {
            try {
                FuelPriceDataRetriever retriever = new FuelPriceDataRetriever();
                //TODO: make decision on the price to return based on the vehicle
                return retriever.getFuelPriceData(getApplicationContext());
            } catch (Exception e) {
                Log.e("FuelPriceData", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(FuelPriceData fuelPriceData) {
            if (fuelPriceData != null) {
                currentFuelPrice = fuelPriceData.regular;
                currentFuelPriceWasLoadedFromCache = fuelPriceData.isFromCache;
                updateFuelPriceText();
            }
            else {
                throw new RuntimeException("Current FuelPriceData was not retrievable via Network or cache. Check data connection -- relaunch app then.");
            }
        }
    }
}