package com.hhn.kite2server.email;

import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@AllArgsConstructor
@Service
public class EmailCreatorService {

    private final ResourceLoader resourceLoader;

    public String buildEmailConfirmRegistrationAddress(String link) {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/confirm_address_email.html");
            String email = new String(Files.readAllBytes(Paths.get(resource.getURI())));
            email = email.replace("{link}", link);
            return email;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String buildEmailConfirmResetOfPassword(String link) {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/email_confirm_reset.html");
            String email = new String(Files.readAllBytes(Paths.get(resource.getURI())));
            email = email.replace("{link}", link);
            return email;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String buildEmailInformAboutNewPassword(String newPassword) {
        try {
            Resource resource = resourceLoader.getResource("classpath:templates/new_password_email.html");
            String email = new String(Files.readAllBytes(Paths.get(resource.getURI())));
            email = email.replace("{password}", newPassword);
            return email;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
