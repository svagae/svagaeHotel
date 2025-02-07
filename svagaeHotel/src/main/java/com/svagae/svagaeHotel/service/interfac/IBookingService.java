package com.svagae.svagaeHotel.service.interfac;

import com.svagae.svagaeHotel.dto.Response;
import com.svagae.svagaeHotel.entity.Booking;

public interface IBookingService {
    Response saveBooking(Long roomId, Long userId, Booking bookingRequest);

    Response findBookingByConfirmationCode(String confirmationCode);

    Response getAllBookings();

    Response cancelBooking(Long bookingId);
}
