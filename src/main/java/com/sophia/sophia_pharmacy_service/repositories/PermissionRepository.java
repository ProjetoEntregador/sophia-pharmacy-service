package com.sophia.sophia_pharmacy_service.repositories;

import com.sophia.sophia_pharmacy_service.entities.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {
    Optional<Permission> findByUserEmailAndPharmacyId(String email, Long pharmacyId);
    List<Permission> findAllByUserEmail(String email);
}
