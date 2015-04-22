package com.example.jake.commutilator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.jake.commutilator.Vehicles.VehicleManager;
import com.example.jake.commutilator.Vehicles.FuelPriceData;
import com.example.jake.commutilator.Vehicles.FuelPriceDataRetriever;


public class CommutilatorHome extends ActionBarActivity {
    VehicleManager vehicleManager;
    TripManager tripManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commutilator_home);
        vehicleManager = VehicleManager.getInstance();
        vehicleManager.LoadVehicle(this);

        tripManager = TripManager.getInstance();
        tripManager.LoadTrips(getApplicationContext());

        //FuelPriceDataRetriever fuelDataRtv = new FuelPriceDataRetriever();
        //FuelPriceData fuelPricedt = fuelDataRtv.getFuelPriceData();

        final Button startStopButton = (Button) findViewById(R.id.start_stop_button);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tripManager.getTripIsActive() == true) {
                    tripManager.EndTrip();
                    ((Button)v).setText("START");
                }
                else
                {
                    tripManager.StartTrip();
                    ((Button)v).setText("STOP");
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
            Intent vehicleConfigurationIntent  = new Intent(CommutilatorHome.this, VehicleConfiguration.class);
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
}
