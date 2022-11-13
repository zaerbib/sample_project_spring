package com.apress.calendar.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Locale;

@Controller
public class WelcomeController {

    private static final Logger log = LoggerFactory.getLogger(WelcomeController.class);

    private final ApplicationContext context;

    public WelcomeController(ApplicationContext context) {
        this.context = context;
    }

    @GetMapping(value="/")
    public String welcome(){
        // String name = context.getMessage("customer.name", new Object[]{46, "http://www.baselogic.com"}, Locale.US);
        log.info("*** welcome name (English) : ");
        return "index";
    }
}
