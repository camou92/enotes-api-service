package com.camoutech.enotesapiservice.service.impl;

import com.camoutech.enotesapiservice.dto.UserDto;
import com.camoutech.enotesapiservice.entity.Role;
import com.camoutech.enotesapiservice.entity.User;
import com.camoutech.enotesapiservice.repository.RoleRepository;
import com.camoutech.enotesapiservice.repository.UserRepository;
import com.camoutech.enotesapiservice.service.UserService;
import com.camoutech.enotesapiservice.util.Validation;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ModelMapper mapper;
    @Autowired
    private Validation validation;

    @Override
    public Boolean register(UserDto userDto) {

        validation.userValidation(userDto);
        User user = mapper.map(userDto, User.class);

        setRole(userDto, user);

        User saveUser = userRepository.save(user);
        if (!ObjectUtils.isEmpty(saveUser)) {
            return true;
        }
        return false;
    }

    private void setRole(UserDto userDto, User user) {
        List<Integer> reqRoleId = userDto.getRoles().stream().map(r -> r.getId()).toList();
        List<Role> roles = roleRepository.findAllById(reqRoleId);
        user.setRoles(roles);
    }
}
