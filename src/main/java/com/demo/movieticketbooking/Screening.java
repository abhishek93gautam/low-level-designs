package com.demo.movieticketbooking;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.UUID;

public class Screening {
    private final UUID id;
    private final Movie movie;
    private final Room room;
    private final LocalDateTime startTime;
    private final LocalDateTime endTime;

    public Screening(Movie movie, Room room, LocalDateTime startTime, LocalDateTime endTime) {
        this.id = UUID.randomUUID();
        this.movie = movie;
        this.room = room;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Duration getDuration() {
        return Duration.between(startTime, endTime);
    }

    public Room getRoom() {
        return this.room;
    }

    public UUID getId() {
        return this.id;
    }

    public Movie getMovie() {
        return this.movie;
    }
}
