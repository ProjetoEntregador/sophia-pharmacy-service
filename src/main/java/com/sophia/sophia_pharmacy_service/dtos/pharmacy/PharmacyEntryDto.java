package com.sophia.sophia_pharmacy_service.dtos.pharmacy;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PharmacyEntryDto {
    private String name;
    private String phone;
    private String address;
    private String city;
    private double latitude;
    private double longitude;
}
