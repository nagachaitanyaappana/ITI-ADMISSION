package com.server.backend.controller;

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

import com.server.backend.DTO.Reports.DistrictCollegeTypeResponse;
import com.server.backend.DTO.Reports.DistrictMasterResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse;
import com.server.backend.DTO.Reports.ItiWithTradesResponse.TradeDetail;
import com.server.backend.service.ReportService;

class ReportControllerTest {

    private FakeReportService reportService;
    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        reportService = new FakeReportService();
        mockMvc = MockMvcBuilders.standaloneSetup(new ReportController(reportService))
                .setViewResolvers(new NoOpViewResolver())
                .build();
    }

    @Test
    void tradedisplayAddsDistrictsAndReturnsTradeDisplayView() throws Exception {
        reportService.districts = List.of(new DistrictMasterResponse("01", "ANANTAPUR"));

        mockMvc.perform(get("/tradedisplay"))
                .andExpect(status().isOk())
                .andExpect(view().name("tradedisplay"))
                .andExpect(model().attribute("districtList", reportService.districts));
    }

    @Test
    void submitTradeDisplayBindsRequestAndAddsReportData() throws Exception {
        reportService.districts = List.of(new DistrictMasterResponse("01", "ANANTAPUR"));
        reportService.itis = List.of(itiWithTrades());

        mockMvc.perform(post("/trade_display2")
                .param("dist", "01")
                .param("type", "G"))
                .andExpect(status().isOk())
                .andExpect(view().name("tradedisplay"))
                .andExpect(model().attribute("districtList", reportService.districts))
                .andExpect(model().attribute("itiList", reportService.itis));

        assertNotNull(reportService.lastRequest);
        assertEquals("01", reportService.lastRequest.getDist());
        assertEquals("G", reportService.lastRequest.getType());
    }

    @Test
    void submitTradeDisplayUsesEmptyListWhenServiceReturnsNull() throws Exception {
        reportService.itis = null;

        mockMvc.perform(post("/trade_display2")
                .param("dist", "01")
                .param("type", ""))
                .andExpect(status().isOk())
                .andExpect(view().name("tradedisplay"))
                .andExpect(model().attribute("itiList", new ArrayList<>()));
    }

    @Test
    void aboutStriveReturnsView() throws Exception {
        mockMvc.perform(get("/AboutStrive"))
                .andExpect(status().isOk())
                .andExpect(view().name("/aboutstrive"));
    }

    @Test
    void disclosureManagementReturnsView() throws Exception {
        mockMvc.perform(get("/DisclosureManagement"))
                .andExpect(status().isOk())
                .andExpect(view().name("/disclosuremanagement"));
    }

    private ItiWithTradesResponse itiWithTrades() {
        ItiWithTradesResponse response = new ItiWithTradesResponse();
        response.setCode("ITI001");
        response.setItiName("Govt ITI");
        response.setTrades(List.of(new TradeDetail("Electrician", 24)));
        return response;
    }

    private static class FakeReportService implements ReportService {
        private List<DistrictMasterResponse> districts = new ArrayList<>();
        private List<ItiWithTradesResponse> itis = new ArrayList<>();
        private DistrictCollegeTypeResponse lastRequest;

        @Override
        public List<DistrictMasterResponse> getAllDistricts() {
            return districts;
        }

        @Override
        public List<ItiWithTradesResponse> getItisWithTradesByDistrict(DistrictCollegeTypeResponse request) {
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
