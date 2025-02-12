package com.sahadev.E_com.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahadev.E_com.DTO.UserDto;
import com.sahadev.E_com.entities.User;
import com.sahadev.E_com.repos.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service

public class UserService  {
    @Autowired private UserRepo userRepo;
    @Autowired private ObjectMapper objectMapper;

    public UserDto addUser(UserDto userDto) {
        return objectMapper.convertValue(userRepo.save(objectMapper.convertValue(userDto,User.class)),UserDto.class) ;
    }
}
