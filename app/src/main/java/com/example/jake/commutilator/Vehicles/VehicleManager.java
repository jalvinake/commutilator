package com.example.jake.commutilator.Vehicles;

import android.content.Context;

import com.example.jake.commutilator.Utilities.JSONSerializer;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;

/**
 * Created by Jake on 4/17/2015.
 */
public class VehicleManager {
    private final String vehicleFileName = "vehicle_storage";
    private static VehicleManager ourInstance = new VehicleManager();

    public static VehicleManager getInstance() {
        return ourInstance;
    }

    private VehicleManager() {

    }

    public Vehicle getCurrentVehicle() {
        return currentVehicle;
    }

    public void setCurrentVehicle(Vehicle currentVehicle) {
        this.currentVehicle = currentVehicle;
    }

    private Vehicle currentVehicle;

    public Boolean getVehicleIsConfigured()
    {
        return getCurrentVehicle() != null;
    }

    public void SaveVehicle(Context context)
    {
        new JSONSerializer().Serialize(getCurrentVehicle(), vehicleFileName, context);
    }

    public void LoadVehicle(Context context)
    {
        Type type =  new TypeToken<Vehicle>() {}.getType();
        JSONSerializer serializer = new JSONSerializer();
        Object vehicle = serializer.Deserialize(type, vehicleFileName,context);

        if(vehicle != null) {
            setCurrentVehicle((Vehicle)vehicle);
        }
    }
}
