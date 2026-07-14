package com.sophia.sophia_pharmacy_service.services;

import com.sophia.sophia_pharmacy_service.audit.AuditContext;
import com.sophia.sophia_pharmacy_service.audit.AuditEvent;
import com.sophia.sophia_pharmacy_service.dtos.audit.PermissionAuditDto;
import com.sophia.sophia_pharmacy_service.dtos.audit.PharmacyAuditDto;
import com.sophia.sophia_pharmacy_service.dtos.localization.LocalizationDto;
import com.sophia.sophia_pharmacy_service.dtos.localization.NearbyPharmaciesDto;
import com.sophia.sophia_pharmacy_service.dtos.mappers.InvitationMapper;
import com.sophia.sophia_pharmacy_service.dtos.mappers.PermissionMapper;
import com.sophia.sophia_pharmacy_service.dtos.mappers.PharmacyMapper;
import com.sophia.sophia_pharmacy_service.dtos.medication.MedicationDto;
import com.sophia.sophia_pharmacy_service.dtos.pagination.PageResponse;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyDetailDto;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyEntryDto;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyListDto;
import com.sophia.sophia_pharmacy_service.dtos.projections.NearbyPharmaciesProjection;
import com.sophia.sophia_pharmacy_service.entities.Invitation;
import com.sophia.sophia_pharmacy_service.entities.Permission;
import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import com.sophia.sophia_pharmacy_service.entities.User;
import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import com.sophia.sophia_pharmacy_service.medication.MedicationClient;
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

import java.util.List;

@Service
public class PharmacyService {

    @Autowired
    MedicationClient medicationClient;

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PharmacyMapper pharmacyMapper;

    @Autowired
    AuditContext auditContext;

    @Autowired
    private PermissionMapper permissionMapper;

    @Autowired
    private InvitationRepository invitationRepository;
    @Autowired
    private InvitationMapper invitationMapper;

    @Transactional
    public PageResponse<PharmacyListDto> findAll(String email, Integer offset, Integer size){
        if (size <= 0 || offset < 0) {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }

        int pageNumber = offset / size;

        Pageable pageable = PageRequest.of(pageNumber, size);


        Page<Pharmacy> page = permissionRepository.findPharmaciesByUserEmail(email, pageable);

        return new PageResponse<>(
                pharmacyMapper.toDtoList(page.getContent()),
                page.getTotalElements(),
                page.getTotalPages(),
                page.getSize(),
                page.getNumber()
        );
    }

    @Transactional
    public PharmacyDetailDto findById(Long id, String email){
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));

        Permission permission = permissionRepository
                .findByUserEmailAndPharmacyId(email, id)
                .orElseThrow(() -> new RuntimeException("Usuário não possui acesso a essa farmácia"));

        return pharmacyMapper.toDtoDetail(pharmacy);
    }


    @Transactional
    @AuditEvent
    public PharmacyDetailDto create(PharmacyEntryDto dto, String email){
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não registrado"));

        Pharmacy newPharmacy = pharmacyMapper.toEntity(dto);

        pharmacyRepository.save(newPharmacy);

        Permission newPermission = new Permission(owner, newPharmacy, Role.OWNER);

        permissionRepository.save(newPermission);

        PharmacyAuditDto auditPharmacy = pharmacyMapper.toAudit(newPharmacy);
        PermissionAuditDto auditPermission = permissionMapper.toAudit(newPermission);

        auditContext.insert("pharmacy", auditPharmacy);
        auditContext.insert("permission", auditPermission);

        return pharmacyMapper.toDtoDetail(newPharmacy);
    }

    @Transactional
    @AuditEvent
    public PharmacyDetailDto update(Long id, String email, PharmacyEntryDto dto){
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));

        PharmacyAuditDto oldPharmacy = pharmacyMapper.toAudit(pharmacy);

        pharmacyMapper.updateFromDto(dto, pharmacy);

        PharmacyAuditDto newPharmacy = pharmacyMapper.toAudit(pharmacyRepository.save(pharmacy));

        auditContext.update("pharmacy", oldPharmacy, newPharmacy);

        return pharmacyMapper.toDtoDetail(pharmacy);
    }

    @Transactional
    @AuditEvent
    public void delete(Long id, String email){
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));

        PharmacyAuditDto oldPharmacy = pharmacyMapper.toAudit(pharmacy);

        auditContext.delete("pharmacy", oldPharmacy);

        List<Permission> permissions = permissionRepository.findAllByPharmacyId(id);

        List<Invitation> invites = invitationRepository.findAllByPharmacyId(id);

        for (Permission permission : permissions) {
            auditContext.delete("permission", permissionMapper.toAudit(permission));
        }

        for (Invitation invite : invites) {
            auditContext.delete("invitation", invitationMapper.toAudit(invite));
        }

        permissionRepository.deleteAll(permissions);

        invitationRepository.deleteAll(invites);

        pharmacyRepository.delete(pharmacy);
    }

    @Transactional
    public List<NearbyPharmaciesDto> findNearby(LocalizationDto dto){

             return pharmacyRepository.findNearbyPharmacies(dto.getLatitude(),dto.getLongitude(), dto.getRadiusKm())
                     .stream().map(this::mapToDto).toList();
    }

    private NearbyPharmaciesDto mapToDto(NearbyPharmaciesProjection projection) {

        List<MedicationDto> medications = medicationClient.findByPharmacy(projection.getId());

        return new NearbyPharmaciesDto(
                projection.getId(),
                projection.getName(),
                projection.getPhone(),
                projection.getAddress(),
                projection.getCity(),
                Math.round(projection.getDistanceKm() * 100.0) / 100.0,
                medications
        );
    }

}
