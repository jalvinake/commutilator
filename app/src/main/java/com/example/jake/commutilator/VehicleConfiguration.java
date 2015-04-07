package com.example.jake.commutilator;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.jake.commutilator.Vehicles.VehicleConfigurationService;

import java.util.ArrayList;


public class VehicleConfiguration extends ActionBarActivity {
    VehicleConfigurationService vehicleConfigurationService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_configuration);

        vehicleConfigurationService = VehicleConfigurationService.getInstance();

        new HttpRequestTask().execute();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_vehicle_configuration, menu);
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

    private class HttpRequestTask extends AsyncTask<Void, Void, ArrayList<String>> {
        @Override
        protected ArrayList<String> doInBackground(Void... params) {
            try {
                return vehicleConfigurationService.GetYears();
            } catch (Exception e) {
                Log.e("VehicleConfiguration", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<String> years) {

            Spinner vehicleYearSpinner = (Spinner) findViewById(R.id.vehicle_year_selection);

            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getApplicationContext(),
                    android.R.layout.simple_spinner_item, years);
            vehicleYearSpinner.setAdapter(adapter);
        }

    }
}
