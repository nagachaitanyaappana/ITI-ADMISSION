package com.server.backend.controller.Reports;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;

import com.server.backend.DTO.Reports.DistrictOptionResponse;
import com.server.backend.DTO.Reports.ItiTradeDisplayResponse;
import com.server.backend.DTO.Reports.ItiTradeDisplayResponse.TradeDetail;
import com.server.backend.DTO.Reports.TradeDisplayReportRequest;
import com.server.backend.service.Reports.TradeDisplayReportService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

class ReportsControllerTest {

    private FakeTradeDisplayReportService tradeDisplayReportService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        tradeDisplayReportService = new FakeTradeDisplayReportService();
        mockMvc = MockMvcBuilders.standaloneSetup(new ReportsController(tradeDisplayReportService))
                .setViewResolvers(new NoOpViewResolver())
                .build();
    }

    @Test
    void tradedisplayAddsDistrictsAndReturnsTradeDisplayView() throws Exception {
        tradeDisplayReportService.districts = List.of(new DistrictOptionResponse("01", "ANANTAPUR"));

        mockMvc.perform(get("/reports/trade-display"))
                .andExpect(status().isOk())
                .andExpect(view().name("tradedisplay"))
                .andExpect(model().attribute("districtList", tradeDisplayReportService.districts))
                .andExpect(model().attribute("itiList", new ArrayList<>()))
                .andExpect(model().attribute("selectedDist", ""))
                .andExpect(model().attribute("selectedType", ""))
                .andExpect(model().attribute("reportSubmitted", false));
    }

    @Test
    void submitTradeDisplayBindsRequestAndAddsReportData() throws Exception {
        tradeDisplayReportService.districts = List.of(new DistrictOptionResponse("01", "ANANTAPUR"));
        tradeDisplayReportService.itis = List.of(itiWithTrades());

        mockMvc.perform(post("/reports/trade-display")
                .param("dist", "01")
                .param("type", "G"))
                .andExpect(status().isOk())
                .andExpect(view().name("tradedisplay"))
                .andExpect(model().attribute("districtList", tradeDisplayReportService.districts))
                .andExpect(model().attribute("itiList", tradeDisplayReportService.itis))
                .andExpect(model().attribute("selectedDist", "01"))
                .andExpect(model().attribute("selectedType", "G"))
                .andExpect(model().attribute("reportSubmitted", true));

        assertNotNull(tradeDisplayReportService.lastRequest);
        assertEquals("01", tradeDisplayReportService.lastRequest.getDist());
        assertEquals("G", tradeDisplayReportService.lastRequest.getType());
    }

    @Test
    void submitTradeDisplayUsesEmptyListWhenServiceReturnsNull() throws Exception {
        tradeDisplayReportService.itis = null;

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

    private static class FakeTradeDisplayReportService implements TradeDisplayReportService {
        private List<DistrictOptionResponse> districts = new ArrayList<>();
        private List<ItiTradeDisplayResponse> itis = new ArrayList<>();
        private TradeDisplayReportRequest lastRequest;

        @Override
        public List<DistrictOptionResponse> getDistrictOptions() {
            return districts;
        }

        @Override
        public List<ItiTradeDisplayResponse> getTradeDisplayReport(TradeDisplayReportRequest request) {
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
