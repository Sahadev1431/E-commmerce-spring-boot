package com.sahadev.E_com.dto;

import com.sahadev.E_com.entities.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserDto {
    private Long id;

    @NotBlank
    private String firstName;
    @NotBlank
    private String lastName;

    @NotBlank
    private String email;
    @NotBlank
    private String password;

    @NotBlank
    private String phone;

    private Role role;

}
