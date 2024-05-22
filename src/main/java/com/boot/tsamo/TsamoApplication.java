package com.boot.tsamo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class TsamoApplication {

    public static void main(String[] args) {
        SpringApplication.run(TsamoApplication.class, args);
    }

}
