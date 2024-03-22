package com.yohansdev.To.Do.List.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                contact = @Contact(
                        name = "YohansDev",
                        email = "yohans.dev@gmail.com",
                        url = "https://yohans-dev.netlify.app"
                ),
                description = "API para gerenciamento de tarefas.",
                title = "To Do List - NUVEN",
                version = "1.0"
        ))
public class OpenApiConfig {
}
