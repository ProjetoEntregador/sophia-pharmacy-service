package com.sophia.sophia_pharmacy_service.audit;

import com.sophia.sophia_pharmacy_service.entities.enums.AuditOperation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuditEvent {
}
