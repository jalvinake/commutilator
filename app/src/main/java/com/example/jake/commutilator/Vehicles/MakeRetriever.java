package com.example.jake.commutilator.Vehicles;

import java.util.ArrayList;

/**
 * Created by Jake on 4/14/2015.
 */
public class MakeRetriever extends  VehicleMenuItemsRetriever {
    public MakeRetriever(Vehicle vehicle) {
        super(vehicle);
    }

    @Override
    public ArrayList<VehicleMenuItem> GetMenuItems() {
        final String makeUrl = "http://www.fueleconomy.gov/ws/rest/vehicle/menu/make?year=";

        String makeUrlWithYear = makeUrl + _vehicle.getYear();

        return getMenuItemsFromAPI(makeUrlWithYear);
    }
}
