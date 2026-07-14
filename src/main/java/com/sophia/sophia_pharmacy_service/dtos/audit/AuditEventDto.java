package com.sophia.sophia_pharmacy_service.dtos.audit;

import com.sophia.sophia_pharmacy_service.entities.enums.AuditOperation;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuditEventDto {
    private String service;
    private String entity;
    private Object oldData;
    private Object newData;
    private AuditOperation operation;
    private String changedBy;
    private String occurredAt;
}
