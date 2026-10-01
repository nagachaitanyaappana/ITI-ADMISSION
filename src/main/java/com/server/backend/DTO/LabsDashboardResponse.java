package com.server.backend.DTO;

public class LabsDashboardResponse {
    private long labsTotal;
    private long labsDistinctItis;
    private long labsDistinctTrades;
    private long labItemsTotal;
    private long labItemsDistinctItems;

    public long getLabsTotal() { return labsTotal; }
    public void setLabsTotal(long labsTotal) { this.labsTotal = labsTotal; }
    public long getLabsDistinctItis() { return labsDistinctItis; }
    public void setLabsDistinctItis(long labsDistinctItis) { this.labsDistinctItis = labsDistinctItis; }
    public long getLabsDistinctTrades() { return labsDistinctTrades; }
    public void setLabsDistinctTrades(long labsDistinctTrades) { this.labsDistinctTrades = labsDistinctTrades; }
    public long getLabItemsTotal() { return labItemsTotal; }
    public void setLabItemsTotal(long labItemsTotal) { this.labItemsTotal = labItemsTotal; }
    public long getLabItemsDistinctItems() { return labItemsDistinctItems; }
    public void setLabItemsDistinctItems(long labItemsDistinctItems) { this.labItemsDistinctItems = labItemsDistinctItems; }
}
