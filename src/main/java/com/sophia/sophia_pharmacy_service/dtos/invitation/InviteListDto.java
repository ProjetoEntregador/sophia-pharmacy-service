package com.sophia.sophia_pharmacy_service.dtos.invitation;

import com.sophia.sophia_pharmacy_service.entities.enums.InvitationStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class InviteListDto {
    private Long id;
    private String email;
    private InvitationStatus status;
    private String expiration;
    private String invitedBy;
}
