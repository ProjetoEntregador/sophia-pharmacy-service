package com.sophia.sophia_pharmacy_service.services;

import com.sophia.sophia_pharmacy_service.audit.AuditContext;
import com.sophia.sophia_pharmacy_service.audit.AuditEvent;
import com.sophia.sophia_pharmacy_service.dtos.audit.InvitationAuditDto;
import com.sophia.sophia_pharmacy_service.dtos.audit.PermissionAuditDto;
import com.sophia.sophia_pharmacy_service.dtos.invitation.InviteListDto;
import com.sophia.sophia_pharmacy_service.dtos.mappers.InvitationMapper;
import com.sophia.sophia_pharmacy_service.dtos.mappers.PermissionMapper;
import com.sophia.sophia_pharmacy_service.dtos.pagination.PageResponse;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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

    @Autowired
    private AuditContext auditContext;

    @Autowired
    private PermissionMapper permissionMapper;

    @Transactional
    @AuditEvent
    public InviteListDto invite(Long pharmacyId, String email, String ownerEmail) {

        User owner = userRepository.findByEmail(ownerEmail).orElseThrow();

        Pharmacy pharmacy = pharmacyRepository.findById(pharmacyId).orElseThrow();

        boolean alreadyInvited = inviteRepository.existsByEmailAndPharmacyIdAndStatusIn(
                                    email,
                                    pharmacyId,
                                    List.of(
                                            InvitationStatus.PENDING,
                                            InvitationStatus.ACCEPTED
                                    ));

        if (alreadyInvited) {
            throw new RuntimeException("Usuário já possui convite pendente ou aceito");
        }

        String token = UUID.randomUUID().toString();

        Invitation invite = new Invitation(email, token, InvitationStatus.PENDING,
                                            LocalDateTime.now().plusDays(3), pharmacy, owner);

        InvitationAuditDto inviteAudit = invitationMapper.toAudit(invite);

        auditContext.insert("invitation", inviteAudit);

        emailService.sendInviteEmail(email, token, pharmacy.getName());

        return invitationMapper.toDto(inviteRepository.save(invite));
    }


    @Transactional
    @AuditEvent
    public void acceptInvite(String token, String email) {

        User user = userRepository.findByEmail(email).orElseThrow();

        Invitation invitation = inviteRepository.findByToken(token)
                .orElseThrow(() -> new RuntimeException("Convite inválido"));

        InvitationAuditDto oldInvite = invitationMapper.toAudit(invitation);

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

        PermissionAuditDto newPermission = permissionMapper.toAudit(permission);

        auditContext.insert("permission", newPermission);

        permissionRepository.save(permission);

        invitation.setStatus(InvitationStatus.ACCEPTED);

        InvitationAuditDto newInvite = invitationMapper.toAudit(inviteRepository.save(invitation));

        auditContext.update("permission", oldInvite, newInvite);
    }

    public PageResponse<InviteListDto> list(Long pharmacyId, Integer offset, Integer size) {

        if (size <= 0 || offset < 0) {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }

        int page = offset / size;

        Pageable pageable = PageRequest.of(page, size);

        Page<Invitation> invitationPage = inviteRepository.findAllByPharmacyId(pharmacyId, pageable);

        List<InviteListDto> content = invitationMapper.toDtoList(invitationPage.getContent());

        return new PageResponse<>(
                content,
                invitationPage.getTotalElements(),
                invitationPage.getTotalPages(),
                invitationPage.getSize(),
                invitationPage.getNumber()
        );
    }

    @Transactional
    @AuditEvent
    public void cancel(Long id){
        Invitation invite = inviteRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Convite não encontrad0"));

        if (invite.getStatus() == InvitationStatus.ACCEPTED){
            Permission permission = permissionRepository.findByUserEmailAndPharmacyId(
                    invite.getEmail(),
                    invite.getPharmacy().getId()
            ).orElseThrow(() -> new RuntimeException("Permissão não encontrada"));

            PermissionAuditDto oldPermission = permissionMapper.toAudit(permission);

            auditContext.delete("permission", oldPermission);

            permissionRepository.delete(permission);
        }

        InvitationAuditDto inviteAudit = invitationMapper.toAudit(inviteRepository.save(invite));

        auditContext.delete("permission", inviteAudit);

        inviteRepository.delete(invite);
    }

}
