package com.sophia.sophia_pharmacy_service.controllers;

import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionListDto;
import com.sophia.sophia_pharmacy_service.dtos.response.ApiResponseDto;
import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import com.sophia.sophia_pharmacy_service.services.PermissionService;
import com.sophia.sophia_pharmacy_service.validation.CheckPharmacyPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pharmacy/{id}/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @CheckPharmacyPermission(role = Role.OWNER)
    public ResponseEntity<ApiResponseDto<List<PermissionListDto>>> getPermissions(@PathVariable Long id){
        List<PermissionListDto> permissions = permissionService.find(id);

        ApiResponseDto<List<PermissionListDto>> response = new ApiResponseDto<>("success",
                permissions, "Requisição completada com sucesso");

        return ResponseEntity.ok(response);
    }

}
