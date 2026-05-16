package nl.bioinf.ishofstede.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;


@RestController
@RequestMapping(value = "/hello")
public class HelloRest {

    @GetMapping(value = "/rest1")
    public String getHello(String rest1) {

        return "Hello Spring Boot!";
    }
}

