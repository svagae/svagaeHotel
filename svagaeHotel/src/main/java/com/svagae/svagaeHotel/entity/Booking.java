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
}
