package com.sophia.sophia_pharmacy_service.dtos.projections;

public interface NearbyPharmaciesProjection {
    Long getId();

    String getName();

    String getPhone();

    String getAddress();

    String getCity();

    Double getDistanceKm();
}
