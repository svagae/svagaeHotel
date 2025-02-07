package com.svagae.svagaeHotel.service.implement;

import com.svagae.svagaeHotel.dto.Response;
import com.svagae.svagaeHotel.dto.RoomDTO;
import com.svagae.svagaeHotel.entity.Room;
import com.svagae.svagaeHotel.exception.HoException;
import com.svagae.svagaeHotel.repo.BookingRepository;
import com.svagae.svagaeHotel.repo.RoomRepository;
import com.svagae.svagaeHotel.service.AwsS3Service;
import com.svagae.svagaeHotel.service.interfac.IRoomService;
import com.svagae.svagaeHotel.utils.Utils;
import org.springframework.beans.factory.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
@Service
public class RoomService implements IRoomService {

    @Autowired
    private RoomRepository roomRepository;
    @Autowired
    private BookingRepository bookingRepository;
    @Autowired
    private AwsS3Service awsS3Service;
    @Override
    public Response addNewRoom(MultipartFile photo, String roomType, BigDecimal roomPrice, String description) {
        Response response = new Response();

        try{
            String imageUrl = awsS3Service.saveImageToS3(photo);
            Room room = new Room();
            room.setRoomType(roomType);
            room.setRoomPrice(roomPrice);
            room.setRoomPhotoUrl(imageUrl);
            room.setRoomDescription(description);
            Room savedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEnitiyToRoomDTO(savedRoom);
            response.setStatusCode(200);
            response.setMessage("Room added successfully");
            response.setRoom(roomDTO);

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error adding new room"+e.getMessage());
        }
        return response;
    }

    @Override
    public List<String> getAllRoomTypes() {
        return roomRepository.findDistinctRoomTypes();
    }

    @Override
    public Response getAllRooms() {
        Response response = new Response();
        try {
            List<Room> roomList = roomRepository.findAll(Sort.by(Sort.Direction.DESC,"id"));
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomDTOList(roomList);
            response.setStatusCode(200);


            response.setMessage("Successfully Retrieved All Rooms");
            response.setRoomList(roomDTOList);
        }catch (Exception e){
        response.setStatusCode(500);
        response.setMessage("Error getting All room"+e.getMessage());
    }
        return response;
    }

    @Override
    public Response deleteRoom(Long roomId) {
        Response response = new Response();
        try {
            roomRepository.findById(Math.toIntExact(roomId)).orElseThrow(()-> new HoException("Room Not Found"));
            roomRepository.deleteById(Math.toIntExact(roomId));

            response.setStatusCode(200);


            response.setMessage("Successfully Deleted the Room");

        }
        catch(HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error deleting room"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response updateRoom(Long roomId, String description, String roomType, BigDecimal roomPrice, MultipartFile photo) {
        Response response = new Response();
        try {
            String imageUrl = null;
            if(photo!=null && !photo.isEmpty()){
                imageUrl = awsS3Service.saveImageToS3(photo);
            }
            Room room = roomRepository.findById(Math.toIntExact(roomId)).orElseThrow(()-> new HoException("Room Not Found"));
            if(roomType!=null)room.setRoomType(roomType);
            if(roomPrice!=null)room.setRoomPrice(roomPrice);
            if(description!=null)room.setRoomDescription(description);
            if(imageUrl!=null)room.setRoomPhotoUrl(imageUrl);

            Room updatedRoom = roomRepository.save(room);
            RoomDTO roomDTO = Utils.mapRoomEnitiyToRoomDTO(updatedRoom);



            response.setStatusCode(200);
            response.setMessage("Successfully Deleted the Room");
            response.setRoom(roomDTO);

        }
        catch(HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error deleting room"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getRoomById(Long roomId) {
        Response response = new Response();
        try {
            Room room = roomRepository.findById(Math.toIntExact(roomId)).orElseThrow(()-> new HoException("Room Not Found"));
            RoomDTO roomDTO = Utils.mapRoomEnitiyToRoomDTOPlusBooking(room);
            roomRepository.deleteById(Math.toIntExact(roomId));


            response.setStatusCode(200);


            response.setMessage("Successfully Deleted the Room");
            response.setRoom(roomDTO);

        }
        catch(HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error deleting room"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAvailableRoomsByDataAndType(LocalDate checkInDate, LocalDate checkOutDate, String roomType) {
        Response response = new Response();
        try {
            List<Room> availableRooms =roomRepository.findAvailableRoomByDatesAndTypes(checkInDate,checkOutDate,roomType);
            List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomDTOList(availableRooms);


            response.setStatusCode(200);


            response.setMessage("Successfully Deleted the Room");
            response.setRoomList(roomDTOList);

        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error deleting room"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllAvailableRooms() {
        Response response = new Response();
        try {
           List<Room> roomList= roomRepository.getAvailableRooms();
           List<RoomDTO> roomDTOList = Utils.mapRoomListEntityToRoomDTOList(roomList);


            response.setStatusCode(200);


            response.setMessage("Successfully Deleted the Room");
            response.setRoomList(roomDTOList);

        }
        catch(HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());

        } catch(Exception e){
            response.setStatusCode(500);
            response.setMessage("Error deleting room"+e.getMessage());
        }
        return response;
    }

}
