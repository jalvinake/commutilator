package com.example.jake.commutilator;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.jake.commutilator.Vehicles.MakeRetriever;
import com.example.jake.commutilator.Vehicles.ModelOptionRetriever;
import com.example.jake.commutilator.Vehicles.ModelRetriever;
import com.example.jake.commutilator.Vehicles.Vehicle;
import com.example.jake.commutilator.Vehicles.VehicleFuelData;
import com.example.jake.commutilator.Vehicles.VehicleFuelDataRetriever;
import com.example.jake.commutilator.Vehicles.VehicleMenuItem;
import com.example.jake.commutilator.Vehicles.VehicleMenuItemsRetriever;
import com.example.jake.commutilator.Vehicles.YearRetriever;

import java.util.ArrayList;


public class VehicleConfiguration extends ActionBarActivity {

    Vehicle vehicle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vehicle_configuration);

        vehicle = new Vehicle();


        final Spinner vehicleYearSpinner = (Spinner) findViewById(R.id.vehicle_year_selection);
        final Spinner vehicleMakeSpinner = (Spinner) findViewById(R.id.vehicle_make_selection);
        final Spinner vehicleModelSpinner = (Spinner) findViewById(R.id.vehicle_model_selection);
        final Spinner vehicleModelOptionSpinner = (Spinner) findViewById(R.id.vehicle_modeloption_selection);

        new VehiclePropertyUpdaterTask(vehicleYearSpinner, new YearRetriever(vehicle)).execute();

        vehicleYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VehicleMenuItem menuItem = (VehicleMenuItem)parent.getItemAtPosition(position);
                vehicle.setYear(menuItem.Value);
                new VehiclePropertyUpdaterTask(vehicleMakeSpinner, new MakeRetriever(vehicle)).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        vehicleMakeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VehicleMenuItem menuItem = (VehicleMenuItem)parent.getItemAtPosition(position);
                vehicle.setMake(menuItem.Value);
                new VehiclePropertyUpdaterTask(vehicleModelSpinner, new ModelRetriever(vehicle)).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vehicleModelSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VehicleMenuItem menuItem = (VehicleMenuItem)parent.getItemAtPosition(position);
                vehicle.setModel(menuItem.Value);
                new VehiclePropertyUpdaterTask(vehicleModelOptionSpinner, new ModelOptionRetriever(vehicle)).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        vehicleModelOptionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                VehicleMenuItem menuItem = (VehicleMenuItem)parent.getItemAtPosition(position);
                vehicle.setModelOption(menuItem.Text);
                vehicle.setVehicleId(menuItem.Value);

                new VehicleFuelDataUpdaterTask(vehicle).execute();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    private class VehiclePropertyUpdaterTask extends  AsyncTask<Void, Void, ArrayList<VehicleMenuItem>>{
        Spinner vehiclePropertySpinner;
        VehicleMenuItemsRetriever menuItemRetriever;
        public VehiclePropertyUpdaterTask(Spinner spinner, VehicleMenuItemsRetriever retriever){
            vehiclePropertySpinner = spinner;
            menuItemRetriever = retriever;
        }

        @Override
        protected ArrayList<VehicleMenuItem> doInBackground(Void... params) {
            try {
                return menuItemRetriever.GetMenuItems();
            } catch (Exception e) {
                Log.e("VehicleConfiguration", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(ArrayList<VehicleMenuItem> vehicleMenuItems) {
            ArrayAdapter<VehicleMenuItem> adapter = new ArrayAdapter<VehicleMenuItem>(getApplicationContext(),
                    R.layout.vehicle_spinner_item, vehicleMenuItems);
            vehiclePropertySpinner.setAdapter(adapter);
        }
    }

    private class VehicleFuelDataUpdaterTask extends  AsyncTask<Void, Void, VehicleFuelData>{
        Vehicle vehicle;

        public VehicleFuelDataUpdaterTask(Vehicle currentVehicle)
        {
            vehicle = currentVehicle;
        }

        @Override
        protected VehicleFuelData doInBackground(Void... params) {
            try {
                VehicleFuelDataRetriever retriever = new VehicleFuelDataRetriever();
                return retriever.GetVehicleFuelData(vehicle.getVehicleId());
            } catch (Exception e) {
                Log.e("VehicleFuelData", e.getMessage(), e);
            }

            return null;
        }

        @Override
        protected void onPostExecute(VehicleFuelData vehicleFuelData) {
            vehicle.setFuelData(vehicleFuelData);
        }
    }
}
