package com.svagae.svagaeHotel.service.implement;

import com.svagae.svagaeHotel.dto.BookingDTO;
import com.svagae.svagaeHotel.dto.Response;
import com.svagae.svagaeHotel.entity.Booking;
import com.svagae.svagaeHotel.entity.Room;
import com.svagae.svagaeHotel.entity.User;
import com.svagae.svagaeHotel.exception.HoException;
import com.svagae.svagaeHotel.repo.BookingRepository;
import com.svagae.svagaeHotel.repo.RoomRepository;
import com.svagae.svagaeHotel.repo.UserRepository;
import com.svagae.svagaeHotel.service.interfac.IBookingService;
import com.svagae.svagaeHotel.service.interfac.IRoomService;
import com.svagae.svagaeHotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookingService implements IBookingService {
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private IRoomService roomService;
    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Response saveBooking(Long roomId, Long userId, Booking bookingRequest) {
        Response response= new Response();

        try{
            if(bookingRequest.getEndDate().isBefore(bookingRequest.getStartDate())){
                throw new IllegalArgumentException("End date cannot be before start date");
            }
            Room room = roomRepository.findById(Math.toIntExact(roomId)).orElseThrow(()-> new HoException("Room Not Found"));
            User user = userRepository.findById(userId).orElseThrow(()-> new HoException("User Not Found"));

            List<Booking> existingBookings = room.getBookings();

            if(!roomIsAvailable(bookingRequest,existingBookings)){
                throw new HoException("Room Not Available for Selected date Range");
            }
            bookingRequest.setRoom(room);
            bookingRequest.setUser(user);
            String bookingConfirmation = Utils.generateRandomConfirmationCode(10);
            bookingRequest.setBookingConfirmationCode(bookingConfirmation);
            response.setStatusCode(200);
            response.setMessage("Booking Confirmed");
            response.setBookingConfirmationCode(bookingConfirmation);


        }catch (HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Saving a booking"+e.getMessage());

        }
        return response;
    }



    private boolean roomIsAvailable(Booking bookingRequest, List<Booking> existingBookings) {
        return existingBookings.stream()
                .noneMatch(existingBooking ->
                        bookingRequest.getStartDate().equals(existingBooking.getStartDate())
                        || bookingRequest.getEndDate().isBefore(existingBooking.getEndDate())
                        || (bookingRequest.getStartDate().isAfter(existingBooking.getStartDate())
                         && bookingRequest.getStartDate().isBefore(existingBooking.getEndDate()))
                        ||(bookingRequest.getStartDate().isBefore(existingBooking.getStartDate())

                        && bookingRequest.getEndDate().equals(existingBooking.getEndDate()))
                        || (bookingRequest.getStartDate().isBefore(existingBooking.getStartDate())

                        && bookingRequest.getEndDate().isAfter(existingBooking.getEndDate()))

                        || (bookingRequest.getStartDate().equals(existingBooking.getEndDate())
                        && bookingRequest.getEndDate().equals(existingBooking.getStartDate()))

                        || (bookingRequest.getStartDate().equals(existingBooking.getEndDate())
                        && bookingRequest.getEndDate().equals(bookingRequest.getStartDate()))
                );

    }

    @Override
    public Response findBookingByConfirmationCode(String confirmationCode) {
        Response response= new Response();

        try{
            Booking booking = bookingRepository.findByBookingConfirmationCode(confirmationCode).orElseThrow(()-> new HoException("Booking Not Found"));
            BookingDTO bookingDTO = Utils.mapBookingEnitiyToBokingDTO(booking);
            response.setStatusCode(200);
            response.setMessage("Booking Confirmed");
           response.setBooking(bookingDTO);


        }catch (HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Saving a booking"+e.getMessage());

        }
        return response;
    }


    @Override
    public Response getAllBookings() {
        Response response= new Response();

        try{
           List<Booking> bookings = bookingRepository.findAll(Sort.by(Sort.Direction.DESC, "id"));
           List<BookingDTO> bookingDTOList = Utils.mapBookingListEntityToBookingDTOList(bookings);
            response.setStatusCode(200);
            response.setMessage("Bookings Retrieved");
            response.setBookingList(bookingDTOList);



        }catch (HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Retrieving bookings "+e.getMessage());

        }
        return response;
    }

    @Override
    public Response cancelBooking(Long bookingId) {
        Response response= new Response();

        try{
            bookingRepository.findById(bookingId).orElseThrow(()-> new HoException("Booking Not Found"));
            bookingRepository.deleteById(bookingId);
            response.setStatusCode(200);
            response.setMessage("Booking Cancelled");
            //  response.


        }catch (HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error canceling your booking"+e.getMessage());

        }
        return response;
    }
}
