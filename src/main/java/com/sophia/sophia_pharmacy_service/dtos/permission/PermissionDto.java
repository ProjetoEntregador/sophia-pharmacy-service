package com.sophia.sophia_pharmacy_service.dtos.permission;

import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PermissionDto {
    private Long id;
    private String pharmacy;
    private Role role;
}
