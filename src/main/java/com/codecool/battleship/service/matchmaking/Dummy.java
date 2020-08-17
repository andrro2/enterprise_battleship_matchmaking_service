package com.codecool.battleship.service.matchmaking;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class Dummy {

    @RequestMapping("/")
    public String index() {
        return "Greetings from Spring Boot!";
    }

}