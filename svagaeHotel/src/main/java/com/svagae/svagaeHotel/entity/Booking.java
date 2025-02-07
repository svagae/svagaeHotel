package com.svagae.svagaeHotel.entity;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name="bookings")

public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @NotNull
    private LocalDate startDate;
    @Future(message = "Check out date must be on future")
    private LocalDate endDate;
    @Min(value = 1,message = "Number of adults must not be less than 1")
    private int numberOfAdults;
    @Min(value = 0,message = "Number of children must not be less than 1")
    private int numberOfChildren;
    private int numberOfGuests;
    private String bookingConfirmationCode;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_id")
    private Room room;


    public void calculateTotalnumberOfguests() {
        this.numberOfGuests = this.numberOfAdults + this.numberOfChildren + this.numberOfGuests;
    }

    public void setNumberOfAdults(int numberOfAdults) {
        this.numberOfAdults = numberOfAdults;
        calculateTotalnumberOfguests();
    }

    public void setNumberOfChildren(int numberOfChildren) {
        this.numberOfChildren = numberOfChildren;
        calculateTotalnumberOfguests();
    }

    @Override
    public String toString() {
        return "Booking{" +
                "id=" + id +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", numberOfAdults=" + numberOfAdults +
                ", numberOfChildren=" + numberOfChildren +
                ", numberOfGuests=" + numberOfGuests +
                ", bookingConfirmationCode='" + bookingConfirmationCode + '\'' +
                '}';
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public int getNumberOfAdults() {
        return numberOfAdults;
    }

    public int getNumberOfChildren() {
        return numberOfChildren;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public String getBookingConfirmationCode() {
        return bookingConfirmationCode;
    }

    public void setBookingConfirmationCode(String bookingConfirmationCode) {
        this.bookingConfirmationCode = bookingConfirmationCode;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }
}
