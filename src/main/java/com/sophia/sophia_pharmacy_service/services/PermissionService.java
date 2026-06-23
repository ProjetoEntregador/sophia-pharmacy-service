package com.sophia.sophia_pharmacy_service.services;

import com.sophia.sophia_pharmacy_service.dtos.mappers.PermissionMapper;
import com.sophia.sophia_pharmacy_service.dtos.pagination.PageResponse;
import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionListDto;
import com.sophia.sophia_pharmacy_service.entities.Permission;
import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import com.sophia.sophia_pharmacy_service.repositories.PermissionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PermissionService {

    @Autowired
    private PermissionRepository permissionRepository;

    @Autowired
    private PermissionMapper permissionMapper;

    @Transactional
    public PageResponse<PermissionListDto> find(Long id, Integer offset, Integer size){
        if (size <= 0 || offset < 0) {
            throw new IllegalArgumentException("Parâmetros inválidos");
        }

        int page = offset / size;

        Pageable pageable = PageRequest.of(page, size);

        Page<Permission> permissionPage = permissionRepository.findAllByPharmacyId(id, pageable);

        List<PermissionListDto> content = permissionMapper.ToDtoList(permissionPage.getContent());

        return new PageResponse<>(
                content,
                permissionPage.getTotalElements(),
                permissionPage.getTotalPages(),
                permissionPage.getNumber(),
                permissionPage.getSize()
        );
    }

    @Transactional
    public void delete(Long id){
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Permissão não encontrada"));

        if (permission.getRole() == Role.OWNER){throw new RuntimeException("Não é possível remover o proprietário");}

        permissionRepository.delete(permission);

    }
}
