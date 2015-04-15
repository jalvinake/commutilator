package com.example.jake.commutilator.Vehicles;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Jake on 4/3/2015.
 */
@Root(name="menuItem")
public class VehicleMenuItem {
    @Element(name = "text")
    public String Text;

    @Element(name = "value")
    public String Value;

    @Override
    public String toString() {
        return Text;
    }
}