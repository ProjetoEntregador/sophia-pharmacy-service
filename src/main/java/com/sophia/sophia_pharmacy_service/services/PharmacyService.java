package com.sophia.sophia_pharmacy_service.services;

import com.sophia.sophia_pharmacy_service.dtos.mappers.PharmacyMapper;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyDetailDto;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyEntryDto;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyListDto;
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

import java.util.List;

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


    @Transactional
    public List<PharmacyListDto> findAll(String email){
        List<Pharmacy> pharmacies = permissionRepository.findAllByUserEmail(email)
                .stream().map(Permission::getPharmacy).toList();

        return pharmacyMapper.toDtoList(pharmacies);
    }


    public PharmacyDetailDto findById(Long id, String email){
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));

        Permission permission = permissionRepository
                .findByUserEmailAndPharmacyId(email, id)
                .orElseThrow(() -> new RuntimeException("Usuário não possui acesso a essa farmácia"));

        return pharmacyMapper.toDtoDetail(pharmacy);
    }


    @Transactional
    public PharmacyDetailDto create(PharmacyEntryDto dto, String email){
        User owner = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario não registrado"));

        Pharmacy newPharmacy = pharmacyMapper.toEntity(dto);

        Permission  newPermission = new Permission();
        newPermission.setPharmacy(pharmacyRepository.save(newPharmacy));
        newPermission.setUser(owner);
        newPermission.setRole(Role.OWNER);

        permissionRepository.save(newPermission);

        return pharmacyMapper.toDtoDetail(pharmacyRepository.save(newPharmacy));
    }

    @Transactional
    public PharmacyDetailDto update(Long id, String email, PharmacyEntryDto dto){
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));

        pharmacyMapper.updateFromDto(dto, pharmacy);

        return pharmacyMapper.toDtoDetail(pharmacyRepository.save(pharmacy));
    }

    @Transactional
    public void delete(Long id, String email){
        Pharmacy pharmacy = pharmacyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Farmácia não encontrada"));

        List<Permission> permissions = permissionRepository.findAllByPharmacyId(id);

        permissionRepository.deleteAll(permissions);

        pharmacyRepository.delete(pharmacy);
    }

}
