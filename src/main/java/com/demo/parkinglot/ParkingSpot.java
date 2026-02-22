package com.demo.parkinglot;

public interface ParkingSpot {
    boolean isAvailable();

    void occupy(Vehicle vehicle);

    void vacate();

    int getSpotNumber();

    VehicleSize getSize();
}
