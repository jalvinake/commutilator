package com.example.jake.commutilator;

import android.content.Intent;
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


public class CommutilatorHome extends ActionBarActivity {
    VehicleManager vehicleManager;
    TripManager tripManager;
    Double currentFuelPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commutilator_home);
        vehicleManager = VehicleManager.getInstance();
        vehicleManager.LoadVehicle(this);

        tripManager = TripManager.getInstance();
        tripManager.LoadTrips(getApplicationContext());

        final TextView currentFuelPriceTextView = (TextView) findViewById(R.id.current_fuel_price);

        final Button configVehicle = (Button) findViewById(R.id.configure_vehicle_button);
        configVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent vehicleConfigurationIntent  = new Intent(CommutilatorHome.this, VehicleConfiguration.class);
                startActivity(vehicleConfigurationIntent);
            }


        });

        if(vehicleManager.getVehicleIsConfigured() == true) {
            new FuelPriceDataUpdaterTask(vehicleManager.getCurrentVehicle(), currentFuelPriceTextView).execute();
            Vehicle v = vehicleManager.getCurrentVehicle();
            configVehicle.setText(v.getMake() + " " + v.getModel() + " " + v.getYear());
        }
        else {
            configVehicle.setText("Configure Vehicle");
        }

        final Button startStopButton = (Button) findViewById(R.id.start_stop_button);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (tripManager.getTripIsActive() == true) {
                    tripManager.EndTrip();
                    ((Button) v).setText("START");
                } else {
                    tripManager.StartTrip(currentFuelPrice, vehicleManager.getCurrentVehicle());
                    ((Button) v).setText("STOP");
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_commutilator_home, menu);
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
        if (id == R.id.action_route_map) {
            Intent routeMapIntent = new Intent(CommutilatorHome.this, RouteMap.class);
            startActivity(routeMapIntent);
        }
        if (id == R.id.action_trip_history) {
            Intent tripHistoryIntent = new Intent(CommutilatorHome.this, TripHistory.class);
            startActivity(tripHistoryIntent);
        }

        return super.onOptionsItemSelected(item);
    }


    private class FuelPriceDataUpdaterTask extends AsyncTask<Void, Void, FuelPriceData> {
        private TextView textView;
        private Vehicle vehicle;

        public FuelPriceDataUpdaterTask(Vehicle currentVehicle, TextView myTextbox) {
            textView = myTextbox;
            vehicle = currentVehicle;
        }

        @Override
        protected FuelPriceData doInBackground(Void... params) {
            try {
                FuelPriceDataRetriever retriever = new FuelPriceDataRetriever();
                //TODO: make decision on the price to return based on the vehicle
                return retriever.getFuelPriceData();
            } catch (Exception e) {
                Log.e("FuelPriceData", e.getMessage(), e);
            }
            return null;
        }

        @Override
        protected void onPostExecute(FuelPriceData fuelPriceData) {
            if (fuelPriceData != null) {
                textView.setText(fuelPriceData.regular.toString());
            }
        }
    }
}