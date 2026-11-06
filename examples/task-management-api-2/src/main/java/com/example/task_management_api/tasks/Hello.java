package com.example.task_management_api.tasks;

import org.springframework.stereotype.Component;

@Component()
public class Hello {
    public Hello() {
        System.out.println("Hello bean created");
    }
}
