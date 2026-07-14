package com.sophia.sophia_pharmacy_service.repositories;

import com.sophia.sophia_pharmacy_service.dtos.localization.NearbyPharmaciesDto;
import com.sophia.sophia_pharmacy_service.dtos.projections.NearbyPharmaciesProjection;
import com.sophia.sophia_pharmacy_service.entities.Pharmacy;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface PharmacyRepository extends JpaRepository<Pharmacy, Long> {
    @Query(value = """
    SELECT *
    FROM (
        SELECT p.id, p.name, p.address,p.city, p.phone,
            (
                6371 * acos(
                    cos(radians(:latitude))
                    * cos(radians(p.latitude))
                    * cos(radians(p.longitude) - radians(:longitude))
                    + sin(radians(:latitude))
                    * sin(radians(p.latitude))
                )
            ) AS distanceKm
        FROM tb_pharmacies p
    ) AS nearby
    WHERE nearby.distanceKm <= :radius
    ORDER BY nearby.distanceKm
    """,
            nativeQuery = true)
    List<NearbyPharmaciesProjection> findNearbyPharmacies(
            @Param("latitude") Double latitude,
            @Param("longitude") Double longitude,
            @Param("radius") Double radius
    );

}
