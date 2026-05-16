package com.sophia.sophia_pharmacy_service.dtos.medication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDto {

    private String id;
    private String name;
    private String dosage;
    private String pharmaceuticalForm;
    private String manufacturer;
    private String description;
    private String stripe;
    private Boolean prescriptionRequired;
    private Double unitPrice;
    private Timestamp createdAt;
}