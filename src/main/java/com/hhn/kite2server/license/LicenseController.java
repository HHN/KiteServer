package com.hhn.kite2server.license;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping(path = {"license"})
@AllArgsConstructor
public class LicenseController {

    @GetMapping(produces = MediaType.TEXT_HTML_VALUE)
    public String getMarketingText() throws IOException {
        InputStream inputStream = getClass().getResourceAsStream("/templates/license_agreement.html");
        if (inputStream == null) {
            throw new FileNotFoundException("Resource not found: /templates/license_agreement.html");
        }
        return new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);
    }
}
