package com.example.jake.commutilator.Vehicles;

import android.content.Context;

import com.example.jake.commutilator.CommutilatorHome;
import com.example.jake.commutilator.Utilities.JSONSerializer;
import com.example.jake.commutilator.Utilities.NetworkTester;
import com.google.gson.reflect.TypeToken;

import org.springframework.http.converter.xml.SimpleXmlHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by etjric on 4/17/15.
 */
public class FuelPriceDataRetriever {
    private final String _fuel_price_cache_file_name = "fuel_price_cache";
    final String fuelPricesUrlStr = "http://www.fueleconomy.gov/ws/rest/fuelprices/";



    public FuelPriceData getFuelPriceData(Context ctx) {
        URL fuelPricesUrl;

        try {
            fuelPricesUrl = new URL(fuelPricesUrlStr);
        }
        catch (MalformedURLException ex) {
            throw new RuntimeException(ex);
        }

        NetworkTester networkTester =  new NetworkTester();

        FuelPriceData response = null;

        JSONSerializer jsonIzer = new JSONSerializer();

        if (networkTester.isURLReachable(ctx, fuelPricesUrl)) {
            RestTemplate restTemplate = new RestTemplate();
            restTemplate.getMessageConverters().add(new SimpleXmlHttpMessageConverter());

            response = restTemplate.getForObject(fuelPricesUrl.toString(), FuelPriceData.class);
            jsonIzer.Serialize(response, _fuel_price_cache_file_name, ctx);
        }
        else {
            //we assume caching is ok so we load from last cached result
            //if we are null here, the nothing was found in cache -- we handle nullality in the caller
            response = (FuelPriceData) jsonIzer.Deserialize(FuelPriceData.class, _fuel_price_cache_file_name, ctx);
            if (response != null) {
                response.isFromCache = true;
            }
        }

        return response;
    }
}
