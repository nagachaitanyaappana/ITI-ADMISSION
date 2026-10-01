package com.server.backend.DTO;

/**
 * Response for getAbove20PercentItisStats / getBelow20PercentItisStats.
 */
public class ItiPercentStatsResponse {
    private long strength;
    private long strength_fill;
    private long strength_vacant;
    private double fill_ratio;
    private int noOfItis;

    public ItiPercentStatsResponse(long strength, long strength_fill, long strength_vacant,
                                   double fill_ratio, int noOfItis) {
        this.strength = strength;
        this.strength_fill = strength_fill;
        this.strength_vacant = strength_vacant;
        this.fill_ratio = fill_ratio;
        this.noOfItis = noOfItis;
    }

    public long getStrength() { return strength; }
    public void setStrength(long strength) { this.strength = strength; }
    public long getStrength_fill() { return strength_fill; }
    public void setStrength_fill(long strength_fill) { this.strength_fill = strength_fill; }
    public long getStrength_vacant() { return strength_vacant; }
    public void setStrength_vacant(long strength_vacant) { this.strength_vacant = strength_vacant; }
    public double getFill_ratio() { return fill_ratio; }
    public void setFill_ratio(double fill_ratio) { this.fill_ratio = fill_ratio; }
    public int getNoOfItis() { return noOfItis; }
    public void setNoOfItis(int noOfItis) { this.noOfItis = noOfItis; }
}
