package com.server.backend.service.Implant;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.server.backend.DTO.Implant.IndustryPartnerExcelRow;
import com.server.backend.Repository.PlacementsRepositories.IndustryPartnerDetailsRepository;

@Service
public class IndustryPartnerDetailsExcelService {

    private final IndustryPartnerDetailsRepository repository;

    public IndustryPartnerDetailsExcelService(
            IndustryPartnerDetailsRepository repository) {
        this.repository = repository;
    }

    public byte[] generateExcel() throws IOException {

        List<IndustryPartnerExcelRow> details =
                repository.findIndustryPartnerExcelRows();

        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream outputStream =
                     new ByteArrayOutputStream()) {

            Sheet sheet =
                    workbook.createSheet("Industry Partner Details");

            // Header row
            Row header = sheet.createRow(0);

            header.createCell(0).setCellValue("SNO");
            header.createCell(1).setCellValue("DIST");
            header.createCell(2).setCellValue("ITI");
            header.createCell(3)
                    .setCellValue("REVISED LEAD SECTOR");
            header.createCell(4)
                    .setCellValue("PROPOSED NEW TRADE");
            header.createCell(5)
                    .setCellValue("REVISED LEAD INDUSTRY PARTNER");

            // Data rows
            int rowNumber = 1;
            int sno = 1;

            for (IndustryPartnerExcelRow detail : details) {

                Row row = sheet.createRow(rowNumber++);

                row.createCell(0).setCellValue(sno++);

                row.createCell(1).setCellValue(
                        detail.getDistName() != null
                                ? detail.getDistName()
                                : "");

                row.createCell(2).setCellValue(
                        detail.getItiName() != null
                                ? detail.getItiName()
                                : "");

                row.createCell(3).setCellValue(
                        detail.getRevisedLeadSector() != null
                                ? detail.getRevisedLeadSector()
                                : "");

                row.createCell(4).setCellValue(
                        detail.getProposedNewTrade() != null
                                ? detail.getProposedNewTrade()
                                : "");

                row.createCell(5).setCellValue(
                        detail.getRevisedLeadIndustryPartner() != null
                                ? detail.getRevisedLeadIndustryPartner()
                                : "");
            }

            // Adjust column widths
            for (int i = 0; i < 6; i++) {
                sheet.autoSizeColumn(i);
            }

            workbook.write(outputStream);

            return outputStream.toByteArray();
        }
    }
}