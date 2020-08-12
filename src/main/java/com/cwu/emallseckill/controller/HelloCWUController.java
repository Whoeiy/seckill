package com.cwu.emallseckill.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloCWUController {

    @GetMapping("/hello")
    public String helloCWU(){
        return "HELLO, CWU!";
    }
}
