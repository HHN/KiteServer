package com.hhn.kite2server;

import com.hhn.kite2server.logger.LoggerConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories("com.hhn.kite2server.*")
@EntityScan("com.hhn.kite2server.*")
public class Kite2ServerApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(Kite2ServerApplication.class, args);
            throw new Exception("Test Exception!");
        } catch (Exception e) {
            LoggerConfig loggerConfig = new LoggerConfig();
            loggerConfig.configureLogger();
            loggerConfig.getLogger().severe("Error encounter: " + e.getMessage());
        }
    }

}
