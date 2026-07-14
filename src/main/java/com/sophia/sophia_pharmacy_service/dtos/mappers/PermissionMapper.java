package com.sophia.sophia_pharmacy_service.dtos.mappers;

import com.sophia.sophia_pharmacy_service.dtos.audit.PermissionAuditDto;
import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionCheckDto;
import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionListDto;
import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionUserDto;
import com.sophia.sophia_pharmacy_service.entities.Permission;
import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {
    @Mapping(target = "pharmacy", source = "pharmacy.name")
    PermissionUserDto toUserInfoDto(Permission permission);

    List<PermissionUserDto> toUserInfoDtoList(List<Permission> permissionList);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    PermissionListDto toDto(Permission permission);

    List<PermissionListDto> toDtoList(List<Permission> permissionList);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "pharmacyId", source = "pharmacy.id")
    PermissionCheckDto toCheckDto(Permission permission);

    @Mapping(target = "userId", source = "user.id")
    @Mapping(target = "pharmacyId", source = "pharmacy.id")
    PermissionAuditDto toAudit(Permission permission);
}
