package com.example.jake.commutilator.Vehicles;

import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;

/**
 * Created by Jake on 4/6/2015.
 */
@Root(name="menuItems")
public class MenuItemList {
    public ArrayList<menuItem> getMenuItems() {
        return menuItems;
    }

    public void setMenuItems(ArrayList<menuItem> menuItems) {
        this.menuItems = menuItems;
    }

    @ElementList(inline=true)
    private ArrayList<menuItem> menuItems;
}
