package com.sophia.sophia_pharmacy_service.dtos.audit;

import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PermissionAuditDto {
    private Long id;
    private Long userId;
    private Long pharmacyId;
    private Role role;
}
