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

    private LocalDate resolveDate(String value) {
        // A blank date used to fall back to LocalDate.now(), so submitting the schedule form
        // with an empty date silently created a schedule starting today.
        if (value == null || value.isBlank()) {
            throw new IllegalArgumentException("Date is required");
        }

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

    Integer nextPk = admissionTimingRepository.getNextTempPkVal();

    AdmissionTiming timing = new AdmissionTiming();
    timing.setItiCode(user.itiCode());
    timing.setDistCode(user.distCode());
    timing.setMinqul(quality);
    timing.setCaste(caste);
    timing.setMeritFrom(0);
    timing.setMeritTo(0);
    timing.setCalDate(resolveDate(req.calDate()));
    timing.setCalTime(resolveTime(req.calTime()));
    timing.setPhase(phase);
    timing.setYear(year);
    
    try {
        timing.setTrno(Integer.valueOf(user.insCode()));
    } catch (NumberFormatException e) {
        timing.setTrno(0);
    }
    timing.setTempPk(String.valueOf(nextPk));

    AdmissionTiming savedRecord = admissionTimingRepository.save(timing);

    boolean useDist = "3".equals(user.roleId());
    String entityName = useDist 
        ? admissionTimingRepository.findDistName(user.distCode()).orElse("Unknown")
        : admissionTimingRepository.findItiName(user.itiCode()).orElse("Unknown");

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "Schedule entry created successfully");
    response.put("data", savedRecord);
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

    Optional<AdmissionTiming> placeholderOpt = useDist 
        ? admissionTimingRepository.findFirstByDistCodeAndPhaseAndYearAndCasteAndMinqulAndMeritFrom(entityValue, phase, year, caste, quality, 0)
        : admissionTimingRepository.findFirstByItiCodeAndPhaseAndYearAndCasteAndMinqulAndMeritFrom(entityValue, phase, year, caste, quality, 0);

    if (placeholderOpt.isEmpty()) {
        throw new IllegalArgumentException("No available placeholder found for this " 
            + (useDist ? "District" : "ITI") + " and category. Please create it first.");
    }
    AdmissionTiming target = placeholderOpt.get();

    LocalDate requestedDate = resolveDate(req.calDate());
    LocalTime requestedTime = resolveTime(req.calTime());

    boolean hasDateTimeOverlap = useDist
        ? admissionTimingRepository.existsByDistCodeAndPhaseAndYearAndCalDateAndCalTimeAndTempPkNot(entityValue, phase, year, requestedDate, requestedTime, target.getTempPk())
        : admissionTimingRepository.existsByItiCodeAndPhaseAndYearAndCalDateAndCalTimeAndTempPkNot(entityValue, phase, year, requestedDate, requestedTime, target.getTempPk());

    if (hasDateTimeOverlap) {
        throw new IllegalArgumentException("The date " + requestedDate + " at " + requestedTime 
            + " is already booked for another session at this " + (useDist ? "District" : "ITI") + ".");
    }

    List<AdmissionTiming> overlapList = useDist
        ? admissionTimingRepository.findOverlappingDistMeritRanges(entityValue, phase, year, target.getTempPk(), req.meritFrom(), req.meritTo())
        : admissionTimingRepository.findOverlappingItiMeritRanges(entityValue, phase, year, target.getTempPk(), req.meritFrom(), req.meritTo());

    if (!overlapList.isEmpty()) {
        AdmissionTiming existing = overlapList.get(0);
        throw new IllegalArgumentException("The merit range " + req.meritFrom() + "-" + req.meritTo() 
            + " overlaps with an existing entry (" + existing.getMeritFrom() + "-" + existing.getMeritTo() + ") for this " 
            + (useDist ? "District" : "ITI") + ".");
    }

    target.setMeritFrom(req.meritFrom());
    target.setMeritTo(req.meritTo());
   
System.out.println("Merit From (Request) = " + req.meritFrom());
System.out.println("Merit To (Request) = " + req.meritTo());

System.out.println("Merit From (Target) = " + target.getMeritFrom());
System.out.println("Merit To (Target) = " + target.getMeritTo());

target.setCalDate(requestedDate);
target.setCalTime(requestedTime);

AdmissionTiming updatedRecord = admissionTimingRepository.save(target);
   
    String entityName = useDist 
        ? admissionTimingRepository.findDistName(user.distCode()).orElse("Unknown")
        : admissionTimingRepository.findItiName(user.itiCode()).orElse("Unknown");

    Map<String, Object> response = new HashMap<>();
    response.put("success", true);
    response.put("message", "Schedule timings updated successfully");
    response.put("data", updatedRecord);
    response.put("dist_name", useDist ? entityName : null);
    response.put("iti_name", useDist ? null : entityName);

    return response;
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

    List<AdmissionTiming> timingsList = admissionTimingRepository.findFilteredSchedules(useDist, code, phase, year, caste, minqul);
    if (filterTime != null) {
        // Applied in memory: the per-institution result is small, and binding a null calTime into
        // the query is what used to break this endpoint.
        List<AdmissionTiming> byTime = new ArrayList<>();
        for (AdmissionTiming timing : timingsList) {
            if (filterTime.equals(timing.getCalTime())) {
                byTime.add(timing);
            }
        }
        timingsList = byTime;
    }
    List<ScheduleViewResponse> formattedList = new ArrayList<>();

    DateTimeFormatter dateWriter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    for (AdmissionTiming t : timingsList) {
        String dateFormatted = t.getCalDate() != null ? t.getCalDate().format(dateWriter) : "";
        String meritRange = t.getMeritFrom() + "-" + t.getMeritTo();
        
        formattedList.add(new ScheduleViewResponse(
            dateFormatted,
            meritRange,
            t.getCaste(),
            t.getMinqul(),
            t.getCalTime()
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
