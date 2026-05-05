package com.sophia.sophia_pharmacy_service.dtos.mappers;

import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyDetailDto;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyEntryDto;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyListDto;
import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PharmacyMapper {

    Pharmacy toEntity(PharmacyEntryDto dto);

    List<PharmacyListDto> toDtoList(List<Pharmacy> pharmacyList);

    PharmacyDetailDto toDtoDetail(Pharmacy pharmacy);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromDto(PharmacyEntryDto dto, @MappingTarget Pharmacy pharmacy);

}
