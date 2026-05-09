package com.sophia.sophia_pharmacy_service.dtos.invitation;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class InviteDto {

    @Email
    @NotBlank
    private String email;
}