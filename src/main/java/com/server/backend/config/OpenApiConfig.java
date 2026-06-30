package com.server.backend.config;
import java.util.Comparator;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerMethod;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ITI Admission Management System API")
                        .version("1.0")
                        .description("REST APIs for ITI Admission Management System")
                        .contact(new Contact()
                                .name("ITI Admission Team")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

    @Bean
    public GroupedOpenApi reportsApi() {
        return GroupedOpenApi.builder()
                .group("reports")
                .pathsToMatch("/api/reports/**")
                .build();
    }

    @Bean
    public org.springdoc.core.customizers.OperationCustomizer operationCustomizer() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            // Extract number from summary for sorting (e.g., "1 - API Dashboard" -> 1)
            String summary = operation.getSummary();
            if (summary != null && !summary.isEmpty()) {
                try {
                    int num = Integer.parseInt(summary.split(" - ")[0]);
                    operation.addExtension("x-order", num);
                } catch (Exception e) {
                    // If no number found, assign a high number to sort at the end
                    operation.addExtension("x-order", 999);
                }
            }
            return operation;
        };
    }

    @Bean
    public Comparator<io.swagger.v3.oas.models.PathItem.HttpMethod> operationSorter() {
        return (method1, method2) -> {
            // Sort by HTTP method: GET first, then others
            if (method1 == method2) return 0;
            if (method1 == io.swagger.v3.oas.models.PathItem.HttpMethod.GET) return -1;
            if (method2 == io.swagger.v3.oas.models.PathItem.HttpMethod.GET) return 1;
            return method1.name().compareTo(method2.name());
        };
    }
}
