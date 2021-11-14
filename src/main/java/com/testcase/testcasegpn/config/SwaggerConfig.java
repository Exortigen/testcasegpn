package com.testcase.testcasegpn.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(
                        new Info()
                                .title("RSR Api")
                                .description("REST сервис-адаптер к SOAP веб-сервису")
                                .version("1.0.0")
                                .contact(
                                        new Contact().name("Petrov Ilya & Belov Viktor")
                                )
                );
    }

}