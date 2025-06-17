package com.camoutech.enotesapiservice.service;

import com.camoutech.enotesapiservice.dto.LoginRequest;
import com.camoutech.enotesapiservice.dto.LoginResponse;
import com.camoutech.enotesapiservice.dto.UserDto;

public interface UserService {

    public Boolean register(UserDto userDto, String url) throws Exception;

    public LoginResponse login(LoginRequest loginRequest);
}
