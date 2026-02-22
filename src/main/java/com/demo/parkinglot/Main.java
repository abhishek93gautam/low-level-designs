package com.demo.parkinglot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        BaseFareStrategy baseFareStrategy = new BaseFareStrategy();
        PeakHoursFareStrategy peakHoursFareStrategy = new PeakHoursFareStrategy();
        List<FareStrategy> strategyList = new ArrayList<>();
        strategyList.add(baseFareStrategy);
        strategyList.add(peakHoursFareStrategy);
        Map<VehicleSize, ArrayList<ParkingSpot>> availableSpots = new HashMap<>();
        ArrayList<ParkingSpot> compactList = new ArrayList<>();
        compactList.add(new CompactSpot(1));
        compactList.add(new CompactSpot(2));

        ArrayList<ParkingSpot> regularList = new ArrayList<>();
        regularList.add(new RegularSpot(1));
        regularList.add(new RegularSpot(2));

        ArrayList<ParkingSpot> oversizedList = new ArrayList<>();
        oversizedList.add(new OversizedSpot(5));
        oversizedList.add(new OversizedSpot(6));

        availableSpots.put(VehicleSize.SMALL, compactList);
        availableSpots.put(VehicleSize.MEDIUM, regularList);
        availableSpots.put(VehicleSize.LARGE, oversizedList);

        ParkingManager parkingManager = new ParkingManager(availableSpots);
        FareCalculator fareCalculator = new FareCalculator(strategyList);
        ParkingLot parkingLot = new ParkingLot(parkingManager, fareCalculator);
        Vehicle vehicle = new Car("KA03");

        Ticket ticket = parkingLot.enterVehicle(vehicle);

        System.out.println("Vehicle " + vehicle.getLicensePlate() + " parked at slot number " + ticket.getParkingSpot().getSpotNumber() +
                " and entry time is " + ticket.getEntryTime());

        System.out.println("Number of Available slots for " + vehicle.getSize() + " after vehicle entry are : " + availableSpots.get(vehicle.getSize()).size());

        Thread.sleep(30000);

        parkingLot.leaveVehicle(ticket);

        System.out.println("Number of Available slots for " + vehicle.getSize() + " after vehicle exit are : " + availableSpots.get(vehicle.getSize()).size());
    }
}
