package com.hhn.kite2server.logger;

import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.io.IOException;
public class LoggerConfig {

    public void configureLogger() {
        Logger logger = Logger.getLogger("ErrorLogger");
        FileHandler fileHandler;

        try {
            // Definieren Sie den Dateinamen und maximalen Dateigröße und Anzahl von Dateien
            fileHandler = new FileHandler("server.log", 0, 2, true); // "server.log" ist der Dateiname

            // Verwenden Sie ein einfaches Format für die Protokolle
            fileHandler.setFormatter(new SimpleFormatter());

            // Fügen Sie den FileHandler zum Logger hinzu
            logger.addHandler(fileHandler);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Logger getLogger(){
        return Logger.getLogger("ErrorLogger");
    }
}
