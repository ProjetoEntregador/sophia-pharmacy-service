package com.sophia.sophia_pharmacy_service.dtos.pharmacy;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PharmacyDetailDto {
    private Long id;
    private String name;
    private String phone;
    private String adress;
    private String city;
    private double latitude;
    private double longitude;
}
