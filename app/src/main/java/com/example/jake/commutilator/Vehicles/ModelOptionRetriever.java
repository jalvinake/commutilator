package com.example.jake.commutilator.Vehicles;

import java.util.ArrayList;

/**
 * Created by Jake on 4/14/2015.
 */
public class ModelOptionRetriever extends VehicleMenuItemsRetriever {
    public ModelOptionRetriever(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public ArrayList<VehicleMenuItem> GetMenuItems() {
        final String modelOptionUrl = "http://www.fueleconomy.gov/ws/rest/vehicle/menu/options?year=";

        String modelOptionUrlWithParameters = modelOptionUrl + _vehicle.getYear() + "&make=" + _vehicle.getMake() + "&model=" + _vehicle.getModel();

        return getMenuItemsFromAPI(modelOptionUrlWithParameters);
    }

//    @Override
//    public VehicleMenuItem GetSelectedMenuItem(ArrayList<VehicleMenuItem> vehicleMenuItems) {
//        for(VehicleMenuItem vehicleMenuItem : vehicleMenuItems) {
//            if(vehicleMenuItem.Text == _vehicle.getModelOption()) {
//                return vehicleMenuItem;
//            }
//        }
//
//        return null;
//    }
}
