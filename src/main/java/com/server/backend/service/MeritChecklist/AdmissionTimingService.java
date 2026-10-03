package com.server.backend.service.MeritChecklist;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import com.server.backend.entity.AdmissionTiming;
import com.server.backend.Repository.MeritChecklist.AdmissionTimingRepository;
import com.server.backend.entity.AdmissionTimingId;
import com.server.backend.DTO.*;
import java.util.Map;
import java.util.Optional;
import java.util.Date;
@Service
public class AdmissionTimingService {
    private final AdmissionTimingRepository admissionTimingRepository;
    private static final Logger logger = LoggerFactory.getLogger(AdmissionTimingService.class);
    public AdmissionTimingService(AdmissionTimingRepository admissionTimingRepository) {
        this.admissionTimingRepository = admissionTimingRepository;
    }

    /**
     * The running admission year.
     *
     * <p>{@code iti_params} code '7' is advisory: it currently reads 2026 while
     * {@code admissions.admission_phase} holds its only current row under 2025, so trusting the param
     * made {@code /api/status} and every schedule lookup fail with
     * "No current phase found for year 2026". {@code admission_phase} is the authority — the same
     * decision {@code MeritListService} and {@code TradeSelectionService.resolvePhase} already
     * document — and the param is only a fallback when no current phase row exists.
     */
    private String resolveCurrentYear() {
        Optional<String> currentYear = admissionTimingRepository.findCurrentPhaseYear();
        if (currentYear.isPresent() && currentYear.get() != null && !currentYear.get().isBlank()) {
            String phaseYear = currentYear.get().trim();
            String paramYear = admissionTimingRepository.findCurrentYearVal();
            if (paramYear != null && !paramYear.trim().equals(phaseYear)) {
                logger.warn("iti_params code '7' year {} does not match the current admission_phase "
                        + "year {}; using admission_phase", paramYear.trim(), phaseYear);
            }
            return phaseYear;
        }

        String yearStr = admissionTimingRepository.findCurrentYearVal();
        if (yearStr == null || yearStr.isBlank()) {
            throw new IllegalArgumentException("Current year value is missing from the system configuration.");
        }
        return yearStr.trim();
    }

    private String resolveCurrentPhase(String year) {
        String yearStr = year != null ? String.valueOf(year) : null;
        Optional<String> byYear = admissionTimingRepository.findCurrentPhaseVal(yearStr);
        if (byYear.isPresent()) {
            return byYear.get();
        }

        // Fall back to the current phase row even when the parameter year disagrees, so a stale
        // iti_params value cannot break the page (see resolveCurrentYear).
        Optional<String> current = admissionTimingRepository.findCurrentPhaseAnyYear();
        if (current.isPresent() && current.get() != null && !current.get().isBlank()) {
            String phase = current.get().trim();
            logger.warn("No current admission_phase row for year {}; using the current phase {}",
                    year, phase);
            return phase;
        }
        throw new IllegalArgumentException("No current phase found for year " + year);
    }

