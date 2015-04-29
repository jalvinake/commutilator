package com.example.jake.commutilator.Vehicles;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Jake on 4/14/2015.
 */
@Root(name = "vehicle", strict=false)
public class VehicleFuelData {
    @Element(name = "comb08U")
    private double CombinedMPGUnrounded;

    @Element(name = "city08U")
    private double CityMPGUnrounded;

    @Element(name = "highway08U")
    private double HighwayMPGUnrounded;

    @Element(name = "comb08")
    private double CombinedMPG;

    @Element(name = "city08")
    private double CityMPG;

    @Element(name = "highway08")
    private double HighwayMPG;

    public double getCombinedMPG() {
        //The unrounded value is not always populated. If it isn't,
        //use the other value and hope it is populated
        if(CombinedMPGUnrounded != 0) {
            return CombinedMPGUnrounded;
        } else {
            return CombinedMPG;
        }
    }

    public double getCityMPG() {
        if(CityMPGUnrounded != 0) {
            return CityMPGUnrounded;
        } else {
            return CityMPG;
        }
    }

    public double getHighwayMPG() {
        if(HighwayMPGUnrounded != 0) {
            return HighwayMPGUnrounded;
        } else {
            return HighwayMPG;
        }
    }
}
