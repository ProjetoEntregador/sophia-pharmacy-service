package com.sophia.sophia_pharmacy_service.entities;

import com.sophia.sophia_pharmacy_service.entities.enums.InvitationStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@Table(name="tb_invitations")
@Entity
public class Invitation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String token;

    @Enumerated(EnumType.STRING)
    private InvitationStatus status;

    private LocalDateTime expiration;

    @ManyToOne
    private Pharmacy pharmacy;

    @ManyToOne
    private User invitedBy;
}
