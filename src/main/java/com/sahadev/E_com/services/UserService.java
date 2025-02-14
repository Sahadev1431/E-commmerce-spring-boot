package com.sahadev.E_com.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sahadev.E_com.dto.AuthRequestDto;
import com.sahadev.E_com.dto.UserDto;
import com.sahadev.E_com.entities.User;
import com.sahadev.E_com.repos.UserRepo;
import com.sahadev.E_com.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service

public class UserService  implements UserDetailsService {
    @Autowired private UserRepo userRepo;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private JwtUtil jwtUtil;
    @Autowired private PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public UserService(@Lazy AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    public UserDto addUser(UserDto userDto) {
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User newUser = objectMapper.convertValue(userDto, User.class);
        return objectMapper.convertValue(userRepo.save(newUser), UserDto.class);
    }

    public User getUserByEmail (String email) {
        return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
    }

    public String loginUser(AuthRequestDto authRequestDto) {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequestDto.getEmail(),authRequestDto.getPassword()));
            User user = userRepo.findByEmail(authRequestDto.getEmail()).orElseThrow(() -> new RuntimeException("User not found with email : " + authRequestDto.getEmail()));

            return jwtUtil.generateToken(user);
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid username or password");
        }
    }

    public String registerUser(UserDto userDto) {
        String hashedPassword =passwordEncoder.encode(userDto.getPassword());
        User newUser = objectMapper.convertValue(userDto, User.class);
        newUser.setPassword(hashedPassword);
        userRepo.save(newUser);

        return jwtUtil.generateToken(newUser);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return userRepo.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User not found with email : " + email));
    }
}
