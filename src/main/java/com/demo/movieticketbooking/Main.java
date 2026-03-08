package com.demo.movieticketbooking;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Collection;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        MovieBookingSystem movieBookingSystem = new MovieBookingSystem();
        Movie movie = new Movie("Dhurandar", "Spy", 300);
        Movie movie1 = new Movie("Titanic", "Spy", 340);
        movieBookingSystem.addMovie(movie);
        movieBookingSystem.addMovie(movie1);
        Cinema cinema = new Cinema("M5 ECity", "Electronic City");
        Cinema cinema2 = new Cinema("Nexus Mall", "Kormangala");

        Layout layout = new Layout(10, 10);
        Layout layout2 = new Layout(5, 5);
        Layout layout3 = new Layout(5, 10);
        Room room1 = new Room("R1", layout);
        Room room2 = new Room("R2", layout2);
        Room room3 = new Room("R3", layout3);

        cinema.addRoom(room1);
        cinema.addRoom(room2);
        cinema2.addRoom(room3);

        movieBookingSystem.addCinema(cinema);
        movieBookingSystem.addCinema(cinema2);

        Screening screening = new Screening(movie, room1,
                LocalDateTime.of(LocalDate.ofYearDay(2026, 8), LocalTime.of(11, 30)),
                LocalDateTime.of(LocalDate.ofYearDay(2026, 8), LocalTime.of(14, 30)));

        Screening screening2 = new Screening(movie1, room2,
                LocalDateTime.of(LocalDate.ofYearDay(2026, 8), LocalTime.of(9, 30)),
                LocalDateTime.of(LocalDate.ofYearDay(2026, 8), LocalTime.of(11, 30)));

        Screening screening3 = new Screening(movie1, room3,
                LocalDateTime.of(LocalDate.ofYearDay(2026, 8), LocalTime.of(15, 30)),
                LocalDateTime.of(LocalDate.ofYearDay(2026, 8), LocalTime.of(18, 30)));

        movieBookingSystem.addScreening(movie, screening);
        movieBookingSystem.addScreening(movie1, screening2);
        movieBookingSystem.addScreening(movie1, screening3);

        System.out.println("Movie " + movie.getTitle()+ " is screening in ");
        printScreening(movieBookingSystem.getScreeningsForMovie(movie));
        System.out.println("Movie " + movie1.getTitle()+ " is screening in ");
        printScreening(movieBookingSystem.getScreeningsForMovie(movie1));

        System.out.println();
        List<Seat> seats = movieBookingSystem.getAvailableSeats(screening);

        System.out.println("Available seats before booking for screening of " + screening.getMovie().getTitle() + " are " + seats.size());
        System.out.println("Seating Layout is below......... ");
        printLayout(seats);

        System.out.println("Started booking for seats 12, 24, 45, 90");

        movieBookingSystem.bookTicket(screening, seats.get(12));
        movieBookingSystem.bookTicket(screening, seats.get(24));
        movieBookingSystem.bookTicket(screening, seats.get(45));
        movieBookingSystem.bookTicket(screening, seats.get(90));

        System.out.println("Available seats after booking for screening of " + screening.getMovie().getTitle() + " are " + movieBookingSystem.getAvailableSeats(screening).size());

        System.out.println("Number of tickets sold for screening of " + screening.getMovie().getTitle() + " are " + movieBookingSystem.getTicketCount(screening));

        System.out.println();
        System.out.println();
        System.out.println("Without Lock trying to Book the same seat again result in extra sold ticket");
        movieBookingSystem.bookTicket(screening, seats.get(90));
        System.out.println("Available seats after booking for screening of " + screening.getMovie().getTitle() + " are " + movieBookingSystem.getAvailableSeats(screening).size());
        System.out.println("Number of tickets sold for screening of " + screening.getMovie().getTitle() + " are " + movieBookingSystem.getTicketCount(screening));

        System.out.println();
        System.out.println();
        System.out.println("With Pessimistic Lock trying to Book the same seat again result in preventing double booking");
        movieBookingSystem.bookSeatOptimistically(screening, seats.get(12)); // This throws exception for double booking
        System.out.println("Available seats after booking for screening of " + screening.getMovie().getTitle() + " are " + movieBookingSystem.getAvailableSeats(screening).size());
        System.out.println("Number of tickets sold for screening of " + screening.getMovie().getTitle() + " are " + movieBookingSystem.getTicketCount(screening));
    }

    public static void printScreening(List<Screening> screeningList) {
        for (Screening screening : screeningList) {
            System.out.println("Room with " + screening.getRoom().getLayout().getAllSeats().size() + " Seats and of duration " + screening.getDuration());
        }
    }

    public static void printLayout(List<Seat> seats) {
        for (Seat seat : seats) {
            System.out.println("Seat " + seat.getSeatNumber() + " for price " + seat.getPricingStrategy().getPrice());
        }
    }
}
