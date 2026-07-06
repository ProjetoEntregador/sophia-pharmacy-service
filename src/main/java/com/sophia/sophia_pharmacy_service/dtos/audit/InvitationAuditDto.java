package com.sophia.sophia_pharmacy_service.dtos.audit;

import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import com.sophia.sophia_pharmacy_service.entities.User;
import com.sophia.sophia_pharmacy_service.entities.enums.InvitationStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class InvitationAuditDto {
    private Long id;
    private String email;
    private String token;
    private InvitationStatus status;
    private LocalDateTime expiration;
    private Long pharmacyId;
    private Long invitedByUserId;
}
