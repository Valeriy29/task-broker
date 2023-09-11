package com.company.taskbroker.rest;

import com.company.taskbroker.dto.AuthenticationRequestDto;
import com.company.taskbroker.dto.AuthenticationResponseDto;
import com.company.taskbroker.exception.CustomException;
import com.company.taskbroker.exception.HttpInternalServerErrorException;
import com.company.taskbroker.model.User;
import com.company.taskbroker.security.jwt.JwtTokenProvider;
import com.company.taskbroker.service.UserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(value="Authorization endpoints")
@AllArgsConstructor
@RestController
@RequestMapping(value = "api/v1/auth/")
public class AuthenticationRestControllerV1 {

    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;
    private final UserService userService;

    @ApiOperation(value = "User login")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Authorization successful"),
            @ApiResponse(code = 403, message = "Access denied, authorization failed"),
    })
    @PostMapping("login")
    public ResponseEntity<AuthenticationResponseDto> login(@RequestBody AuthenticationRequestDto requestDto) {
        try {
            String username = requestDto.getUsername();
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));

            User user = userService.findByUserName(username);
            if (user == null) {
                throw new CustomException("User with username: " + username + " not found");
            }

            String token = jwtTokenProvider.createToken(user);
            AuthenticationResponseDto response = new AuthenticationResponseDto();
            response.setUsername(username);
            response.setToken(token);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (CustomException e) {
            throw new UsernameNotFoundException(e.getMessage());
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password");
        } catch (Exception e) {
            throw new HttpInternalServerErrorException(e.getMessage());
        }
    }
}