package com.sophia.sophia_pharmacy_service.repositories;

import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
}
