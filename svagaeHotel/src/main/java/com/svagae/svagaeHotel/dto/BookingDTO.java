package com.svagae.svagaeHotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.svagae.svagaeHotel.entity.Room;
import com.svagae.svagaeHotel.entity.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BookingDTO {
    private long id;
    private LocalDate startDate;
    private LocalDate endDate;

    private int numberOfAdults;
    private int numberOfChildren;
    private int numberOfGuests;
    private String bookingConfirmationCode;
    private UserDTO user;
    private RoomDTO room;
}