    /**
     * Resolves a date that the caller has already validated as present. Used by Step 2, where a
     * scheduled session genuinely needs its date.
     */
    private LocalDate resolveDate(String value) {
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Date is required");
        }
        return parseDate(value);
    }

    /**
     * Resolves a date that may legitimately be absent, returning {@code null} for a blank value.
     *
     * <p>Used by Step 1 (schedule initialisation). That step only defines a scope -- category and
     * qualification -- and its date field lives in Step 2, which the JSP only reveals once Step 1
     * succeeds. Requiring the date here deadlocked the wizard: the field could not be filled in
     * until the request that demanded it had already succeeded. Step 2 overwrites the null with the
     * date the user actually types, so a placeholder row is created now and dated later.
     *
     * <p>{@code admission_timings.cal_date} is nullable, so the stored null is safe.
     */
    private LocalDate resolveOptionalDate(String value) {
        if (value == null || value.isBlank()) {
            logger.debug("resolveOptionalDate: input is null or blank, leaving the date unset");
            return null;
        }
        return parseDate(value);
    }

    private LocalDate parseDate(String value) {
        try {
            return LocalDate.parse(value);
        } catch (DateTimeParseException ex) {
            try {
                return LocalDate.parse(value, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            } catch (DateTimeParseException innerEx) {
                throw new IllegalArgumentException("Invalid date format: " + value, innerEx);
            }
        }
    }

    private LocalTime resolveTime(String value) {
        if (value == null || value.isBlank()) {
            logger.debug("resolveTime: input is null or blank");
            return null;
        }

        String v = value.trim();
        DateTimeFormatter[] patterns = new DateTimeFormatter[] {
            DateTimeFormatter.ISO_LOCAL_TIME,
            DateTimeFormatter.ofPattern("H:mm"),
            DateTimeFormatter.ofPattern("HH:mm"),
            DateTimeFormatter.ofPattern("h:mm a"),
            DateTimeFormatter.ofPattern("hh:mm a"),
            DateTimeFormatter.ofPattern("H:mm:ss"),
            DateTimeFormatter.ofPattern("HH:mm:ss")
        };

        for (DateTimeFormatter fmt : patterns) {
            try {
                LocalTime parsed = LocalTime.parse(v, fmt);
                logger.debug("resolveTime: parsed '{}' using pattern {} -> {}", v, fmt, parsed);
                return parsed;
            } catch (DateTimeParseException ex) {
                // try next
            }
        }

        // Try a relaxed uppercase/no-dots variant (e.g., "12:30pm" or "12.30 pm")
        String alt = v.replaceAll("\\.", "").replaceAll("\\s+", " ").toUpperCase();
        for (DateTimeFormatter fmt : patterns) {
            try {
                LocalTime parsed = LocalTime.parse(alt, fmt);
                logger.debug("resolveTime: parsed '{}' (alt='{}') using pattern {} -> {}", v, alt, fmt, parsed);
                return parsed;
            } catch (DateTimeParseException ex) {
                // try next
            }
        }

        logger.debug("resolveTime: failed to parse time '{}'", v);
        throw new IllegalArgumentException("Invalid time format: " + value);
    }

    public AdmissionTiming createAdmissionTiming(AdmissionTiming admissionTiming) {
        return admissionTimingRepository.save(admissionTiming);
    }
    public List<AdmissionTiming> getAllAdmissionTimings() {
        return admissionTimingRepository.findAll();
    }
    public AdmissionTiming getById(String itiCode, String phase) {
        AdmissionTimingId id = new AdmissionTimingId(itiCode, phase);
        return admissionTimingRepository.findById(id).orElse(null);
    }
    public void delete(String itiCode, String phase) {
        AdmissionTimingId id = new AdmissionTimingId(itiCode, phase);
        admissionTimingRepository.deleteById(id);
    }
    public AdmissionTiming updateAdmissionTiming(String itiCode, String phase, AdmissionTiming updatedAdmissionTiming) {
        updatedAdmissionTiming.setItiCode(itiCode);
        updatedAdmissionTiming.setPhase(phase);
        return admissionTimingRepository.save(updatedAdmissionTiming);
    }
    @Transactional
public Map<String, Object> createScheduleEntry(CreateEntryRequest req, CurrentUser user) {
    String year = resolveCurrentYear().toString();
    String phase = resolveCurrentPhase(year);

    String caste = req.reservation() != null ? req.reservation() : "all";
    String quality = req.minqul() != null ? req.minqul() : "all";

    // Serialise the MAX+1 read and the INSERT below. Without this, two Step 1 requests arriving
    // together both read the same max and are handed the same temp_pk.
    admissionTimingRepository.lockTempPkAllocation();
    Integer nextPk = admissionTimingRepository.getNextTempPkVal();

    int trno;
    try {
        trno = Integer.parseInt(user.insCode());
    } catch (NumberFormatException e) {
        trno = 0;
    }
    String tempPk = String.valueOf(nextPk);
    LocalDate calDate = resolveOptionalDate(req.calDate());
    LocalTime calTime = resolveTime(req.calTime());

    admissionTimingRepository.insertScheduleRow(
        user.itiCode(), user.distCode(), quality, calDate, calTime, caste, trno, tempPk, phase, year);

    boolean useDist = "3".equals(user.roleId());
    String entityName = useDist 
        ? admissionTimingRepository.findDistName(user.distCode()).orElse("Unknown")
        : admissionTimingRepository.findItiName(user.itiCode()).orElse("Unknown");

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "Schedule entry created successfully");
    response.put("data", schedulePayload(useDist, useDist ? user.distCode() : user.itiCode(),
        phase, year, tempPk, calDate, calTime, 0, 0, caste, quality, entityName));
    response.put("dist_name", useDist ? entityName : null);
    response.put("iti_name", useDist ? null : entityName);

    return response;
}

