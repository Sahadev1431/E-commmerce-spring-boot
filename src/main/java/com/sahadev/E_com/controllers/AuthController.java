package com.sahadev.E_com.controllers;

import com.sahadev.E_com.dto.AuthRequestDto;
import com.sahadev.E_com.dto.UserDto;
import com.sahadev.E_com.services.UserService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseCookie;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping ("/api/auth")
public class AuthController {
    @Autowired private AuthenticationManager authenticationManager;
    @Autowired private UserService userService;

    @PostMapping ("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequestDto authRequestDto, HttpServletResponse response) {
        String token = userService.loginUser(authRequestDto);
        ResponseCookie jwtCookie = setCookie(token);
        response.addHeader(HttpHeaders.SET_COOKIE, jwtCookie.toString());
        return ResponseEntity.ok("Login successful");
    }

    @PostMapping ("/register")
    public ResponseEntity<?> registerUser(@RequestBody UserDto userDto,HttpServletResponse response) {
        String token = userService.registerUser(userDto);

        ResponseCookie jwtCookie = setCookie(token);
        response.setHeader(HttpHeaders.SET_COOKIE,jwtCookie.toString());

        return ResponseEntity.ok("User registered successfully");
    }

    @PostMapping ("/log-out")
    public ResponseEntity<?> logoutUser(HttpServletResponse response) {
        ResponseCookie clearCookie = clearCookie();
        response.setHeader(HttpHeaders.SET_COOKIE,clearCookie.toString());
        return ResponseEntity.ok("Logged out successfully");
    }

    public ResponseCookie setCookie(String token) {

        return ResponseCookie.from("jwt",token)
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(24 * 60 * 60)
                .build();
    }

    public ResponseCookie clearCookie() {
        return ResponseCookie.from("jwt","")
                .httpOnly(true)
                .secure(false)
                .path("/")
                .maxAge(0)
                .build();
    }
}
