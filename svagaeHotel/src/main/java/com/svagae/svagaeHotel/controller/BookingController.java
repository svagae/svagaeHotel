package com.svagae.svagaeHotel.controller;

import com.svagae.svagaeHotel.dto.Response;
import com.svagae.svagaeHotel.entity.Booking;
import com.svagae.svagaeHotel.service.interfac.IBookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bookings")
public class BookingController {
    @Autowired
    private IBookingService bookingService;

    @PostMapping("/book-room/{roomId}/{userId}")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> saveBooking(@PathVariable Long roomId,
                                                @PathVariable Long userId,
                                                @RequestBody Booking bookingRequest) {
        Response response= bookingService.saveBooking(roomId, userId, bookingRequest);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @GetMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<Response> getAllBookings() {
        Response response= bookingService.getAllBookings();
        return ResponseEntity.status(response.getStatusCode()).body(response);

    }
    @GetMapping("/get-by-confirmation-code/{confirmation}")
    public ResponseEntity<Response> getBookingByConfirmation(@PathVariable String confirmation) {
        Response response = bookingService.findBookingByConfirmationCode(confirmation);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
    @DeleteMapping("/all")
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('USER')")
    public ResponseEntity<Response> cancelBooking(@PathVariable Long bookingId) {
        Response response = bookingService.cancelBooking(bookingId);
        return ResponseEntity.status(response.getStatusCode()).body(response);
    }
}
