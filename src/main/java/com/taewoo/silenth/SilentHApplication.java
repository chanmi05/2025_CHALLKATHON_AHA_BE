package com.taewoo.silenth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SilentHApplication {

    public static void main(String[] args) {
        SpringApplication.run(SilentHApplication.class, args);
    }

}
