package com.sophia.sophia_pharmacy_service.dtos.pharmacy;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PharmacyListDto {
    private Long id;
    private String name;
    private String phone;
}
