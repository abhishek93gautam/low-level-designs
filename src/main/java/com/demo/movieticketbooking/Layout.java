package com.demo.movieticketbooking;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Layout {
    private final int rows;
    private final int columns;

    // Maps seat numbers (e.g., "0-0") to Seat objects for direct access
    private final Map<String, Seat> seatsByNumber;

    // Nested map for position-based access (row → column → seat)
    private final Map<Integer, Map<Integer, Seat>> seatsByPosition;

    public Layout(int rows, int columns) {
        this.rows = rows;
        this.columns = columns;
        this.seatsByNumber = new LinkedHashMap<>();
        this.seatsByPosition = new HashMap<>();
        initializeLayout();
    }

    // Creates seats for all positions with default null pricing
    private void initializeLayout() {
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < columns; j++) {
                String seatNumber = i + "-" + j;
                if (i >= j) {
                    addSeat(seatNumber, i, j, new Seat(seatNumber, new PremiumRate(BigDecimal.valueOf(20))));
                } else {
                    addSeat(seatNumber, i, j, new Seat(seatNumber, new NormalRate(BigDecimal.valueOf(10))));
                }
            }
        }
    }

    public void addSeat(String seatNumber, int row, int column, Seat seat) {
        // Store seat in number-based lookup map
        seatsByNumber.put(seatNumber, seat);
        // Store seat in position-based lookup map
        seatsByPosition.computeIfAbsent(row, k -> new HashMap<>()).put(column, seat);
    }

    public Seat getSeatByNumber(String seatNumber) {
        return seatsByNumber.get(seatNumber);
    }

    // Gets a seat by its row and column position
    public Seat getSeatByPosition(int row, int column) {
        Map<Integer, Seat> rowSeats = seatsByPosition.get(row);
        return (rowSeats != null) ? rowSeats.get(column) : null;
    }

    public List<Seat> getAllSeats() {
        return List.copyOf(seatsByNumber.values());
    }
}
