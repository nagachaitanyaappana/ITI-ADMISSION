package com.server.backend.DTO;

/**
 * Aggregate seat statistics for one dashboard widget (pie chart).
 * Field names match what placementDashboard.jsp expects:
 * strength, strength_fill, strength_vacant, fill_ratio.
 */
public class SeatStatsResponse {
    private long strength;
    private long strength_fill;
    private long strength_vacant;
    private double fill_ratio;

    public SeatStatsResponse(long strength, long strength_fill, long strength_vacant, double fill_ratio) {
        this.strength = strength;
        this.strength_fill = strength_fill;
        this.strength_vacant = strength_vacant;
        this.fill_ratio = fill_ratio;
    }

    public long getStrength() { return strength; }
    public void setStrength(long strength) { this.strength = strength; }
    public long getStrength_fill() { return strength_fill; }
    public void setStrength_fill(long strength_fill) { this.strength_fill = strength_fill; }
    public long getStrength_vacant() { return strength_vacant; }
    public void setStrength_vacant(long strength_vacant) { this.strength_vacant = strength_vacant; }
    public double getFill_ratio() { return fill_ratio; }
    public void setFill_ratio(double fill_ratio) { this.fill_ratio = fill_ratio; }
}
