package com.sophia.sophia_pharmacy_service.services;

import com.sophia.sophia_pharmacy_service.dtos.invitation.InviteListDto;
import com.sophia.sophia_pharmacy_service.dtos.mappers.InvitationMapper;
import com.sophia.sophia_pharmacy_service.emailSender.EmailSender;
import com.sophia.sophia_pharmacy_service.entities.Invitation;
import com.sophia.sophia_pharmacy_service.entities.Permission;
import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import com.sophia.sophia_pharmacy_service.entities.User;
import com.sophia.sophia_pharmacy_service.entities.enums.InvitationStatus;
import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import com.sophia.sophia_pharmacy_service.repositories.InvitationRepository;
import com.sophia.sophia_pharmacy_service.repositories.PermissionRepository;
import com.sophia.sophia_pharmacy_service.repositories.PharmacyRepository;
import com.sophia.sophia_pharmacy_service.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class InvitationService {
    @Autowired
    private InvitationRepository inviteRepository;

    @Autowired
    private PharmacyRepository pharmacyRepository;

    @Autowired
    private EmailSender emailService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private InvitationMapper invitationMapper;

    @Transactional
    public void invite(Long pharmacyId, String email, String ownerEmail) {

        User owner = userRepository.findByEmail(ownerEmail).orElseThrow();

        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElseThrow();

        boolean alreadyInvited = inviteRepository.existsByEmailAndPharmacyIdAndStatus(email, pharmacyId,
                                                                                            InvitationStatus.PENDING);

        if (alreadyInvited) {
            throw new RuntimeException("Usuário já possui convite pendente");
        }

        String token = UUID.randomUUID().toString();

        Invitation invite = new Invitation(email, token, InvitationStatus.PENDING,
                                            LocalDateTime.now().plusDays(3), pharmacy, owner);

        inviteRepository.save(invite);

        String link = "LINK DO FRONT + token:" + token;

        emailService.sendInviteEmail(email, link, pharmacy.getName());
    }


    @Transactional
    public void acceptInvite(String token, String email) {

        User user = userRepository.findByEmail(email).orElseThrow();

        Invitation invitation = inviteRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Convite inválido"));

        if (invitation.getStatus() != InvitationStatus.PENDING) {
            throw new RuntimeException("Convite já utilizado");
        }

        if (invitation.getExpiration().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("Convite expirado");
        }

        if (!invitation.getEmail().equals(email)) {
            throw new RuntimeException("Este convite não pertence ao usuário");
        }

        Permission permission = new Permission(user, invitation.getPharmacy(), Role.EMPLOYEE);

        permissionRepository.save(permission);

        invitation.setStatus(InvitationStatus.ACCEPTED);

        inviteRepository.save(invitation);
    }

    public List<InviteListDto> list(Long id){
        return invitationMapper.toDtoList(inviteRepository.findAllByPharmacyId(id));
    }

    @Transactional
    public void cancel(Long id){
        Invitation invite = inviteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convite não encontrad0"));

        inviteRepository.delete(invite);
    }

}
