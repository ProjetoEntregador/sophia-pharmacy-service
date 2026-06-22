package com.sophia.sophia_pharmacy_service.dtos.mappers;

import com.sophia.sophia_pharmacy_service.dtos.invitation.InviteListDto;
import com.sophia.sophia_pharmacy_service.entities.Invitation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)

public interface InvitationMapper {
    @Mapping(target = "invitedBy", source = "invitedBy.username")
    InviteListDto toDto(Invitation invite);

    List<InviteListDto> toDtoList(List<Invitation> invites);

    default String mapExpiration(LocalDateTime expiration) {
        return expiration != null ? expiration.format(DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm")) : null;
    }
}
