package com.company.taskbroker.dto;

import lombok.Data;

@Data
public class UserRegistrationRequestDto {
    private String username;
    private String email;
    private String password;
}
