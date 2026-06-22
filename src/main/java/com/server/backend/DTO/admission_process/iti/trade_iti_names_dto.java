package com.server.backend.DTO.admission_process.iti;

import java.util.List;

public class trade_iti_names_dto {

    private List<String> itiNames;
    private List<String> tradeNames;

    public trade_iti_names_dto(List<String> itiNames, List<String> tradeNames) {
        this.itiNames = itiNames;
        this.tradeNames = tradeNames;
    }

    public List<String> getItiNames() {
        return itiNames;
    }

    public void setItiNames(List<String> itiNames) {
        this.itiNames = itiNames;
    }

    public List<String> getTradeNames() {
        return tradeNames;
    }

    public void setTradeNames(List<String> tradeNames) {
        this.tradeNames = tradeNames;
    }
}