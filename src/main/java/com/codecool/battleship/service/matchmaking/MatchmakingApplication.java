package com.codecool.battleship.service.matchmaking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class MatchmakingApplication {

    @RequestMapping(value = "/")
    public String home() {
        return "Hello! I make matches...";
    }

    public static void main(String[] args) {
        SpringApplication.run(MatchmakingApplication.class, args);
    }

}
