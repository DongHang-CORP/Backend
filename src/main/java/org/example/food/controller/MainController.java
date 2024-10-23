package org.example.food.controller;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @GetMapping
    public String main(){
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return "Main" + name;
    }

}