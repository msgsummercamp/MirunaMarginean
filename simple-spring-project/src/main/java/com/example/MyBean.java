package com.example;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class MyBean {
    private final GreetingService greetingService;

    @Autowired
    public MyBean(@Qualifier("spanishGreetingService") GreetingService greetingService) {
        this.greetingService = greetingService;
    }

    public void sayHello() {
        greetingService.greet();
    }
}