package com.demo.movieticketbooking;

import java.math.BigDecimal;

public class Ticket {
    private final Screening screening;
    private final Seat seat;
    private final BigDecimal price;

    public Ticket(Screening screening, Seat seat, BigDecimal price) {
        this.screening = screening;
        this.seat = seat;
        this.price = price;
    }

    public BigDecimal getPrice() {
        return this.price;
    }

    public Seat getSeat() {
        return this.seat;
    }

    // getter and setter methods are omitted for brevity
}
