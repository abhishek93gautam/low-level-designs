package com.demo.movieticketbooking;

public class Room {
    private final String roomNumber;
    private final Layout layout;

    public Room(String roomNumber, Layout layout) {
        this.roomNumber = roomNumber;
        this.layout = layout;
    }

    public Layout getLayout() {
        return this.layout;
    }
}
