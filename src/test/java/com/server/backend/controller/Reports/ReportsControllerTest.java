package com.server.backend.controller.Reports;

import java.util.Locale;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class ReportsControllerTest {

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(new ReportsController())
                .setViewResolvers(new NoOpViewResolver())
                .build();
    }

    @Test
    void aboutStriveReturnsView() throws Exception {
        mockMvc.perform(get("/reports/about-strive"))
                .andExpect(status().isOk())
                .andExpect(view().name("aboutstrive"));
    }

    @Test
    void disclosureManagementReturnsView() throws Exception {
        mockMvc.perform(get("/reports/disclosure-management"))
                .andExpect(status().isOk())
                .andExpect(view().name("disclosuremanagement"));
    }

    private static class NoOpViewResolver implements ViewResolver {
        @Override
        public View resolveViewName(String viewName, Locale locale) {
            return new View() {
                @Override
                public String getContentType() {
                    return "text/html";
                }

                @Override
                public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response) {
                }
            };
        }
    }
}
