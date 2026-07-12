package com.sophia.sophia_pharmacy_service.medication;

import com.sophia.sophia_pharmacy_service.dtos.medication.MedicationDto;
import com.sophia.sophia_pharmacy_service.dtos.medication.MedicationPageDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.Collections;
import java.util.List;

@Slf4j
@Service
public class MedicationClient {

    @Autowired
    WebClient webClient;

    @Value("${services.medication.url}")
    private String medicationServiceUrl;

    public List<MedicationDto> findByPharmacy(Long pharmacyId) {

        try {

            MedicationPageDto page = webClient.get()
                    .uri(medicationServiceUrl + "/medications/pharmacy/" + pharmacyId)
                    .retrieve()
                    .bodyToMono(MedicationPageDto.class)
                    .timeout(Duration.ofSeconds(3))
                    .block();

            if (page == null || page.getItems() == null) {
                return Collections.emptyList();
            }

            return page.getItems();

        } catch (Exception e) {
            log.error("Error fetching medications for pharmacy {}", pharmacyId, e);

            return Collections.emptyList();
        }
    }
}