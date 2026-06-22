package com.sophia.sophia_pharmacy_service.auth;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Getter
@Setter
public class CorsProperties {

    @Value("${cors.allowed-origins}")
    private String allowedOrigins;
}