@Transactional
public Map<String, Object> addScheduleTimings(UpdateTimingsRequest req, CurrentUser user) {
    String year = resolveCurrentYear();
    String phase = resolveCurrentPhase(year);
    
    String caste = req.reservation() != null ? req.reservation() : "all";
    String quality = req.minqul() != null ? req.minqul() : "all";

    boolean useDist = "3".equals(user.roleId());
    String entityValue = useDist ? user.distCode() : user.itiCode();

    List<String> placeholderPks = admissionTimingRepository.findPlaceholderTempPk(
        useDist, entityValue, phase, year, caste, quality);

    if (placeholderPks.isEmpty()) {
        throw new IllegalArgumentException("No available placeholder found for this "
            + (useDist ? "District" : "ITI") + " and category. Please create it first.");
    }
    String tempPk = placeholderPks.get(0);

    LocalDate requestedDate = resolveDate(req.calDate());
    LocalTime requestedTime = resolveTime(req.calTime());

    boolean hasDateTimeOverlap = useDist
        ? admissionTimingRepository.existsByDistCodeAndPhaseAndYearAndCalDateAndCalTimeAndTempPkNot(entityValue, phase, year, requestedDate, requestedTime, tempPk)
        : admissionTimingRepository.existsByItiCodeAndPhaseAndYearAndCalDateAndCalTimeAndTempPkNot(entityValue, phase, year, requestedDate, requestedTime, tempPk);

    if (hasDateTimeOverlap) {
        throw new IllegalArgumentException("The date " + requestedDate + " at " + requestedTime 
            + " is already booked for another session at this " + (useDist ? "District" : "ITI") + ".");
    }

    List<Object[]> overlapList = admissionTimingRepository.findOverlappingMeritRange(
        useDist, entityValue, phase, year, tempPk, req.meritFrom(), req.meritTo());

    if (!overlapList.isEmpty()) {
        Object[] existing = overlapList.get(0);
        throw new IllegalArgumentException("The merit range " + req.meritFrom() + "-" + req.meritTo()
            + " overlaps with an existing entry (" + existing[0] + "-" + existing[1] + ") for this "
            + (useDist ? "District" : "ITI") + ".");
    }

    int updated = admissionTimingRepository.updateScheduleRow(
        useDist, entityValue, phase, year, caste, quality, tempPk,
        req.meritFrom(), req.meritTo(), requestedDate, requestedTime);

    if (updated == 0) {
        throw new IllegalArgumentException("The schedule placeholder for this "
            + (useDist ? "District" : "ITI")
            + " was already filled in by another session. Please create it again.");
    }

    String entityName = useDist 
        ? admissionTimingRepository.findDistName(user.distCode()).orElse("Unknown")
        : admissionTimingRepository.findItiName(user.itiCode()).orElse("Unknown");

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "Schedule timings updated successfully");
    response.put("data", schedulePayload(useDist, entityValue, phase, year, tempPk, requestedDate,
        requestedTime, req.meritFrom(), req.meritTo(), caste, quality, entityName));
    response.put("dist_name", useDist ? entityName : null);
    response.put("iti_name", useDist ? null : entityName);

    return response;
}

/**
 * Re-reads the row Step 2 wrote and shapes it like the entity used to serialise, so the JSP keeps
 * receiving the same {@code data} object it did before ({@code minqul}, {@code reservation},
 * {@code phase}, ...) without any entity hydration.
 *
 * <p>{@code dist_name} / {@code iti_name} are repeated inside {@code data} because the page renders
 * the entity name from {@code result.data.dist_name || result.data.iti_name}
 * ({@code checkmeritschedule/ScheduleEntry.jsp}, {@code showTimingForm}) -- reading them from the
 * top level alone left both undefined there and the label fell through to a stale localStorage
 * value. Only one of the two is populated, matching the top-level keys, which are kept because that
 * is the shape the API has always returned.
 */
