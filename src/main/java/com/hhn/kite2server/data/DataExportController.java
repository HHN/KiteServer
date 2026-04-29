package com.hhn.kite2server.data;

import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

/**
 * Controller providing functionality to export stored interaction data as CSV.
 */
@RestController
@AllArgsConstructor
public class DataExportController {

    private final DataService dataService;

    @GetMapping("/data/export")
    public void exportData(HttpServletResponse response) throws IOException {
        // Set content type and headers to trigger a file download in the browser
        response.setContentType("text/csv; charset=UTF-8");
        response.setHeader("Content-Disposition", "attachment; filename=\"data_objects.csv\"");

        // Retrieve all stored interaction data
        List<DataObject> dataObjects = dataService.getAllDataObjects().getDataObjects();

        PrintWriter writer = response.getWriter();
        // Write CSV Header
        writer.write("id,prompt,completion,createdAt\n");

        // Write CSV data rows
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

    // RFC4180 compliance: quote only if necessary, escape double quotes
    private String toCsvField(String value) {
        if (value == null) return "";

        // CSV Injection protection (Excel executes cells starting with =, +, -, @ as formulas).
        // We prepend a single quote to ensure Excel treats it as plain text.
        if (value.startsWith("=") || value.startsWith("+") || value.startsWith("-") || value.startsWith("@")) {
            value = "'" + value;
        }

        boolean mustQuote = value.contains(",") || value.contains("\"") || value.contains("\n") || value.contains("\r");
        String escaped = value.replace("\"", "\"\"");
        return mustQuote ? "\"" + escaped + "\"" : escaped;
    }
}
