package com.svagae.svagaeHotel.service.interfac;

import com.svagae.svagaeHotel.dto.LoginRequest;
import com.svagae.svagaeHotel.dto.Response;
import com.svagae.svagaeHotel.entity.User;

public interface IUserService {
    Response register(User user);
    Response login(LoginRequest loginRequest);
    Response getAllUsers();
    Response getUserBookingHistory(String userId);
    Response deleteUser(String userId);
    Response getUserId(String userId);
    Response getMyInfo(String email);
}
