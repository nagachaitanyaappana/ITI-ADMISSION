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
    // varshitha's admission flow (19 controllers).
    //   /admission/**              -> the 16 /admission/* controllers
    //   /api/iti/**                -> itinames_by_govt_pvt_controller
    //   /candidate-selected-trade/** -> candidate_selected_trade_controller
    //   /api/trades/by-minqual/**  -> trdnms_by_minqual_controller
    //     (shares the /api/trades base path with the ITI module, so the ITI
    //      group excludes this one pattern - see itiGroup below)
    @Bean
    public GroupedOpenApi admissionProcessGroup() {
        return GroupedOpenApi.builder()
                .group("Admission Process")
                .pathsToMatch("/admission/**", "/api/iti/**",
                               "/candidate-selected-trade/**",
                               "/api/trades/by-minqual/**")
                .build();
    }

    // ── Student ─────────────────────────────────────────────────────────
    // dilli's student module + the reference data the student form needs:
    //   /api/student/**        -> StudentApplication, StudentCandMarks,
    //                             CasteController (caste-subcaste lookup)
    //   /api/admission-phase/**-> AdmissionPhaseController
    //   /api/master/**         -> StateController (state dropdown)
    @Bean
    public GroupedOpenApi studentGroup() {
        return GroupedOpenApi.builder()
                .group("Student")
                .pathsToMatch("/api/student/**", "/api/admission-phase/**",
                               "/api/master/**")
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
    //   /api/labs/**           -> LabsController
    //   /placements/labs-report-> LabsReportController
    //   /itilogin/**           -> ItiLabEntryController + ItiLabsReportController
    @Bean
    public GroupedOpenApi labsGroup() {
        return GroupedOpenApi.builder()
                .group("Labs")
                .pathsToMatch("/api/labs/**", "/placements/labs-report",
                               "/itilogin/**")
                .build();
    }

    // ── Implant ──────────────────────────────────────────────────────────
    // The 7 controllers in controller/Implant/ (industry + in-plant training).
    // Note: two of them live under the /api/placements base path, so the
    // Placements group excludes those two patterns - see placementsGroup.
    @Bean
    public GroupedOpenApi implantGroup() {
        return GroupedOpenApi.builder()
                .group("Implant")
                .pathsToMatch("/api/implant/**", "/api/implant-report/**",
                               "/api/industry-connected-trades/**",
                               "/api/placements/industries/**",
                               "/api/placements/industry-trade-mapping/**")
                .build();
    }

    // ── Placements ───────────────────────────────────────────────────────
    //   /api/placements/**  -> PlacementsController
    //   /masterdata/**      -> MasterDataController (placements dashboard)
    @Bean
    public GroupedOpenApi placementsGroup() {
        return GroupedOpenApi.builder()
                .group("Placements")
                .pathsToMatch("/api/placements/**", "/masterdata/**")
                .pathsToExclude("/api/placements/industries/**",
                                "/api/placements/industry-trade-mapping/**")
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

    // ── ITI ──────────────────────────────────────────────────────────────
    // Ramya's ITI module (5 controllers):
    //   /api/itis/**                -> itiController
    //   /api/trades/**              -> ItiTradeMstController
    //   /api/shift-unit-permitted/**-> ShiftUnitPermittedController
    //   /api/districts/**           -> DistrictController
    //   /api/designations/**        -> DesignationController
    // /api/trades/by-minqual/** belongs to the Admission Process module
    // (trdnms_by_minqual_controller) even though it shares this base path.
    @Bean
    public GroupedOpenApi itiGroup() {
        return GroupedOpenApi.builder()
                .group("ITI")
                .pathsToMatch(
                    "/api/itis/**", "/api/trades/**",
                    "/api/shift-unit-permitted/**",
                    "/api/districts/**", "/api/designations/**")
                .pathsToExclude("/api/trades/by-minqual/**")
                .build();
    }

}
