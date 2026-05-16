package com.sophia.sophia_pharmacy_service.message;

import com.sophia.sophia_pharmacy_service.dtos.localization.LocalizationDto;
import com.sophia.sophia_pharmacy_service.dtos.localization.NearbyPharmaciesDto;
import com.sophia.sophia_pharmacy_service.dtos.message.ProcessingRequest;
import com.sophia.sophia_pharmacy_service.dtos.message.ProcessingResponse;
import com.sophia.sophia_pharmacy_service.services.PharmacyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PharmacyOrchestratorService {

    @Autowired
    PharmacyService pharmacyService;

    public ProcessingResponse process(ProcessingRequest request) {

        LocalizationDto localization = new LocalizationDto(request.getLatitude(), request.getLongitude(), request.getRadiusKm());

        List<NearbyPharmaciesDto> pharmacies = pharmacyService.findNearby(localization);

        return new ProcessingResponse(request.getJobId(),"SUCCESS", pharmacies);
    }
}
