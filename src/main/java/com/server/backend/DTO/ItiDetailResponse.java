package com.server.backend.DTO;

/**
 * One row of the >=20% / <20% ITI drill-down table (getAbove20PercentItis /
 * getBelow20PercentItis). Field names match placementDashboard.jsp renderDrillTable.
 */
public class ItiDetailResponse {
    private String dist_name;
    private String iti_name;
    private long strength;
    private long strength_fill;
    private long strength_vacant;
    private double fill_ratio;

    public ItiDetailResponse(String dist_name, String iti_name, long strength,
                             long strength_fill, long strength_vacant, double fill_ratio) {
        this.dist_name = dist_name;
        this.iti_name = iti_name;
        this.strength = strength;
        this.strength_fill = strength_fill;
        this.strength_vacant = strength_vacant;
        this.fill_ratio = fill_ratio;
    }

    public String getDist_name() { return dist_name; }
    public void setDist_name(String dist_name) { this.dist_name = dist_name; }
    public String getIti_name() { return iti_name; }
    public void setIti_name(String iti_name) { this.iti_name = iti_name; }
    public long getStrength() { return strength; }
    public void setStrength(long strength) { this.strength = strength; }
    public long getStrength_fill() { return strength_fill; }
    public void setStrength_fill(long strength_fill) { this.strength_fill = strength_fill; }
    public long getStrength_vacant() { return strength_vacant; }
    public void setStrength_vacant(long strength_vacant) { this.strength_vacant = strength_vacant; }
    public double getFill_ratio() { return fill_ratio; }
    public void setFill_ratio(double fill_ratio) { this.fill_ratio = fill_ratio; }
}