private Map<String, Object> schedulePayload(boolean useDist, String code, String phase, String year,
        String tempPk, LocalDate calDate, LocalTime calTime, Integer meritFrom, Integer meritTo,
        String caste, String minqul, String entityName) {
    List<Object[]> rows = admissionTimingRepository.findRowByTempPk(useDist, code, phase, year, tempPk);
    Object[] row = rows.isEmpty() ? new Object[] { tempPk, calDate, meritFrom, meritTo, calTime }
                                 : rows.get(0);

    DateTimeFormatter dateWriter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    LocalDate storedDate = asLocalDate(row[1]);

    Map<String, Object> payload = new HashMap<>();
    payload.put("tempPk", row[0]);
    payload.put("phase", phase);
    payload.put("year", year);
    payload.put("itiCode", useDist ? null : code);
    payload.put("distCode", useDist ? code : null);
    payload.put("minqul", minqul);
    payload.put("reservation", caste);
    payload.put("meritFrom", row[2]);
    payload.put("meritTo", row[3]);
    payload.put("calDate", storedDate == null ? null : storedDate.format(dateWriter));
    payload.put("calTime", asLocalTime(row[4]) != null ? asLocalTime(row[4]) : calTime);
    payload.put("dist_name", useDist ? entityName : null);
    payload.put("iti_name", useDist ? null : entityName);
    return payload;
}

    /**
     * Native queries hand back {@code java.sql.Date}/{@code java.sql.Time} over plain JDBC and
     * {@code LocalDate}/{@code LocalTime} over the JPA driver, so both shapes are accepted. Casting
     * to one concrete type inline is what used to throw ClassCastException on the other path.
     */
    private static LocalDate asLocalDate(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDate localDate) {
            return localDate;
        }
        if (value instanceof java.sql.Date sqlDate) {
            return sqlDate.toLocalDate();
        }
        if (value instanceof LocalDateTime dateTime) {
            return dateTime.toLocalDate();
        }
        if (value instanceof Date legacy) {
            return LocalDateTime.ofInstant(legacy.toInstant(), ZoneId.systemDefault()).toLocalDate();
        }
        return null;
    }

    private static LocalTime asLocalTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalTime localTime) {
            return localTime;
        }
        if (value instanceof java.sql.Time sqlTime) {
            return sqlTime.toLocalTime();
        }
        if (value instanceof LocalDateTime dateTime) {
            return dateTime.toLocalTime();
        }
        if (value instanceof Date legacy) {
            return LocalDateTime.ofInstant(legacy.toInstant(), ZoneId.systemDefault()).toLocalTime();
        }
        return null;
    }

    /**
     * The admission date window comes back as {@code LocalDateTime} from a Spring Data native query
     * and as {@code java.sql.Timestamp} from plain JdbcTemplate. Both are accepted here: the cast to
     * {@code java.util.Date} that used to sit inline threw ClassCastException on the JPA path, which
     * is why {@code POST /admission-timings/view} had never returned a row.
     */
    private static LocalDateTime asLocalDateTime(Object value) {
        if (value == null) {
            return null;
        }
        if (value instanceof LocalDateTime dateTime) {
            return dateTime;
        }
        if (value instanceof Date legacy) {
            return LocalDateTime.ofInstant(legacy.toInstant(), ZoneId.systemDefault());
        }
        logger.debug("Unsupported admission window type: {}", value.getClass().getName());
        return null;
    }

    public Map<String, Object> viewScheduleTimings(ViewScheduleRequest req, CurrentUser user) {
    String year = resolveCurrentYear();
    String yearStr = String.valueOf(year);
    
    List<Object[]> phaseRows = admissionTimingRepository.findPhaseDates(yearStr);
    if (phaseRows.isEmpty()) {
        throw new IllegalArgumentException("No current phase found for year " + year);
    }
    Object[] phaseMeta = phaseRows.get(0);

    String phase = phaseMeta[0] == null ? null : String.valueOf(phaseMeta[0]);
    LocalDateTime startDate = asLocalDateTime(phaseMeta[1]);
    LocalDateTime endDate = asLocalDateTime(phaseMeta[2]);

    LocalDateTime now = LocalDateTime.now();
    if (startDate != null && now.isBefore(startDate)) {
        throw new IllegalArgumentException("Admission for phase " + phase + " has not started yet (Starts: " + startDate + ")");
    }
    if (endDate != null && now.isAfter(endDate)) {
        throw new IllegalArgumentException("Admission for phase " + phase + " has ended (Ended: " + endDate + ")");
    }

    boolean useDist = "3".equals(user.roleId());
    String code = useDist ? user.distCode() : user.itiCode();

    String caste = req.caste() != null ? req.caste() : "all";
    String minqul = req.minqul() != null ? req.minqul() : "all";

    // Parse filters coming from the request DTO (strings) into LocalDate/LocalTime
    LocalTime filterTime = null;
    try {
        filterTime = resolveTime(req.calTime());
    } catch (IllegalArgumentException ex) {
        // If the provided time is invalid, treat as no time filter
        logger.debug("viewScheduleTimings: invalid calTime filter '{}', ignoring filter", req.calTime());
        filterTime = null;
    }

    List<Object[]> rowList = admissionTimingRepository.findFilteredScheduleRows(useDist, code, phase, year, caste, minqul);

    // Native columns: cal_date, merit_from, merit_to, caste, minqul, cal_time.
    // Null-safe per element -- a single malformed row must not hide every other schedule behind it.
    List<ScheduleViewResponse> formattedList = new ArrayList<>();

    DateTimeFormatter dateWriter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    for (Object[] row : rowList) {
        LocalDate rowDate = asLocalDate(row[0]);
        LocalTime rowTime = asLocalTime(row[5]);

        if (filterTime != null && !filterTime.equals(rowTime)) {
            continue;
        }

        formattedList.add(new ScheduleViewResponse(
            rowDate == null ? "" : rowDate.format(dateWriter),
            row[1] + "-" + row[2],
            row[3] == null ? null : String.valueOf(row[3]),
            row[4] == null ? null : String.valueOf(row[4]),
            rowTime
        ));
    }

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("heading", useDist ? "Admission Schedule" : "Admission Counselling");
    response.put("data", formattedList);

    return response;
}
public Map<String,Object> getCurrentStatus(){
    String year=resolveCurrentYear();
    String phase=resolveCurrentPhase(year);
    Map<String,Object> response=new HashMap<>();
    response.put("success",true);
    response.put("year",year);
    response.put("phase",phase);

    return response;
 }
}
