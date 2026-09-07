package com.server.backend.service.Implant;

import com.server.backend.DTO.Implant.IndustryDropdownResponse;
import com.server.backend.DTO.Implant.TradeResponse;
import com.server.backend.Repository.PlacementsRepositories.IndustriesRepository;
import com.server.backend.entity.Placements.Industries;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
@Service
public class IndustriesServiceImpl implements IndustriesService {

    private final IndustriesRepository industriesRepository;

    public IndustriesServiceImpl(IndustriesRepository industriesRepository) {
        this.industriesRepository = industriesRepository;
    }

  @Override
public List<IndustryDropdownResponse> getIndustriesByIti(Integer itiCode) {

    List<Industries> industries =
            industriesRepository.findByItiCode(itiCode);

    Map<Long, IndustryDropdownResponse> industryMap =
            new LinkedHashMap<>();

    for (Industries industry : industries) {

        IndustryDropdownResponse response =
                industryMap.get(industry.getIndustryId());

        if (response == null) {

            response = new IndustryDropdownResponse(
                    industry.getIndustryId(),
                    industry.getIndustryName(),
                    industry.getIndustryType(),
                    new ArrayList<>()
            );

            industryMap.put(industry.getIndustryId(), response);
        }

        // Add trade only when trade information exists
        if (industry.getTradeCode() != null) {

            // Prevent duplicate trade for the same industry
            boolean tradeAlreadyExists =
                    response.getTrades()
                            .stream()
                            .anyMatch(trade ->
                                    trade.getTradeCode()
                                            .equals(industry.getTradeCode())
                            );

            if (!tradeAlreadyExists) {

                TradeResponse trade = new TradeResponse(
                        industry.getTradeCode(),
                        industry.getTradeName(),
                        industry.getTradeShort()
                );

                response.getTrades().add(trade);
            }
        }
    }

    return new ArrayList<>(industryMap.values());
}
}
