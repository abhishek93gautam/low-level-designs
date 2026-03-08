package com.demo.movieticketbooking;

public class Seat {
    private final String seatNumber;
    private PricingStrategy pricingStrategy;

    public Seat(String seatNumber, PricingStrategy pricingStrategy) {
        this.seatNumber = seatNumber;
        this.pricingStrategy = pricingStrategy;
    }

    public PricingStrategy getPricingStrategy() {
        return this.pricingStrategy;
    }

    public String getSeatNumber() {
        return this.seatNumber;
    }
}
