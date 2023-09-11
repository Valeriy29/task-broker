package com.company.taskbroker.rest;

import com.company.taskbroker.dto.UserRegistrationRequestDto;
import com.company.taskbroker.dto.UserRegistrationResponseDto;
import com.company.taskbroker.exception.HttpInternalServerErrorException;
import com.company.taskbroker.mapper.UserMapper;
import com.company.taskbroker.model.User;
import com.company.taskbroker.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value = "Registration endpoints")
@AllArgsConstructor
@RestController
@RequestMapping(value = "api/v1/auth/")
public class RegistrationRestControllerV1 {

    private final UserService userService;
    private final UserMapper userMapper;

    @ApiOperation(value = "User registration")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Registration completed successfully"),
            @ApiResponse(code = 204, message = "Registration failed"),
    })
    @PostMapping("registration")
    public ResponseEntity<UserRegistrationResponseDto> register(@RequestBody UserRegistrationRequestDto requestDto) {
        try {
            User user = userMapper.mapUserRegistrationRequestDtoToUser(requestDto);
            User registeredUser = userService.register(user);
            UserRegistrationResponseDto response = userMapper.mapUserToUserRegistrationResponseDto(registeredUser);
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            throw new HttpInternalServerErrorException(e.getMessage());
        }
    }
}
