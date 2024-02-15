package com.hhn.kite2server.reviewobserver;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Optional;

@Configuration
public class LoadReviewObserver {

    @Bean
    CommandLineRunner initDatabase(ReviewObserverRepository repository) {
        return args -> {
            AddEmailToDatabase("Nicola.Marsden@hs-heilbronn.de", repository);
            AddEmailToDatabase("Tim.Reichert@hs-heilbronn.de", repository);
            AddEmailToDatabase("Mergim.Miftari@hs-heilbronn.de", repository);
            AddEmailToDatabase("Yvonne.Tang@hs-heilbronn.de", repository);
            AddEmailToDatabase("jleidel@stud.hs-heilbronn.de", repository);
            AddEmailToDatabase("Andrea.Block@hs-heilbronn.de", repository);
            AddEmailToDatabase("Claudia.Herling@hs-heilbronn.de", repository);
        };
    }

    private void AddEmailToDatabase(String email, ReviewObserverRepository repository) {
        Optional<ReviewObserver> existingObserver = repository.findByEmail(email);
        if (existingObserver.isPresent()) {
            return;
        }
        ReviewObserver reviewObserver = new ReviewObserver();
        reviewObserver.setEmail(email);
        repository.save(reviewObserver);
    }
}
