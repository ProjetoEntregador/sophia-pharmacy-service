package com.sophia.sophia_pharmacy_service.dtos.localization;

import com.sophia.sophia_pharmacy_service.dtos.medication.MedicationDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class NearbyPharmaciesDto {

    private Long id;
    private String name;
    private String phone;
    private String address;
    private String city;
    private Double distanceKm;
    private List<MedicationDto> medications;
}
