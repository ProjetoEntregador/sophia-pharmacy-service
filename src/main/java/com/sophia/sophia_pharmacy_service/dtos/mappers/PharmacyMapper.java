package com.sophia.sophia_pharmacy_service.dtos.mappers;

import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyEntryDto;
import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PharmacyMapper {

    Pharmacy toEntity(PharmacyEntryDto dto);

}
