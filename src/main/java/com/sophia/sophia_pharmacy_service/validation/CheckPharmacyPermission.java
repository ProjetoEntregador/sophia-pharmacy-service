package com.sophia.sophia_pharmacy_service.validation;

import com.sophia.sophia_pharmacy_service.entities.enums.Role;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface CheckPharmacyPermission {

    Role role();

    String pharmacyIdParam() default "id";
}
