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
}
