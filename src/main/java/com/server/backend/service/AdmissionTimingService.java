package com.server.backend.service;
import org.springframework.stereotype.Service;
import java.util.List;
import com.server.backend.entity.AdmissionTiming;
import com.server.backend.Repository.AdmissionTimingRepository;
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
    public AdmissionTiming getById(String itiCode) {
        return admissionTimingRepository.findById(itiCode).orElse(null);
    }
    public void delete(String itiCode){
        admissionTimingRepository.deleteById(itiCode);
    }
    public AdmissionTiming updateAdmissionTiming(String itiCode, AdmissionTiming updatedAdmissionTiming) {
        updatedAdmissionTiming.setItiCode(itiCode);
        return admissionTimingRepository.save(updatedAdmissionTiming);
    }
}