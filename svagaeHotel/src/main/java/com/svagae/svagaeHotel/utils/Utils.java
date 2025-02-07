package com.svagae.svagaeHotel.utils;

import com.svagae.svagaeHotel.dto.BookingDTO;
import com.svagae.svagaeHotel.dto.RoomDTO;
import com.svagae.svagaeHotel.dto.UserDTO;
import com.svagae.svagaeHotel.entity.Booking;
import com.svagae.svagaeHotel.entity.Room;
import com.svagae.svagaeHotel.entity.User;

import java.security.SecureRandom;
import java.util.List;
import java.util.stream.Collectors;

public class Utils {
    private static final String ALPHANUMERIC_STRING = "ABCDEFGHIGKLMNOPQRSTUVWXYZ0123456789";
    private static final SecureRandom secureRandom = new SecureRandom();

    public static String generateRandomConfirmationCode(int length) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int randomIndex = secureRandom.nextInt(ALPHANUMERIC_STRING.length());
            char randomChar = ALPHANUMERIC_STRING.charAt(randomIndex);
            stringBuilder.append(randomChar);
        }
        return stringBuilder.toString();
    }

    public static UserDTO mapUserEnitiyToUserDTO(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());
        return userDTO;


    }
    public static RoomDTO mapRoomEnitiyToRoomDTO(Room room){
        RoomDTO roomDTO = new RoomDTO();
        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomDescription(room.getRoomDescription());
        return roomDTO;
    }
    public static UserDTO mapUserEnitiyToUserDTOPlusUserBookingsAndRoom(User user){
        UserDTO userDTO = new UserDTO();

        userDTO.setId(user.getId());
        userDTO.setName(user.getName());
        userDTO.setEmail(user.getEmail());
        userDTO.setPhoneNumber(user.getPhoneNumber());
        userDTO.setRole(user.getRole());

        if(!user.getBookings().isEmpty()){
            userDTO.setBookings(user.getBookings().stream().map(booking -> mapBookingEnitiyToBookingDTOPlusBookedRooms(booking,false)).collect(Collectors.toList()));

        }

        return userDTO;
    }
    public static BookingDTO mapBookingEnitiyToBokingDTO(Booking booking){
        BookingDTO bookingDTO = new BookingDTO();
        bookingDTO.setStartDate(booking.getStartDate());
        bookingDTO.setEndDate(booking.getEndDate());

        bookingDTO.setId(booking.getId());
        bookingDTO.setNumberOfChildren(booking.getNumberOfChildren());
        bookingDTO.setNumberOfAdults(booking.getNumberOfAdults());
        bookingDTO.setNumberOfGuests(booking.getNumberOfGuests());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        return bookingDTO;


    }
    public static RoomDTO mapRoomEnitiyToRoomDTOPlusBooking(Room room){
        RoomDTO roomDTO = new RoomDTO();

        roomDTO.setId(room.getId());
        roomDTO.setRoomType(room.getRoomType());
        roomDTO.setRoomPrice(room.getRoomPrice());
        roomDTO.setRoomPhotoUrl(room.getRoomPhotoUrl());
        roomDTO.setRoomDescription(room.getRoomDescription());
        if(room.getBookings()!=null){
            roomDTO.setBookings(room.getBookings().stream().map(Utils::mapBookingEnitiyToBokingDTO).collect(Collectors.toList()));
        }
        return roomDTO;


    }
    public static BookingDTO mapBookingEnitiyToBookingDTOPlusBookedRooms(Booking booking,boolean mapUser){
        BookingDTO bookingDTO = new BookingDTO();

        bookingDTO.setStartDate(booking.getStartDate());
        bookingDTO.setEndDate(booking.getEndDate());
        bookingDTO.setId(booking.getId());
        bookingDTO.setNumberOfChildren(booking.getNumberOfChildren());
        bookingDTO.setNumberOfAdults(booking.getNumberOfAdults());
        bookingDTO.setNumberOfGuests(booking.getNumberOfGuests());
        bookingDTO.setBookingConfirmationCode(booking.getBookingConfirmationCode());
        if(mapUser){
            bookingDTO.setUser(Utils.mapUserEnitiyToUserDTO(booking.getUser()));
        }
        if(booking.getRoom()!=null){
            RoomDTO roomDTO = new RoomDTO();
            roomDTO.setId(booking.getRoom().getId());
            roomDTO.setRoomType(booking.getRoom().getRoomType());
            roomDTO.setRoomPhotoUrl(booking.getRoom().getRoomPhotoUrl());
            roomDTO.setRoomPrice(booking.getRoom().getRoomPrice());
            roomDTO.setRoomDescription(booking.getRoom().getRoomDescription());

        }
        return bookingDTO;


    }
    public static List<UserDTO> mapUserListEntityToUserDTOList(List<User> userList){
        return userList.stream().map(Utils::mapUserEnitiyToUserDTO).collect(Collectors.toList());
    }
    public static List<RoomDTO> mapRoomListEntityToRoomDTOList(List<Room> roomList){
        return roomList.stream().map(Utils::mapRoomEnitiyToRoomDTO).collect(Collectors.toList());
    }
    public static List<BookingDTO> mapBookingListEntityToBookingDTOList(List<Booking> bookingList){
        return bookingList.stream().map(Utils::mapBookingEnitiyToBokingDTO).collect(Collectors.toList());
    }



}
