package com.sahadev.E_com.DTO;

import com.sahadev.E_com.entities.Role;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.PrePersist;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
