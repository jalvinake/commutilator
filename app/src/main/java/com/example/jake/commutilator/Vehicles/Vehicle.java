package com.example.jake.commutilator.Vehicles;

/**
 * Created by Jake on 4/11/2015.
 */
public class Vehicle {
    public Vehicle(){

    }

    public Vehicle(Vehicle currentVehicle){
        this.setYear(new String(currentVehicle.getYear()));
        this.setMake(new String(currentVehicle.getMake()));
        this.setModel(new String(currentVehicle.getModel()));
        this.setModelOption(new String(currentVehicle.getModelOption()));
        this.setFuelData(new VehicleFuelData(currentVehicle.getFuelData()));
        this.setVehicleId(new String(currentVehicle.getVehicleId()));
    }


    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    private String year;

    private String make;

    public String getMake() {
        return make;
    }

    public void setMake(String make) {
        this.make = make;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    private String model;

    public String getModelOption() {
        return modelOption;
    }

    public void setModelOption(String modelOption) {
        this.modelOption = modelOption;
    }

    private String modelOption;

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    private String vehicleId;

    public VehicleFuelData getFuelData() {
        return fuelData;
    }

    public void setFuelData(VehicleFuelData fuelData) {
        this.fuelData = fuelData;
    }

    private VehicleFuelData fuelData;
}
