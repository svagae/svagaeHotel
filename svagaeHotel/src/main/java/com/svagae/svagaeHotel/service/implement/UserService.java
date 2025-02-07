package com.svagae.svagaeHotel.service.implement;

import com.svagae.svagaeHotel.dto.LoginRequest;
import com.svagae.svagaeHotel.dto.Response;
import com.svagae.svagaeHotel.dto.UserDTO;
import com.svagae.svagaeHotel.entity.User;
import com.svagae.svagaeHotel.exception.HoException;
import com.svagae.svagaeHotel.repo.UserRepository;
import com.svagae.svagaeHotel.service.interfac.IUserService;
import com.svagae.svagaeHotel.utils.JWTUtils;
import com.svagae.svagaeHotel.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
@Service

public class UserService implements IUserService {
   @Autowired
   private UserRepository userRepository;
   @Autowired
   private PasswordEncoder passwordEncoder;
    @Autowired
    private JWTUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;



    @Override
    public Response register(User user) {
        Response response = new Response();
        try{
            if(user.getRole() == null || user.getRole().isBlank()){
                user.setRole("USER");
            }
            if(userRepository.existsByEmail(user.getEmail())){
                throw new HoException("Email Already Exists");
            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            User savedUser = userRepository.save(user);
            UserDTO userDTO = Utils.mapUserEnitiyToUserDTO(savedUser);
            response.setStatusCode(200);
            response.setUser(userDTO);

        }catch (HoException e){
            response.setStatusCode(400);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error occured while registering user" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response login(LoginRequest loginRequest) {
        Response response = new Response();

        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));
            var user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(() -> new HoException("Email Not Found"));

            var token = jwtUtils.generateToken(user);
            response.setStatusCode(200);
            response.setToken(token);
            response.setRole(user.getRole());
            response.setExpirationTime("7 Days");
            response.setMessage("Successfully logged in");
        } catch (HoException e) {
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error Occured While User Login" + e.getMessage());
        }
        return response;
    }

    @Override
    public Response getAllUsers() {
        Response response = new Response();
        try{
            List<User> userList = userRepository.findAll();
            List<UserDTO> userDTOList = Utils.mapUserListEntityToUserDTOList(userList);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved all users");
            response.setUserList(userDTOList);

        }catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting all users"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserBookingHistory(String userId) {
        Response response = new Response();
        try{
            User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new HoException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEnitiyToUserDTOPlusUserBookingsAndRoom(user);


        }catch (HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting user booking history"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response deleteUser(String userId) {
        Response response = new Response();
        try{
            userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new HoException("User Not Found"));
            userRepository.deleteById(Long.valueOf(userId));
            response.setStatusCode(200);
             response.setMessage("Successfully deleted user");


        }catch (HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error deleting user"+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getUserId(String userId) {
        Response response = new Response();
        try{
           User user = userRepository.findById(Long.valueOf(userId)).orElseThrow(()-> new HoException("User Not Found"));
           UserDTO userDTO = Utils.mapUserEnitiyToUserDTO(user);
           response.setStatusCode(200);
           response.setMessage("Successfully retrieved user");
           response.setUser(userDTO);


        }catch (HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting user "+e.getMessage());
        }
        return response;
    }

    @Override
    public Response getMyInfo(String email) {
        Response response = new Response();
        try{
            User user = userRepository.findByEmail(email).orElseThrow(()-> new HoException("User Not Found"));
            UserDTO userDTO = Utils.mapUserEnitiyToUserDTO(user);
            response.setStatusCode(200);
            response.setMessage("Successfully retrieved user info");
            response.setUser(userDTO);


        }catch (HoException e){
            response.setStatusCode(404);
            response.setMessage(e.getMessage());
        }
        catch (Exception e){
            response.setStatusCode(500);
            response.setMessage("Error getting user "+e.getMessage());
        }
        return response;
    }
}
