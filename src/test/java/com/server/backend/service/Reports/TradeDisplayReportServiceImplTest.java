package com.server.backend.service.Reports;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.server.backend.DTO.Reports.ItiTradeDisplayResponse;
import com.server.backend.DTO.Reports.TradeDisplayReportRequest;
import com.server.backend.Repository.DistrictMasterRepository;
import com.server.backend.Repository.ItiRepository;

class TradeDisplayReportServiceImplTest {

    private ItiRepository itiRepository;
    private TradeDisplayReportServiceImpl reportService;

    @BeforeEach
    void setUp() {
        itiRepository = mock(ItiRepository.class);
        reportService = new TradeDisplayReportServiceImpl(mock(DistrictMasterRepository.class), itiRepository);
    }

    @Test
    void groupsItiTradeRowsByItiCodeForSelectedDistrictAndType() {
        TradeDisplayReportRequest request = new TradeDisplayReportRequest();
        request.setDist("01");
        request.setType("G");

        when(itiRepository.findItiAndTradeNamesByDistrictCodeAndGovt("01", "G"))
                .thenReturn(List.<Object[]>of(
                        new Object[] { "ITI1", "Govt ITI One", "Electrician", 24 },
                        new Object[] { "ITI1", "Govt ITI One", "Fitter", 20 },
                        new Object[] { "ITI2", "Govt ITI Two", "Welder", 16 }));

        List<ItiTradeDisplayResponse> results = reportService.getItisWithTradesByDistrict(request);

        assertEquals(2, results.size());
        assertEquals("ITI1", results.get(0).getCode());
        assertEquals("Govt ITI One", results.get(0).getItiName());
        assertEquals(2, results.get(0).getTrades().size());
        assertEquals("Electrician", results.get(0).getTrades().get(0).getTradeName());
        assertEquals(24, results.get(0).getTrades().get(0).getStrength());
        assertEquals("Fitter", results.get(0).getTrades().get(1).getTradeName());
        assertEquals(20, results.get(0).getTrades().get(1).getStrength());
        assertEquals("ITI2", results.get(1).getCode());
        assertEquals("Welder", results.get(1).getTrades().get(0).getTradeName());
    }

    @Test
    void usesAllTypesQueryWhenTypeIsBlank() {
        TradeDisplayReportRequest request = new TradeDisplayReportRequest();
        request.setDist("01");
        request.setType("");

        when(itiRepository.findItiAndTradeNamesByDistrictCode("01"))
                .thenReturn(List.<Object[]>of(new Object[] { "ITI1", "ITI One", "Electrician", 24 }));

        List<ItiTradeDisplayResponse> results = reportService.getItisWithTradesByDistrict(request);

        assertEquals(1, results.size());
        assertEquals("ITI One", results.get(0).getItiName());
        verify(itiRepository).findItiAndTradeNamesByDistrictCode("01");
    }

    @Test
    void returnsEmptyListWhenDistrictIsMissing() {
        TradeDisplayReportRequest request = new TradeDisplayReportRequest();

        List<ItiTradeDisplayResponse> results = reportService.getItisWithTradesByDistrict(request);

        assertTrue(results.isEmpty());
    }
}
