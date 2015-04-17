package com.example.jake.commutilator.Vehicles;

import java.util.ArrayList;

/**
 * Created by Jake on 4/14/2015.
 */
public class YearRetriever extends VehicleMenuItemsRetriever {
    public YearRetriever(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public ArrayList<VehicleMenuItem> GetMenuItems() {
        final String yearUrl = "http://www.fueleconomy.gov/ws/rest/vehicle/menu/year";

        return getMenuItemsFromAPI(yearUrl);
    }

//    @Override
//    public VehicleMenuItem GetSelectedMenuItem(ArrayList<VehicleMenuItem> vehicleMenuItems) {
//        for(VehicleMenuItem vehicleMenuItem : vehicleMenuItems) {
//            if(vehicleMenuItem.Text == _vehicle.getYear()) {
//                return vehicleMenuItem;
//            }
//        }
//
//        return null;
//    }
}
