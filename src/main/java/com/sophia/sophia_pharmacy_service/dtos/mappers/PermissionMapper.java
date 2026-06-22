package com.sophia.sophia_pharmacy_service.dtos.mappers;

import com.sophia.sophia_pharmacy_service.dtos.permission.PermissionDto;
import com.sophia.sophia_pharmacy_service.entities.Permission;
import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PermissionMapper {
    @Mapping(target = "pharmacy", source = "pharmacy.name")
    PermissionDto toDto(Permission permission);

    List<PermissionDto> toDtoList(List<Permission> permissionList);
}
