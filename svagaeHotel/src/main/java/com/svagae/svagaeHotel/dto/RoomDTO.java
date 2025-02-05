package com.svagae.svagaeHotel.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.svagae.svagaeHotel.entity.Booking;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoomDTO {
    private Long id;
    private String roomDescription;
    private BigDecimal roomPrice;
    private String roomPhotoUrl;
    private String roomType;
    private List<BookingDTO> bookings ;
}
