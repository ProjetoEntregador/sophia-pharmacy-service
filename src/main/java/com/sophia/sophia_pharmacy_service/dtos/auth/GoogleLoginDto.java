package com.sophia.sophia_pharmacy_service.dtos.auth;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GoogleLoginDto {
    private String idToken;

    public GoogleLoginDto(String idToken){
        this.idToken = idToken;
    }
}