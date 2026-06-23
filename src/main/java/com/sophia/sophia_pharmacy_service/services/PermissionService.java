package com.sophia.sophia_pharmacy_service.services;

import com.sophia.sophia_pharmacy_service.dtos.mappers.PermissionMapper;
import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionListDto;
import com.sophia.sophia_pharmacy_service.entities.Permission;
import com.sophia.sophia_pharmacy_service.repositories.PermissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Transactional
    public List<PermissionListDto> find(Long id){
        List<Permission> permissions = permissionRepository.findAllByPharmacyId(id);

        return permissionMapper.ToDtoList(permissions);
    }
}
