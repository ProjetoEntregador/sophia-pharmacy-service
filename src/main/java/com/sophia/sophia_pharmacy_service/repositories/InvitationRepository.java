package com.sophia.sophia_pharmacy_service.repositories;

import com.sophia.sophia_pharmacy_service.entities.Invitation;
import com.sophia.sophia_pharmacy_service.entities.enums.InvitationStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Long> {

    Optional<Invitation> findByToken(String token);

    boolean existsByEmailAndPharmacyIdAndStatus(String email, Long pharmacyId, InvitationStatus status);

    Page<Invitation> findAllByPharmacyId(Long pharmacyId, Pageable pageable);
}