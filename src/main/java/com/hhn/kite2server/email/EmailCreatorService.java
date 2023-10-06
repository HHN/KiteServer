package com.hhn.kite2server.email;

import lombok.AllArgsConstructor;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@AllArgsConstructor
@Service
public class EmailCreatorService {
    private final ResourceLoader resourceLoader;

    public String buildEmailConfirmRegistrationAddress(String link) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/templates/confirm_address_email.html");
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: /templates/confirm_address_email.html");
            }

            // Lesen Sie den Stream in einen String
            String email = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            email = email.replace("{link}", link);
            return email;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String buildEmailConfirmResetOfPassword(String link) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/templates/email_confirm_reset.html");
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: /templates/email_confirm_reset.html");
            }

            // Lesen Sie den Stream in einen String
            String email = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            email = email.replace("{link}", link);
            return email;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String buildEmailInformAboutNewPassword(String newPassword) {
        try {
            InputStream inputStream = getClass().getResourceAsStream("/templates/new_password_email.html");
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: /templates/new_password_email.html");
            }

            // Lesen Sie den Stream in einen String
            String email = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            email = email.replace("{password}", newPassword);
            return email;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
