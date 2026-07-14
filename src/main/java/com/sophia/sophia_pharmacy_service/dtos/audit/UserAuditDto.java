package com.sophia.sophia_pharmacy_service.dtos.audit;

import com.sophia.sophia_pharmacy_service.entities.enums.Provider;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserAuditDto {
    private Long id;
    private String username;
    private String email;
    private String password;
    private Provider provider;
}
