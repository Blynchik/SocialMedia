package com.project.socialMedia.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@SecurityScheme(
        name = "bearerAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "bearer",
        bearerFormat = "JWT",
        in = SecuritySchemeIn.HEADER,
        paramName = "Authorization"
)
@SecurityScheme(
        name = "basicAuth",
        type = SecuritySchemeType.HTTP,
        scheme = "basic"
)
@OpenAPIDefinition(
        info = @Info(
                title = "Social Media API documentation",
                version = "1.0",
                description = """
                        <p><b>Test credentials:</b>
                        <br>
                        email/password
                        <br>
                        test@mail.ru/test
                        <br>
                        user@mail.com/user
                        <br>
                        """,
                contact = @Contact(url = "https://github.com/Blynchik", name = "Vadim Sovetnikov", email = "vadimsovetnikov@mail.ru")
        ),
        security = {
                @SecurityRequirement(name = "bearerAuth"),
                @SecurityRequirement(name = "basicAuth")
        }
)
public class OpenAPIConfig {

    @Bean
    public GroupedOpenApi api() {
        return GroupedOpenApi.builder()
                .group("REST API")
                .pathsToMatch("/api/**")
                .build();
    }
}