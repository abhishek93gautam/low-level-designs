package com.demo.movieticketbooking;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class MovieBookingSystem {

    private final List<Movie> movies;
    private final List<Cinema> cinemas;
    private final ScreeningManager screeningManager;

    public MovieBookingSystem() {
        this.movies = new ArrayList<>();
        this.cinemas = new ArrayList<>();
        this.screeningManager = new ScreeningManager();
    }

    public void addMovie(Movie movie) {
        movies.add(movie);
    }

    public void addCinema(Cinema cinema) {
        cinemas.add(cinema);
    }

    public void addScreening(Movie movie, Screening screening) {
        screeningManager.addScreening(movie, screening);
    }

    // Books a ticket for a specific seat at a screening
    public void bookTicket(Screening screening, Seat seat) {
        BigDecimal price = seat.getPricingStrategy().getPrice();
        Ticket ticket = new Ticket(screening, seat, price);
        System.out.println("Ticket booked for seat " + seat.getSeatNumber() + " for the price " + price);
        screeningManager.addTicket(screening, ticket);
    }

    // Returns all screenings for a specific movie
    public List<Screening> getScreeningsForMovie(Movie movie) {
        return screeningManager.getScreeningsForMovie(movie);
    }

    // Returns all available seats for a screening
    public List<Seat> getAvailableSeats(Screening screening) {
        return screeningManager.getAvailableSeats(screening);
    }

    // Returns the number of tickets sold for a screening
    public int getTicketCount(Screening screening) {
        return screeningManager.getTicketsForScreening(screening).size();
    }

    // Returns the list of tickets for a screening
    public List<Ticket> getTicketsForScreening(Screening screening) {
        return screeningManager.getTicketsForScreening(screening);
    } // getter and setter methods are omitted for brevity

    // Simplified optimistic locking in ScreeningManager
    public synchronized Ticket bookSeatOptimistically(Screening screening, Seat seat) {
        // First check if a seat is available (optimistic)
        if (isSeatBooked(screening, seat)) {
            throw new IllegalStateException("Seat is already booked");
        }

        // Create ticket - at this point, we're optimistically assuming
        // the seat is still available
        BigDecimal price = seat.getPricingStrategy().getPrice();
        Ticket ticket = new Ticket(screening, seat, price);

        // Add to booking system - this effectively "reserves" the seat
        screeningManager.addTicket(screening, ticket);

        return ticket;
    }

    private boolean isSeatBooked(Screening screening, Seat seat) {
        List<Ticket> tickets = getTicketsForScreening(screening);
        return tickets.stream().anyMatch(ticket -> ticket.getSeat().equals(seat));
    }
}
