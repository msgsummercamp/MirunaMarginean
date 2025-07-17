package com.example;

import org.springframework.stereotype.Component;

@Service("spanishGreetingService")
public class SpanishGreetingService implements GreetingService {
    public void greet() {
        System.out.println("¡Hola!");
    }
}