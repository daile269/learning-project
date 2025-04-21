package com.learning.core_service.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Locale;

@RestController
@RequiredArgsConstructor
public class GreetingController {
    private final MessageSource messageSource;

    @GetMapping("/hello")
    public String greeting(@RequestParam(name = "name", defaultValue = "World") String name,
                           HttpServletRequest request){
        Locale locale = request.getLocale();
        return messageSource.getMessage("greeting",new Object[]{name},locale);
    }
}
