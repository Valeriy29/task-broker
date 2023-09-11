package com.company.taskbroker.dto;

import lombok.Data;

@Data
public class AuthenticationResponseDto {
    private String username;
    private String token;
}
