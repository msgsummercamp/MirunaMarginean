package com.example;

import org.springframework.stereotype.Component;

@Service("englishGreetingService")
public class EnglishGreetingService implements GreetingService {
    public void greet() {
        System.out.println("Hello!");
    }
}