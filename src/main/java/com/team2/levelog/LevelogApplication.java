package com.team2.levelog;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class LevelogApplication {

    public static void main(String[] args) {
        SpringApplication.run(LevelogApplication.class, args);
    }

}
