package com.reservei.clientsapi.config.openapi;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                description = "API for Clients of Reservei System",
                title = "Reservei Clients API"
        )
)
public class OpenApiConfig {
}
