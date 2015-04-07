package com.example.jake.commutilator.Vehicles;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;


/**
 * Created by Jake on 4/3/2015.
 */
public class VehicleConfigurationService {
    private static VehicleConfigurationService instance = null;
    protected VehicleConfigurationService() {
        // Exists only to defeat instantiation.
    }
    public static VehicleConfigurationService getInstance() {
        if(instance == null) {
            instance = new VehicleConfigurationService();
        }
        return instance;
    }

    public ArrayList<String> GetYears()
    {
        final String url = "http://www.fueleconomy.gov/ws/rest/vehicle/menu/year";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

        MenuItemList response = restTemplate.getForObject(url, MenuItemList.class);

        ArrayList<String> years = new ArrayList<String>();

        for (menuItem item : response.getMenuItems()) {
            years.add(item.getText());
        }

        return years;
    }

}
