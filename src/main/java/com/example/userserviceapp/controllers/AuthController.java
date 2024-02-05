package com.example.userserviceapp.controllers;

import com.example.userserviceapp.dtos.*;
import com.example.userserviceapp.exceptions.*;
import com.example.userserviceapp.models.SessionStatus;
import com.example.userserviceapp.models.User;
import com.example.userserviceapp.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.MultiValueMapAdapter;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    public ResponseEntity<UserDto> login(@RequestBody LoginRequestDto loginRequestDto) throws UserDoesntExistException, InvalidPasswordException {
        return authService.login(loginRequestDto.getEmail(), loginRequestDto.getPassword());
    }
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestBody LogoutRequestDto logoutRequestDto){
        authService.logout(logoutRequestDto.getToken(),logoutRequestDto.getUserId());
        return ResponseEntity.ok().build();

    }
    @PostMapping("/signup")
    public ResponseEntity<UserDto> signUp(@RequestBody SignUpRequestDto signUpRequestDto) throws UserAlreadyExistsException {
        UserDto userDto = UserDto.from(authService.signUp(signUpRequestDto.getEmail(),signUpRequestDto.getPassword()));
        return new ResponseEntity<>(userDto, HttpStatus.CREATED);
    }
    @PostMapping("/validate")
    public ResponseEntity<ValidateTokenResponseDto> validate(@RequestBody ValidateTokenRequestDto validateTokenRequestDto){
        User user = authService.validate(validateTokenRequestDto.getToken(), validateTokenRequestDto.getUserId());
        if(user == null){
           ValidateTokenResponseDto validateTokenResponseDto = new ValidateTokenResponseDto();
           validateTokenResponseDto.setSessionStatus(SessionStatus.INVALID);
           return new ResponseEntity<ValidateTokenResponseDto>(validateTokenResponseDto,HttpStatus.OK);

        }
        ValidateTokenResponseDto validateTokenResponseDto = new ValidateTokenResponseDto();
        validateTokenResponseDto.setUserDto(UserDto.from(user));
        validateTokenResponseDto.setSessionStatus(SessionStatus.ACTIVE);
        return new ResponseEntity<ValidateTokenResponseDto>(validateTokenResponseDto,HttpStatus.OK);
    }
}
