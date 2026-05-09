package com.sophia.sophia_pharmacy_service.controllers;

import com.sophia.sophia_pharmacy_service.dtos.auth.GoogleLoginDto;
import com.sophia.sophia_pharmacy_service.dtos.auth.LoginDto;
import com.sophia.sophia_pharmacy_service.dtos.auth.UserDto;
import com.sophia.sophia_pharmacy_service.dtos.response.ApiResponseDto;
import com.sophia.sophia_pharmacy_service.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.Map;

@RestController
@RequestMapping("/auth")
public class UserController {

    @Autowired
    private UserService userService;


    @PostMapping("/registration")
    public ResponseEntity<ApiResponseDto<Void>> register(@Valid @RequestBody UserDto dto) {
        userService.register(dto);

        ApiResponseDto<Void> response = new ApiResponseDto<>("success",
                null, "Usuário criado com sucesso");

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponseDto<String>> login(@Valid @RequestBody LoginDto dto) {
        String token = userService.login(dto);

        ApiResponseDto<String> response = new ApiResponseDto<>("success",
                token, "Login efetuado com sucessso");

        return ResponseEntity.ok(response);
    }

    @PostMapping("/google")
    public ResponseEntity<ApiResponseDto<String>> loginGoogle(@RequestBody GoogleLoginDto dto) {

        String token = userService.loginWithGoogle(dto.getIdToken());

        ApiResponseDto<String> response = new ApiResponseDto<>("success",
                token, "Login Google efetuado com sucessso");

        return ResponseEntity.ok(response);
    }

}
