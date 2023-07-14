package com.gmail.javacoded78.latwitter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LAtwitterApplication {

    public static void main(String[] args) {
        SpringApplication.run(LAtwitterApplication.class, args);
    }

}
