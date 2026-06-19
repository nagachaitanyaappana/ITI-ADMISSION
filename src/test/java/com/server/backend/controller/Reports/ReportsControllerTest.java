package com.server.backend.controller.Reports;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.server.backend.DTO.Reports.DistrictOptionResponse;
import com.server.backend.DTO.Reports.ItiTradeDisplayResponse;
import com.server.backend.DTO.Reports.ItiTradeDisplayResponse.TradeDetail;
import com.server.backend.DTO.Reports.TradeDisplayReportRequest;
import com.server.backend.service.Reports.TradeDisplayReportService;

class ReportsControllerTest {

    private FakeReportService reportService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        reportService = new FakeReportService();
        mockMvc = MockMvcBuilders.standaloneSetup(new ReportsController(reportService))
                .setViewResolvers(new NoOpViewResolver())
                .build();
    }

    @Test
    void tradedisplayAddsDistrictsAndReturnsTradeDisplayView() throws Exception {
        reportService.districts = List.of(new DistrictOptionResponse("01", "ANANTAPUR"));

        mockMvc.perform(get("/reports/trade-display"))
                .andExpect(status().isOk())
                .andExpect(view().name("tradedisplay"))
                .andExpect(model().attribute("districtList", reportService.districts))
                .andExpect(model().attribute("itiList", new ArrayList<>()))
                .andExpect(model().attribute("selectedDist", ""))
                .andExpect(model().attribute("selectedType", ""))
                .andExpect(model().attribute("reportSubmitted", false));
    }

    @Test
    void submitTradeDisplayBindsRequestAndAddsReportData() throws Exception {
        reportService.districts = List.of(new DistrictOptionResponse("01", "ANANTAPUR"));
        reportService.itis = List.of(itiWithTrades());

        mockMvc.perform(post("/reports/trade-display")
                .param("dist", "01")
                .param("type", "G"))
                .andExpect(status().isOk())
                .andExpect(view().name("tradedisplay"))
                .andExpect(model().attribute("districtList", reportService.districts))
                .andExpect(model().attribute("itiList", reportService.itis))
                .andExpect(model().attribute("selectedDist", "01"))
                .andExpect(model().attribute("selectedType", "G"))
                .andExpect(model().attribute("reportSubmitted", true));

        assertNotNull(reportService.lastRequest);
        assertEquals("01", reportService.lastRequest.getDist());
        assertEquals("G", reportService.lastRequest.getType());
    }

    @Test
    void submitTradeDisplayUsesEmptyListWhenServiceReturnsNull() throws Exception {
        reportService.itis = null;

        mockMvc.perform(post("/reports/trade-display")
                .param("dist", "01")
                .param("type", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("tradedisplay"))
                .andExpect(model().attribute("itiList", new ArrayList<>()));
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

    private ItiTradeDisplayResponse itiWithTrades() {
        ItiTradeDisplayResponse response = new ItiTradeDisplayResponse();
        response.setCode("ITI001");
        response.setItiName("Govt ITI");
        response.setTrades(List.of(new TradeDetail("Electrician", 24)));
        return response;
    }

    private static class FakeReportService implements TradeDisplayReportService {
        private List<DistrictOptionResponse> districts = new ArrayList<>();
        private List<ItiTradeDisplayResponse> itis = new ArrayList<>();
        private TradeDisplayReportRequest lastRequest;

        @Override
        public List<DistrictOptionResponse> getAllDistricts() {
            return districts;
        }

        @Override
        public List<ItiTradeDisplayResponse> getItisWithTradesByDistrict(TradeDisplayReportRequest request) {
            lastRequest = request;
            return itis;
        }
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
