package com.example.jake.commutilator.Vehicles;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by Jake on 4/14/2015.
 */
public class VehicleFuelDataRetriever {
    public VehicleFuelData GetVehicleFuelData(String vehicleId)
    {
        final String vehicleUrl = "http://www.fueleconomy.gov/ws/rest/vehicle/";

        String url = vehicleUrl + vehicleId;
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

        VehicleFuelData response = restTemplate.getForObject(url, VehicleFuelData.class);

        return response;
    }
}
