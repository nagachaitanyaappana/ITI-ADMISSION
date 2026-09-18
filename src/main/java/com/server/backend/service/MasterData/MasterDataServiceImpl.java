package com.server.backend.service.MasterData;

import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.DashBoardDataResponse;
import com.server.backend.DTO.ItiDetailResponse;
import com.server.backend.DTO.ItiPercentStatsResponse;
import com.server.backend.DTO.SeatStatsResponse;

/**
 * Placement-portal dashboard aggregation (placementDashboard.jsp widgets).
 *
 * Reads the PLACEMENTS-side schemas of the merged database:
 *   public2.iti_seatmatrix (hstore seat matrix) x public2.iti (govt flag) x public2.dist_mst.
 * Admissions-side data lives in public./admissions. and is intentionally not used here.
 *
 * Logic (per the ITI module document):
 *   Vacant Seats  = Opened Seats - Filled Seats
 *   Admission %   = Filled Seats / Opened Seats x 100   (fill_ratio)
 *   >=20% bucket  = ITIs with fill_ratio >= 20
 *   <20%  bucket  = ITIs with fill_ratio <  20
 *
 * year/phase default to the latest values present in public2.iti_seatmatrix so the
 * dashboard keeps working when a new admission year's data is loaded.
 */
@Service
public class MasterDataServiceImpl implements MasterDataService {

    private final JdbcTemplate jdbcTemplate;

    public MasterDataServiceImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final double PERCENT_THRESHOLD = 20.0;

    /** Per-ITI aggregate seat sums for the given year/phase. */
    private static final String ITI_SEATS_SQL = """
        SELECT TRIM(i.iti_code) AS iti_code,
               i.iti_name,
               d.dist_name,
               i.govt,
               COALESCE(SUM((SELECT SUM(v::bigint) FROM each(sm.strength) t(k,v))), 0)        AS strength,
               COALESCE(SUM((SELECT SUM(v::bigint) FROM each(sm.strength_fill) t(k,v))), 0)   AS fill,
               COALESCE(SUM((SELECT SUM(v::bigint) FROM each(sm.strength_vacant) t(k,v))), 0) AS vacant
        FROM public2.iti_seatmatrix sm
        JOIN public2.iti i ON TRIM(sm.iti_code) = TRIM(i.iti_code)
        LEFT JOIN public2.dist_mst d ON TRIM(i.dist_code) = TRIM(d.dist_code)
        WHERE sm.year::text = ?::text AND sm.phase = ?
        GROUP BY TRIM(i.iti_code), i.iti_name, d.dist_name, i.govt
        ORDER BY d.dist_name, i.iti_name
        """;

    private static final String LATEST_YEAR_SQL =
        "SELECT MAX(year) FROM public2.iti_seatmatrix";

    private static final String LATEST_PHASE_SQL =
        "SELECT MAX(phase) FROM public2.iti_seatmatrix WHERE year::text = ?::text";

    private record ItiSeats(String itiCode, String itiName, String distName, String govt,
                            long strength, long fill, long vacant) {
        double fillRatio() {
            return strength > 0 ? round2(fill * 100.0 / strength) : 0.0;
        }
    }

    private static double round2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    private String resolveYear(String year) {
        if (year != null && !year.isBlank()) return year;
        String y = jdbcTemplate.query(LATEST_YEAR_SQL, rs -> rs.next() ? rs.getString(1) : null);
        return y != null ? y : String.valueOf(java.time.Year.now().getValue());
    }

    private int resolvePhase(Integer phase, String year) {
        if (phase != null && phase > 0) return phase;
        Integer p = jdbcTemplate.query(LATEST_PHASE_SQL, rs -> rs.next() ? rs.getInt(1) : null, year);
        return (p != null && p > 0) ? p : 1;
    }

    private List<ItiSeats> fetchItiSeats(String year, int phase) {
        return jdbcTemplate.query(ITI_SEATS_SQL, (rs, rowNum) -> new ItiSeats(
                rs.getString("iti_code"),
                rs.getString("iti_name"),
                rs.getString("dist_name"),
                rs.getString("govt"),
                rs.getLong("strength"),
                rs.getLong("fill"),
                rs.getLong("vacant")),
                year, phase);
    }

    private SeatStatsResponse aggregate(List<ItiSeats> rows) {
        long s = 0, f = 0, v = 0;
        for (ItiSeats r : rows) {
            s += r.strength();
            f += r.fill();
            v += r.vacant();
        }
        double ratio = s > 0 ? round2(f * 100.0 / s) : 0.0;
        return new SeatStatsResponse(s, f, v, ratio);
    }

    private ItiPercentStatsResponse percentStats(List<ItiSeats> rows, boolean above) {
        List<ItiSeats> bucket = new ArrayList<>();
        for (ItiSeats r : rows) {
            if (above ? r.fillRatio() >= PERCENT_THRESHOLD : r.fillRatio() < PERCENT_THRESHOLD) {
                bucket.add(r);
            }
        }
        SeatStatsResponse agg = aggregate(bucket);
        return new ItiPercentStatsResponse(agg.getStrength(), agg.getStrength_fill(),
                agg.getStrength_vacant(), agg.getFill_ratio(), bucket.size());
    }

    private List<ItiDetailResponse> percentDetails(List<ItiSeats> rows, boolean above) {
        List<ItiDetailResponse> out = new ArrayList<>();
        for (ItiSeats r : rows) {
            if (above ? r.fillRatio() >= PERCENT_THRESHOLD : r.fillRatio() < PERCENT_THRESHOLD) {
                out.add(new ItiDetailResponse(r.distName(), r.itiName(),
                        r.strength(), r.fill(), r.vacant(), r.fillRatio()));
            }
        }
        return out;
    }

    @Override
    public DashBoardDataResponse getDashBoardData(String year, Integer phase) {
        String y = resolveYear(year);
        int p = resolvePhase(phase, y);
        List<ItiSeats> all = fetchItiSeats(y, p);
        List<ItiSeats> govt = all.stream().filter(r -> "G".equalsIgnoreCase(r.govt())).toList();
        List<ItiSeats> pvt = all.stream().filter(r -> "P".equalsIgnoreCase(r.govt())).toList();
        return new DashBoardDataResponse(aggregate(all), aggregate(govt), aggregate(pvt));
    }

    @Override
    public ItiPercentStatsResponse getAbove20PercentItisStats(String year, Integer phase) {
        String y = resolveYear(year);
        return percentStats(fetchItiSeats(y, resolvePhase(phase, y)), true);
    }

    @Override
    public ItiPercentStatsResponse getBelow20PercentItisStats(String year, Integer phase) {
        String y = resolveYear(year);
        return percentStats(fetchItiSeats(y, resolvePhase(phase, y)), false);
    }

    @Override
    public List<ItiDetailResponse> getAbove20PercentItis(String year, Integer phase) {
        String y = resolveYear(year);
        return percentDetails(fetchItiSeats(y, resolvePhase(phase, y)), true);
    }

    @Override
    public List<ItiDetailResponse> getBelow20PercentItis(String year, Integer phase) {
        String y = resolveYear(year);
        return percentDetails(fetchItiSeats(y, resolvePhase(phase, y)), false);
    }
}
