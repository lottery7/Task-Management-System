package dev.lottery.tms.swagger;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@OpenAPIDefinition(
        info = @Info(
                title = "Task Management System API",
                description = "API for simple Task Management System",
                version = "0.1.0",
                contact = @Contact(
                        name = "Eugene Akimov",
                        email = "flotery@yandex.ru"
                )
        )
)
public class OpenApiConfig {
}
