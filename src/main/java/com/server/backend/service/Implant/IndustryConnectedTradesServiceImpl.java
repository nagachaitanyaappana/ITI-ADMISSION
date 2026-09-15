package com.server.backend.service.Implant;
import com.server.backend.DTO.Industries.IndustryConnectedTradesDTO;
import com.server.backend.Repository.PlacementsRepositories.IndustryConnectedTradesRepository;
import org.apache.poi.ss.usermodel.Sheet;
import java.io.ByteArrayOutputStream;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class IndustryConnectedTradesServiceImpl
        implements IndustryConnectedTradesService {

    private final IndustryConnectedTradesRepository repository;

    public IndustryConnectedTradesServiceImpl(
            IndustryConnectedTradesRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<IndustryConnectedTradesDTO> getReport() {
        return repository.getReport();
    }

@Override
public byte[] downloadExcel() {

    List<IndustryConnectedTradesDTO> data = repository.getReport();

    try (Workbook workbook = new XSSFWorkbook()) {

        Sheet sheet = workbook.createSheet("Industry Connected Trades");

        Row header = sheet.createRow(0);

        header.createCell(0).setCellValue("S.No");
        header.createCell(1).setCellValue("District");
        header.createCell(2).setCellValue("ITI Code");
        header.createCell(3).setCellValue("ITI Name");
        header.createCell(4).setCellValue("Trade");
        header.createCell(5).setCellValue("Total Trainees");
        header.createCell(6).setCellValue("Industry Name");

        int rowNum = 1;

        for (IndustryConnectedTradesDTO dto : data) {

            Row row = sheet.createRow(rowNum++);

            row.createCell(0).setCellValue(dto.getSno());
            row.createCell(1).setCellValue(dto.getDistrict());
            row.createCell(2).setCellValue(dto.getItiCode());
            row.createCell(3).setCellValue(dto.getItiName());
            row.createCell(4).setCellValue(dto.getTradeName());
            row.createCell(5).setCellValue(dto.getTotalTrainees());
            row.createCell(6).setCellValue(dto.getIndustryName());
        }

        for (int i = 0; i < 7; i++) {
            sheet.autoSizeColumn(i);
        }

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        workbook.write(out);

        return out.toByteArray();

    } catch (Exception e) {
        throw new RuntimeException("Error generating Excel", e);
    }
}


}