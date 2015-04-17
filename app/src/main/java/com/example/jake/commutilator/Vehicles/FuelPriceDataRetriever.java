package com.example.jake.commutilator.Vehicles;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * Created by etjric on 4/17/15.
 */
public class FuelPriceDataRetriever {

    public FuelPriceData getFuelPriceData() {
        final String fuelPricesUrl = "http://www.fueleconomy.gov/ws/rest/fuelprices/";

        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

        FuelPriceData response = restTemplate.getForObject(fuelPricesUrl, FuelPriceData.class);

        return response;
    }
}
