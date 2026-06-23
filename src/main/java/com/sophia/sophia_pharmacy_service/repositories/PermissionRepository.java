package com.sophia.sophia_pharmacy_service.repositories;

import com.sophia.sophia_pharmacy_service.entities.Permission;
import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByUserEmailAndPharmacyId(String email, Long pharmacyId);

    List<Permission> findAllByUserEmail(String email);

    @Query("""
    SELECT p.pharmacy
    FROM Permission p
    WHERE p.user.email = :email
    """)
    Page<Pharmacy> findPharmaciesByUserEmail(String email, Pageable pageable);

    List<Permission> findAllByPharmacyId(Long pharmacyId);
}
