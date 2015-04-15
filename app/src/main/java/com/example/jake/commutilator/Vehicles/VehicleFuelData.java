package com.example.jake.commutilator.Vehicles;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Jake on 4/14/2015.
 */
@Root(name = "vehicle", strict=false)
public class VehicleFuelData {
    @Element(name = "comb08U")
    public Double CombinedMPG;

    @Element(name = "city08U")
    public Double CityMPG;

    @Element(name = "highway08U")
    public Double HighwayMPG;
}
