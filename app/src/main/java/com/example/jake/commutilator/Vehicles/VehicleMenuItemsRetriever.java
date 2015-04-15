package com.example.jake.commutilator.Vehicles;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

/**
 * Created by Jake on 4/14/2015.
 */
public abstract class VehicleMenuItemsRetriever {
    Vehicle _vehicle;

    public VehicleMenuItemsRetriever(Vehicle vehicle)
    {
        _vehicle = vehicle;
    }

    public abstract ArrayList<VehicleMenuItem> GetMenuItems();

    protected ArrayList<VehicleMenuItem> getMenuItemsFromAPI(String url)
    {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

        MenuItemList response = restTemplate.getForObject(url, MenuItemList.class);

        return response.getMenuItems();
    }
}
