package com.server.backend.service;
import org.springframework.stereotype.Service;
import java.util.List;
import com.server.backend.entity.AdmissionTiming;
import com.server.backend.Repository.AdmissionTimingRepository;
import com.server.backend.entity.AdmissionTimingId;
@Service
public class AdmissionTimingService {
    private final AdmissionTimingRepository admissionTimingRepository;
    public AdmissionTimingService(AdmissionTimingRepository admissionTimingRepository) {
        this.admissionTimingRepository = admissionTimingRepository;
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
}