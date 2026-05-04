package com.sophia.sophia_pharmacy_service.services;

import com.sophia.sophia_pharmacy_service.dtos.mappers.PharmacyMapper;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyEntryDto;
import com.sophia.sophia_pharmacy_service.entities.Permission;
import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import com.sophia.sophia_pharmacy_service.entities.User;
import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import com.sophia.sophia_pharmacy_service.repositories.PermissionRepository;
import com.sophia.sophia_pharmacy_service.repositories.PharmacyRepository;
import com.sophia.sophia_pharmacy_service.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PharmacyService {

    @Autowired
    PharmacyRepository pharmacyRepository;

    @Autowired
    PermissionRepository permissionRepository;

    @Autowired
    UserRepository userRepository;

    @Autowired
    PharmacyMapper pharmacyMapper;


    public void validatePermission(Long pharmacyId, String email) {
        Permission permission = permissionRepository
                .findByUserEmailAndPharmacyId(email, pharmacyId)
                .orElseThrow(() -> new RuntimeException("Sem acesso"));
    }

    @Transactional
    public void create(PharmacyEntryDto dto, String email){
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não registrado"));

        Pharmacy newPharmacy = pharmacyMapper.toEntity(dto);

        Permission  newPermission = new Permission();
        newPermission.setPharmacy(pharmacyRepository.save(newPharmacy));
        newPermission.setUser(owner);
        newPermission.setRole(Role.OWNER);

        permissionRepository.save(newPermission);
    }




}
