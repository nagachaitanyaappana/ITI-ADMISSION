package com.server.backend.DTO;

public class PlacementsOverviewResponse {
    private long allPlacement;
    private long allImplants;
    private long allLabs;

    public long getAllPlacement() { return allPlacement; }
    public void setAllPlacement(long allPlacement) { this.allPlacement = allPlacement; }
    public long getAllImplants() { return allImplants; }
    public void setAllImplants(long allImplants) { this.allImplants = allImplants; }
    public long getAllLabs() { return allLabs; }
    public void setAllLabs(long allLabs) { this.allLabs = allLabs; }
}
