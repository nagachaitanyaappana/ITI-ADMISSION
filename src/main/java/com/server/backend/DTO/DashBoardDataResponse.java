package com.server.backend.DTO;

/**
 * Response for /masterdata/dashBoardData.
 * Field names match placementDashboard.jsp: dashBoardAllSeats,
 * dashBoardGovtSeats, dashBoardPvtSeats.
 */
public class DashBoardDataResponse {
    private SeatStatsResponse dashBoardAllSeats;
    private SeatStatsResponse dashBoardGovtSeats;
    private SeatStatsResponse dashBoardPvtSeats;

    public DashBoardDataResponse(SeatStatsResponse dashBoardAllSeats,
                                 SeatStatsResponse dashBoardGovtSeats,
                                 SeatStatsResponse dashBoardPvtSeats) {
        this.dashBoardAllSeats = dashBoardAllSeats;
        this.dashBoardGovtSeats = dashBoardGovtSeats;
        this.dashBoardPvtSeats = dashBoardPvtSeats;
    }

    public SeatStatsResponse getDashBoardAllSeats() { return dashBoardAllSeats; }
    public void setDashBoardAllSeats(SeatStatsResponse dashBoardAllSeats) { this.dashBoardAllSeats = dashBoardAllSeats; }
    public SeatStatsResponse getDashBoardGovtSeats() { return dashBoardGovtSeats; }
    public void setDashBoardGovtSeats(SeatStatsResponse dashBoardGovtSeats) { this.dashBoardGovtSeats = dashBoardGovtSeats; }
    public SeatStatsResponse getDashBoardPvtSeats() { return dashBoardPvtSeats; }
    public void setDashBoardPvtSeats(SeatStatsResponse dashBoardPvtSeats) { this.dashBoardPvtSeats = dashBoardPvtSeats; }
}
