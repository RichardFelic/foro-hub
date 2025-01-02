package com.hub.foro.api.config.springdoc;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;

@Configuration
public class SpringDocConfig {

        private static final String SECURITY_SCHEME_NAME = "bearer-key";
        private static final String SERVER_URL = "https://foro-hub.herokuapp.com";
        private static final String SERVER_DESCRIPTION = "Servidor de producci n";

        @Bean
        public OpenAPI customOpenAPI() {
                return new OpenAPI()
                                .components(components())
                                .info(info())
                                .servers(servers());
        }

        private Components components() {
                return new Components()
                                .addSecuritySchemes(SECURITY_SCHEME_NAME, securityScheme());
        }

        private SecurityScheme securityScheme() {
                return new SecurityScheme()
                                .type(SecurityScheme.Type.HTTP)
                                .scheme("bearer")
                                .bearerFormat("JWT");
        }

        private Info info() {
                return new Info()
                                .title("Foro Hub API")
                                .description("API para sistema de foros, donde los usuarios pueden crear topicos y responderse entre si para resolver dudas o preguntas.")
                                .contact(contact())
                                .license(license());
        }

        private Contact contact() {
                return new Contact()
                                .name("Richard Feliciano")
                                .email("felricharde42@gmail.com");
        }

        private License license() {
                return new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT");
        }

        private List<Server> servers() {
                return List.of(new Server().url(SERVER_URL).description(SERVER_DESCRIPTION));
        }
}

