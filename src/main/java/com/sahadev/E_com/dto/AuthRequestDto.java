package com.sahadev.E_com.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter @AllArgsConstructor
@NoArgsConstructor

public class AuthRequestDto {
    private String email;
    private String password;
}
