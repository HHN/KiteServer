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
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=\"data_objects.csv\"");

        // Alle DataObjects abrufen
        List<DataObject> dataObjects = dataService.getAllDataObjects().getDataObjects();

        PrintWriter writer = response.getWriter();
        // CSV-Header
        writer.write("id,prompt,completion,createdAt\n");

        // CSV-Datenzeilen
        for (DataObject data : dataObjects) {
            writer.write(String.format("%d,\"%s\",\"%s\",%s\n",
                    data.getId(),
                    escapeCsv(data.getPrompt()),
                    escapeCsv(data.getCompletion()),
                    data.getCreatedAt() != null ? data.getCreatedAt().toString() : ""
            ));
        }

        writer.flush();
    }

    // Hilfsmethode zum Escapen von CSV-sensitiven Zeichen
    private String escapeCsv(String value) {
        if (value == null) {
            return "";
        }
        // Ersetze doppelte Anführungszeichen und setze das Feld in Anführungszeichen
        return value.replace("\"", "\"\"");
    }
}
