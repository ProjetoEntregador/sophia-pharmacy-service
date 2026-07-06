package com.sophia.sophia_pharmacy_service.audit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sophia.sophia_pharmacy_service.auth.JwtUtil;
import com.sophia.sophia_pharmacy_service.dtos.audit.AuditEventDto;
import com.sophia.sophia_pharmacy_service.message.ProcessingPublisher;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

@Aspect
@Component
@RequiredArgsConstructor
@Slf4j
public class AuditAspect {

    private final JwtUtil jwtUtil;
    private final AuditContext auditContext;
    private final ProcessingPublisher eventPublisher;
    private final ObjectMapper objectMapper;

    @Around("@annotation(auditEvent)")
    public Object audit(ProceedingJoinPoint joinPoint, AuditEvent auditEvent) throws Throwable {
        try {
            Object result = joinPoint.proceed();

            for (AuditEventDto event : auditContext.getEvents()) {

                event.setService("pharmacy");
                event.setChangedBy(jwtUtil.getAuthenticatedUserId().orElse(null));
                event.setOccurredAt(DateTimeFormatter.ISO_INSTANT.format(Instant.now()));

                log.info("Evento enviado: {}", event);

                eventPublisher.publishAudit(event);
            }

            return result;

        }finally {
            auditContext.clear();
        }
    }

}
