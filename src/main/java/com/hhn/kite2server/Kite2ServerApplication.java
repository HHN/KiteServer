package com.hhn.kite2server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Arrays;

@SpringBootApplication
@EnableJpaRepositories("com.hhn.kite2server.*")
@EntityScan("com.hhn.kite2server.*")
public class Kite2ServerApplication {

    public static void main(String[] args) {
        try{
            SpringApplication.run(Kite2ServerApplication.class, args);
        } catch (Exception e) {

        }
    }

}
