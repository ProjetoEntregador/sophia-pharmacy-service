package com.sophia.sophia_pharmacy_service.dtos.message;

import com.sophia.sophia_pharmacy_service.dtos.localization.NearbyPharmaciesDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessingResponse {

    private String jid;
    private List<NearbyPharmaciesDto> pharmacies;
}