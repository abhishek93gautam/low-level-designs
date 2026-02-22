package com.demo.parkinglot;

public class CompactSpot implements ParkingSpot {
    private int spotNumber;
    private Vehicle vehicle; // The vehicle currently occupying this spot

    public CompactSpot(int spotNumber) {
        this.spotNumber = spotNumber;
        this.vehicle = null; // No vehicle occupying initially
    }

    @Override
    public int getSpotNumber() {
        return spotNumber;
    }

    @Override
    public boolean isAvailable() {
        return vehicle == null;
    }

    @Override
    public void occupy(Vehicle vehicle) {
        if (isAvailable()) {
            this.vehicle = vehicle;
        } else {
            // Spot is already occupied.
        }
    }

    @Override
    public void vacate() {
        this.vehicle = null; // Make the spot available
    }

    @Override
    public VehicleSize getSize() {
        return VehicleSize.SMALL; // Compact spots fit small vehicles
    }
}