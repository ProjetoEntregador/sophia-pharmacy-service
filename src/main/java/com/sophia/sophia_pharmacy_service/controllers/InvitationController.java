package com.sophia.sophia_pharmacy_service.controllers;

import com.sophia.sophia_pharmacy_service.dtos.invitation.InviteAcceptDto;
import com.sophia.sophia_pharmacy_service.dtos.invitation.InviteDto;
import com.sophia.sophia_pharmacy_service.dtos.invitation.InviteListDto;
import com.sophia.sophia_pharmacy_service.dtos.pagination.PageResponse;
import com.sophia.sophia_pharmacy_service.dtos.response.ApiResponseDto;
import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import com.sophia.sophia_pharmacy_service.services.InvitationService;
import com.sophia.sophia_pharmacy_service.validation.CheckPharmacyPermission;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/invites")
public class InvitationController {

    @Autowired
    private InvitationService inviteService;

    @PostMapping("/pharmacy/{id}/send")
    @CheckPharmacyPermission(role = Role.OWNER)
    public ResponseEntity<ApiResponseDto<Void>> invite(@PathVariable Long id, @RequestBody InviteDto dto,
                                        @AuthenticationPrincipal String ownerEmail) {

        inviteService.invite(id, dto.getEmail(), ownerEmail);

        ApiResponseDto<Void> response = new ApiResponseDto<>("success",
                null, "Email enviado com sucesso");

        return ResponseEntity.ok(response);
    }


    @PostMapping("/accept")
    public ResponseEntity<ApiResponseDto<Void>> accept(@RequestBody InviteAcceptDto request,
                                        @AuthenticationPrincipal String email) {

        inviteService.acceptInvite(request.getToken(), email);

        ApiResponseDto<Void> response = new ApiResponseDto<>("success",
                null, "Convite aceito com sucesso");

        return ResponseEntity.ok(response);
    }

    @GetMapping("pharmacy/{id}/list")
    @CheckPharmacyPermission(role = Role.OWNER)
    public ResponseEntity<ApiResponseDto<PageResponse<InviteListDto>>> getInvites(@PathVariable Long id,
                                                            @RequestParam(defaultValue = "0") Integer offset,
                                                            @RequestParam(defaultValue = "10") Integer size){

        PageResponse<InviteListDto> invites = inviteService.list(id, offset, size);

        ApiResponseDto<PageResponse<InviteListDto>> response = new ApiResponseDto<>("success",
                invites, "Requisição completda com sucesso");

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("pharmacy/{id}/cancel/{inviteId}")
    @CheckPharmacyPermission(role = Role.OWNER)
    public ResponseEntity<ApiResponseDto<Void>> deleteInvite(@PathVariable Long id, @PathVariable Long inviteId ){
        inviteService.cancel(inviteId);

        ApiResponseDto<Void> response = new ApiResponseDto<>("success",
                null, "Convite cancelado com sucesso");

        return ResponseEntity.ok(response);
    }

}
