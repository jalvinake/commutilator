package com.example.jake.commutilator.Vehicles;

import java.util.ArrayList;

/**
 * Created by Jake on 4/14/2015.
 */
public class ModelRetriever extends  VehicleMenuItemsRetriever {
    public ModelRetriever(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public ArrayList<VehicleMenuItem> GetMenuItems() {
        final String modelUrl = "http://www.fueleconomy.gov/ws/rest/vehicle/menu/model?year=";

        String modelUrlWithParameters = modelUrl + _vehicle.getYear() + "&make=" + _vehicle.getMake();

        return getMenuItemsFromAPI(modelUrlWithParameters);
    }
}
