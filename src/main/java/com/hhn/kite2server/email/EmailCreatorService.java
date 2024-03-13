package com.hhn.kite2server.email;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@AllArgsConstructor
@Service
public class EmailCreatorService {
    private static final Logger logger = LoggerFactory.getLogger(EmailCreatorService.class);

    public String buildEmailForNotificationAboutNovelReview(String novelName, String score, String reviewText) {
        try (InputStream inputStream = getClass().getResourceAsStream("/templates/novel_bewertungs_benachrichtigung_email.html")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: /templates/novel_bewertungs_benachrichtigung_email.html");
            }

            // Lesen Sie den Stream in einen String
            String email = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            email = email.replace("{visual_novel_name}", novelName);
            email = email.replace("{rating_number}", score);
            email = email.replace("{rating_text}", reviewText);
            return email;
        } catch (IOException e) {
            logger.error("Error occured while building email for notification about novel review.", e);
            return null;
        }
    }

    public String buildEmailForNotificationAboutAiReview(String novelName, String aiPrompt, String aiReview, String reviewText) {
        try (InputStream inputStream = getClass().getResourceAsStream("/templates/ai_bwertung_benachrichtigung_email.html")) {
            if (inputStream == null) {
                throw new FileNotFoundException("Resource not found: /templates/ai_bwertung_benachrichtigung_email.html");
            }

            // Lesen Sie den Stream in einen String
            String email = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            email = email.replace("{visual_novel_name}", novelName);
            email = email.replace("{ai_prompt}", aiPrompt);
            email = email.replace("{ai_review}", aiReview);
            email = email.replace("{review_of_review}", reviewText);
            return email;
        } catch (IOException e) {
            logger.error("Error occured while building email for notification about ai review.", e);
            return null;
        }
    }
}
