package com.hhn.kite2server.data;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@AllArgsConstructor
public class DataExportController {

    private final DataService dataService;

    @GetMapping("/data/export")
    public void exportData(HttpServletResponse response) throws IOException {
        // Setze den Content-Type und den Header, damit der Browser den Download startet
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"data_objects.csv\"");

        // Alle DataObjects abrufen
        List<DataObject> dataObjects = dataService.getAllDataObjects().getDataObjects();

        PrintWriter writer = response.getWriter();
        // CSV-Header
        writer.write("id,prompt,completion,createdAt\n");

        // CSV-Datenzeilen
        for (DataObject data : dataObjects) {
            writer.append(String.valueOf(data.getId() != null ? data.getId() : ""))
                    .append(',')
                    .append(toCsvField(data.getPrompt()))
                    .append(',')
                    .append(toCsvField(data.getCompletion()))
                    .append(',')
                    .append(toCsvField(data.getCreatedAt() != null ? data.getCreatedAt().toString() : ""))
                    .append('\n');
        }

        writer.flush();
    }

    // RFC4180-ish: Quote nur wenn nötig, Quotes verdoppeln
    private String toCsvField(String value) {
        if (value == null) return "";
        boolean mustQuote = value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r");
        String escaped = value.replace("\"", "\"\"");
        return mustQuote ? "\"" + escaped + "\"" : escaped;
    }
}
