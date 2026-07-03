package com.sophia.sophia_pharmacy_service.controllers;

import com.sophia.sophia_pharmacy_service.dtos.pagination.PageResponse;
import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionCheckDto;
import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionListDto;
import com.sophia.sophia_pharmacy_service.dtos.response.ApiResponseDto;
import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import com.sophia.sophia_pharmacy_service.services.PermissionService;
import com.sophia.sophia_pharmacy_service.validation.CheckPharmacyPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pharmacy/{id}/permissions")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    @GetMapping
    @CheckPharmacyPermission(role = Role.OWNER)
    public ResponseEntity<ApiResponseDto<PageResponse<PermissionListDto>>> getPermissions(@PathVariable Long id,
                                                                    @RequestParam(defaultValue = "0") Integer offset,
                                                                    @RequestParam(defaultValue = "10") Integer size){

        PageResponse<PermissionListDto> permissions = permissionService.find(id, offset, size);

        ApiResponseDto<PageResponse<PermissionListDto>> response = new ApiResponseDto<>("success",
                permissions, "Requisição completada com sucesso");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{permissionId}/remove")
    @CheckPharmacyPermission(role = Role.OWNER)
    public ResponseEntity<ApiResponseDto<Void>> removePermission(@PathVariable Long permissionId, @PathVariable Long id){
        permissionService.delete(permissionId);

        ApiResponseDto<Void> response = new ApiResponseDto<>("success",
                null, "Permissão removida com sucesso");

        return ResponseEntity.ok(response);
    }


    @GetMapping("/check")
    @CheckPharmacyPermission(role = {Role.EMPLOYEE, Role.OWNER})
    public ResponseEntity<ApiResponseDto<PermissionCheckDto>> checkPermission(@PathVariable Long id,
                                                                              @AuthenticationPrincipal String email){

        PermissionCheckDto permission = permissionService.check(id, email);

        ApiResponseDto<PermissionCheckDto> response = new ApiResponseDto<>("success",
                permission, "Permissão verificada");

        return ResponseEntity.ok(response);
    }

}
