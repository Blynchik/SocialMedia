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
                        <p><b>Test credentials:</b></p>
                            <p>email/password:</p>
                            <ul>
                            <li>test@mail.ru/test</li>
                            <li>user@mail.com/user</li>
                            </ul>
                            <p>Приложение поддерживает авторизацию через стандартный ввод логина и пароля, а также через JWT.</p>
                            <p>Для авторизации можно использовать кнопку "Authorize" справа.</p>
                            <p>Меню предоставляет возможность воспользоваться более удобным для вас способом авторизации.</p>
                            <p>Чтобы получить JWT, можно создать нового пользователя с помощью:</p>
                            <p><b>/api/registration</b></p>
                            <p>или использовать пароль и электронную почту, указанные выше, с помощью:</p>
                            <p><b>/api/login</b></p>
                            <p>Полученный токен нужно использовать в меню "Authorize".</p>
                            <p>Токен будет действительным в течение 10 минут. Время действительности токена можно изменить в файле application.properties.</p>
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