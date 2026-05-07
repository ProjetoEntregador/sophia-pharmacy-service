package com.sophia.sophia_pharmacy_service.controllers;

import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyDetailDto;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyEntryDto;
import com.sophia.sophia_pharmacy_service.dtos.pharmacy.PharmacyListDto;
import com.sophia.sophia_pharmacy_service.dtos.response.ApiResponseDto;
import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import com.sophia.sophia_pharmacy_service.services.PharmacyService;
import com.sophia.sophia_pharmacy_service.validation.CheckPharmacyPermission;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pharmacy")
public class PharmacyController {

    @Autowired
    PharmacyService pharmacyService;


    @GetMapping("/list")
    public ResponseEntity<ApiResponseDto<List<PharmacyListDto>>> getPharmacies(@AuthenticationPrincipal String email){

        List<PharmacyListDto> pharmacies = pharmacyService.findAll(email);

        ApiResponseDto<List<PharmacyListDto>> response = new ApiResponseDto<>();
        response.setStatus("success");
        response.setMessage("Requisição completada com sucesso");
        response.setData(pharmacies);

        return ResponseEntity.ok(response);
    }


    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<PharmacyDetailDto>> getPharmacyById(@AuthenticationPrincipal String email,
                                                                                @PathVariable Long id ){
        PharmacyDetailDto pharmacy = pharmacyService.findById(id, email);

        ApiResponseDto<PharmacyDetailDto> response = new ApiResponseDto<>();
        response.setStatus("success");
        response.setMessage("Requisição completada com sucesso");
        response.setData(pharmacy);

        return ResponseEntity.ok(response);

    }

    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<PharmacyDetailDto>> createPharmacy(@AuthenticationPrincipal String email,
                                                                            @Valid @RequestBody PharmacyEntryDto dto){
        PharmacyDetailDto pharmacy = pharmacyService.create(dto, email);

        ApiResponseDto<PharmacyDetailDto> response = new ApiResponseDto<>();
        response.setStatus("success");
        response.setMessage("Requisição completada com sucesso");
        response.setData(pharmacy);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @CheckPharmacyPermission(role = Role.OWNER)
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponseDto<PharmacyDetailDto>> updatePharmacy(@AuthenticationPrincipal String email,
                                                                            @PathVariable Long id,
                                                                            @Valid @RequestBody PharmacyEntryDto dto){

        PharmacyDetailDto pharmacy = pharmacyService.update(id, email, dto);

        ApiResponseDto<PharmacyDetailDto> response = new ApiResponseDto<>();
        response.setStatus("success");
        response.setMessage("Requisição completada com sucesso");
        response.setData(pharmacy);

        return ResponseEntity.ok(response);
    }

    @CheckPharmacyPermission(role = Role.OWNER)
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponseDto<Void>> deletePharmacy(@AuthenticationPrincipal String email,
                                                               @PathVariable Long id){

        pharmacyService.delete(id, email);

        ApiResponseDto<Void> response = new ApiResponseDto<>();
        response.setStatus("success");
        response.setMessage("Requisição completada com sucesso");

        return ResponseEntity.ok(response);
    }


}
