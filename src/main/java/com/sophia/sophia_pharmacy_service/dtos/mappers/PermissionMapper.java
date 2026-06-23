package com.sophia.sophia_pharmacy_service.dtos.mappers;

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
    PermissionListDto ToDto(Permission permission);

    List<PermissionListDto> ToDtoList(List<Permission> permissionList);
}
