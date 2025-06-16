package com.camoutech.enotesapiservice.service;

import com.camoutech.enotesapiservice.dto.UserDto;

public interface UserService {

    public Boolean register(UserDto userDto) throws Exception;
}
