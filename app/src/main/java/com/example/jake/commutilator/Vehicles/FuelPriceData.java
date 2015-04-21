package com.example.jake.commutilator.Vehicles;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by etjric on 4/17/15.
 */
@Root(name = "fuelPrices", strict=false)
    public class FuelPriceData {
    @Element(name = "cng")
    public Double cng;

    @Element(name = "diesel")
    public Double diesel;

    @Element(name = "e85")
    public Double e85;

    @Element(name = "electric")
    public Double electric;

    @Element(name = "lpg")
    public Double lpg;

    @Element(name = "midgrade")
    public Double midgrade;

    @Element(name = "premium")
    public Double premium;

    @Element(name = "regular")
    public Double regular;
}
