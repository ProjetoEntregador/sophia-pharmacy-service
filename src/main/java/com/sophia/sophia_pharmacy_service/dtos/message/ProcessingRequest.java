package com.sophia.sophia_pharmacy_service.dtos.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingRequest {

    private String jobId;
    private Double latitude;
    private Double longitude;
    private Double radiusKm;
}