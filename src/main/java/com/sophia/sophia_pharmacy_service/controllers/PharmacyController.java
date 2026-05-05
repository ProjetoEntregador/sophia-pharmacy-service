package com.sophia.sophia_pharmacy_service.controllers;

import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyDetailDto;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyEntryDto;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyListDto;
import com.sophia.sophia_pharmacy_service.dtos.response.ApiResponseDto;
import com.sophia.sophia_pharmacy_service.services.PharmacyService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy")
public class PharmacyController {

    @Autowired
    PharmacyService pharmacyService;


    @GetMapping("/list")
    public ResponseEntity<ApiResponseDto<List<PharmacyListDto>>> getPharmacies(@AuthenticationPrincipal UserDetails userDetails){

        String email = userDetails.getUsername();

        List<PharmacyListDto> pharmacies = pharmacyService.findAll(email);

        ApiResponseDto<List<PharmacyListDto>> response = new ApiResponseDto<>();
        response.setStatus("success");
        response.setMessage("Requisição completada com sucesso");
        response.setData(pharmacies);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<PharmacyDetailDto>> getPharmacyById(@AuthenticationPrincipal UserDetails userDetails,
                                                                                @PathVariable Long id ){
        String email = userDetails.getUsername();

        PharmacyDetailDto pharmacy = pharmacyService.findById(id, email);

        ApiResponseDto<PharmacyDetailDto> response = new ApiResponseDto<>();
        response.setStatus("success");
        response.setMessage("Requisição completada com sucesso");
        response.setData(pharmacy);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<PharmacyDetailDto>> createPharmacy(@AuthenticationPrincipal UserDetails userDetails,
                                                                            @Valid @RequestBody PharmacyEntryDto dto){
        String email = userDetails.getUsername();

        PharmacyDetailDto pharmacy = pharmacyService.create(dto, email);

        ApiResponseDto<PharmacyDetailDto> response = new ApiResponseDto<>();
        response.setStatus("success");
        response.setMessage("Requisição completada com sucesso");
        response.setData(pharmacy);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<PharmacyDetailDto>> updatePharmacy(@AuthenticationPrincipal UserDetails userDetails,
                                                                            @PathVariable Long id,
                                                                            @Valid @RequestBody PharmacyEntryDto dto){
        String email = userDetails.getUsername();

        PharmacyDetailDto pharmacy = pharmacyService.update(id, email, dto);

        ApiResponseDto<PharmacyDetailDto> response = new ApiResponseDto<>();
        response.setStatus("success");
        response.setMessage("Requisição completada com sucesso");
        response.setData(pharmacy);

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deletePharmacy(@AuthenticationPrincipal UserDetails userDetails,
                                                               @PathVariable Long id){
        String email = userDetails.getUsername();

        pharmacyService.delete(id, email);

        ApiResponseDto<Void> response = new ApiResponseDto<>();
        response.setStatus("success");
        response.setMessage("Requisição completada com sucesso");

        return ResponseEntity.ok(response);
    }


}
