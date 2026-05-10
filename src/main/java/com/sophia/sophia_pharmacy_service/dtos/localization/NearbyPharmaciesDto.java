package com.sophia.sophia_pharmacy_service.dtos.localization;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

}
