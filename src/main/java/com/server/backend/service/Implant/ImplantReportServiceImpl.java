package com.server.backend.service.Implant;
import com.server.backend.DTO.Industries.ImplantIndustryResponse;
import com.server.backend.DTO.Industries.ImplantReportDTO;
import com.server.backend.Repository.PlacementsRepositories.ImplantReportRepository;
import org.apache.poi.ss.usermodel.Sheet;
import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ImplantReportServiceImpl implements ImplantReportService {

    private final ImplantReportRepository repository;

    public ImplantReportServiceImpl(ImplantReportRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<ImplantIndustryResponse> getIndustries(Integer itiCode) {

        return repository.getIndustries(itiCode)
                .stream()
                .map(row -> new ImplantIndustryResponse(
                        ((Number) row[0]).longValue(),
                        (String) row[1]
                ))
                .toList();
    }

    @Override
    public List<ImplantReportDTO> getImplantReportByIndustry(Integer industryId) {

        return repository.getImplantReportByIndustry(industryId)
                .stream()
                .map(row -> new ImplantReportDTO(
                        ((Number) row[0]).longValue(),   // implantId
                        ((Number) row[1]).longValue(),   // industryId
                        (String) row[2],                // industryName
                        (String) row[3],                // facultyName
                        (String) row[4],                // tradeShort
                        (String) row[5],                // industryAddress
                        ((Number) row[6]).longValue(),  // hrNo
                        rsDate(row[7]),                // fromDate
                        rsDate(row[8]),                // toDate
                        ((Number) row[9]).intValue(),   // noOfDays
                        ((Number) row[10]).intValue(),  // noOfStudents
                        (String) row[11],               // location
                        (String) row[12]                // description
                ))
                .toList();
    }

    private java.util.Date rsDate(Object value) {
        return value == null ? null : (java.util.Date) value;
    }
    @Override
public byte[] downloadExcel(Integer industryId) {

    List<ImplantReportDTO> reports =
            getImplantReportByIndustry(industryId);

    try (Workbook workbook = new XSSFWorkbook();
         ByteArrayOutputStream out = new ByteArrayOutputStream()) {

        Sheet sheet = workbook.createSheet("InPlant Report");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("Implant ID");
        header.createCell(1).setCellValue("Industry ID");
        header.createCell(2).setCellValue("Industry Name");
        header.createCell(3).setCellValue("Faculty Name");
        header.createCell(4).setCellValue("Trade");
        header.createCell(5).setCellValue("Industry Address");
        header.createCell(6).setCellValue("HR No");
        header.createCell(7).setCellValue("From Date");
        header.createCell(8).setCellValue("To Date");
        header.createCell(9).setCellValue("No Of Days");
        header.createCell(10).setCellValue("No Of Students");
        header.createCell(11).setCellValue("Location");
        header.createCell(12).setCellValue("Description");

        int rowNum = 1;

        for (ImplantReportDTO report : reports) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(report.getImplantId());
            row.createCell(1).setCellValue(report.getIndustryId());
            row.createCell(2).setCellValue(report.getIndustryName());
            row.createCell(3).setCellValue(report.getFacultyName());
            row.createCell(4).setCellValue(report.getTradeShort());
            row.createCell(5).setCellValue(report.getIndustryAddress());
            row.createCell(6).setCellValue(report.getHrNo());

            row.createCell(7).setCellValue(
                    report.getFromDate() == null ? "" : report.getFromDate().toString());

            row.createCell(8).setCellValue(
                    report.getToDate() == null ? "" : report.getToDate().toString());

            row.createCell(9).setCellValue(report.getNoOfDays());
            row.createCell(10).setCellValue(report.getNoOfStudents());
            row.createCell(11).setCellValue(report.getLocation());
            row.createCell(12).setCellValue(report.getDescription());
        }

        for (int i = 0; i < 13; i++) {
            sheet.autoSizeColumn(i);
        }

        workbook.write(out);

        return out.toByteArray();

    } catch (Exception e) {
        throw new RuntimeException("Error generating Excel", e);
    }
}
}