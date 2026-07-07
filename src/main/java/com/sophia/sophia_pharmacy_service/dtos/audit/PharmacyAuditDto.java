package com.sophia.sophia_pharmacy_service.dtos.audit;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PharmacyAuditDto {
    private Long id;
    private String name;
    private String phone;
    private String address;
    private String city;
    private Double latitude;
    private Double longitude;
}
