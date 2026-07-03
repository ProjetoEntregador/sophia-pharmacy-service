package com.sophia.sophia_pharmacy_service.validation;

import com.sophia.sophia_pharmacy_service.entities.Permission;
import com.sophia.sophia_pharmacy_service.entities.enums.Role;
import com.sophia.sophia_pharmacy_service.exceptions.AccessDeniedException;
import com.sophia.sophia_pharmacy_service.repositories.PermissionRepository;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
public class PharmacyPermissionAspect {

    private final PermissionRepository permissionRepository;

    public PharmacyPermissionAspect(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    @Before("@annotation(checkPermission)")
    public void check(JoinPoint joinPoint, CheckPharmacyPermission checkPermission) {

        String email = SecurityContextHolder.getContext()
                .getAuthentication()
                .getName();

        Object[] args = joinPoint.getArgs();
        String paramName = checkPermission.pharmacyIdParam();

        Long pharmacyId = extractPharmacyId(joinPoint, paramName);

        Permission permission = permissionRepository
                .findByUserEmailAndPharmacyId(email, pharmacyId)
                .orElseThrow(() -> new AccessDeniedException("Usuário não possui acesso a essa farmácia"));

        Role[] allowedRoles = checkPermission.role();

        boolean authorized = Arrays.stream(allowedRoles)
                .anyMatch(role -> role == permission.getRole());

        if (!authorized) {
            throw new AccessDeniedException("Usuário não possui nível de acesso para este recurso");
        }
    }

    private Long extractPharmacyId(JoinPoint joinPoint, String paramName) {

        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] parameterNames = signature.getParameterNames();
        Object[] args = joinPoint.getArgs();

        for (int i = 0; i < parameterNames.length; i++) {
            if (parameterNames[i].equals(paramName)) {
                return (Long) args[i];
            }
        }

        throw new AccessDeniedException("Parâmetro de Id da farmácia não encontrado");
    }
}
