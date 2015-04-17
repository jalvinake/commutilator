package com.example.jake.commutilator;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.jake.commutilator.Vehicles.VehicleManager;
import com.example.jake.commutilator.Vehicles.FuelPriceData;
import com.example.jake.commutilator.Vehicles.FuelPriceDataRetriever;


public class CommutilatorHome extends ActionBarActivity {
    VehicleManager vehicleManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_commutilator_home);
        vehicleManager = VehicleManager.getInstance();
        vehicleManager.LoadVehicle(this);

        FuelPriceDataRetriever fuelDataRtv = new FuelPriceDataRetriever();
        FuelPriceData fuelPricedt = fuelDataRtv.getFuelPriceData();
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

        return super.onOptionsItemSelected(item);
    }
}
