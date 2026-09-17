package com.server.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.models.GroupedOpenApi;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ITI Admission Management System API")
                        .version("1.0")
                        .description(
                            "REST APIs for ITI Admission Management System")
                        .contact(new Contact()
                                .name("ITI Admission Team")
                                .email("support@example.com"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("http://springdoc.org")));
    }

    // ── Auth ─────────────────────────────────────────────────────────────
    @Bean
    public GroupedOpenApi authGroup() {
        return GroupedOpenApi.builder()
                .group("Auth")
                .pathsToMatch("/api/auth/**")
                .build();
    }

    // ── Admission Process ─────────────────────────────────────────────────
    // varshitha's admission flow: /admission/* endpoints
    @Bean
    public GroupedOpenApi admissionProcessGroup() {
        return GroupedOpenApi.builder()
                .group("Admission Process")
                .pathsToMatch("/admission/**")
                .build();
    }

    // ── Student ──────────────────────────────────────────────────────────
    // dilli's student module: /api/student/*, /api/admission-phase/*
    @Bean
    public GroupedOpenApi studentGroup() {
        return GroupedOpenApi.builder()
                .group("Student")
                .pathsToMatch("/api/student/**", "/api/admission-phase/**")
                .build();
    }

    // ── Merit & Checklist ─────────────────────────────────────────────────
    // bhavani's merit list + checklist + admission timing + status
    @Bean
    public GroupedOpenApi meritChecklistGroup() {
        return GroupedOpenApi.builder()
                .group("Merit & Checklist")
                .pathsToMatch(
                    "/api/meritlist/**", "/api/checklist/**",
                    "/admission-timings/**", "/api/dsc/**",
                    "/api/status/**")
                .build();
    }

    // ── Labs ─────────────────────────────────────────────────────────────
    // Labs controllers: /api/labs, /placements (labs report),
    // /itilogin (ITI lab entry/report login)
    @Bean
    public GroupedOpenApi labsGroup() {
        return GroupedOpenApi.builder()
                .group("Labs")
                .pathsToMatch("/api/labs/**", "/placements/labs/**",
                               "/placements/iti/labs/**", "/itilogin/**")
                .build();
    }

    // ── Placements & Industry ─────────────────────────────────────────────
    // PlacementsController (/api/placements/...) +
    //   Industry sub-controllers (/api/implant/...) +
    //   MasterDataController (/api/masterdata/...)
    @Bean
    public GroupedOpenApi placementsGroup() {
        return GroupedOpenApi.builder()
                .group("Placements & Industry")
                .pathsToMatch(
                    "/api/placements/**", "/api/masterdata/**",
                    "/api/implant/**")
                .build();
    }

    // ── Reports ───────────────────────────────────────────────────────────
    @Bean
    public GroupedOpenApi reportsGroup() {
        return GroupedOpenApi.builder()
                .group("Reports")
                .pathsToMatch("/api/reports/**")
                .build();
    }

    // ── IT Administration ─────────────────────────────────────────────────
    // Ramya's ITI module: /api/itis, /api/trades, /api/shift-unit-permitted,
    //   /api/districts, /api/designations
    @Bean
    public GroupedOpenApi itAdminGroup() {
        return GroupedOpenApi.builder()
                .group("IT Administration")
                .pathsToMatch(
                    "/api/itis/**", "/api/trades/**",
                    "/api/shift-unit-permitted/**",
                    "/api/districts/**", "/api/designations/**")
                .build();
    }

}
