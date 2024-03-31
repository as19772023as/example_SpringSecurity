package ru.strebkov.example_SpringSecurity;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@SpringBootApplication
public class ExampleSpringSecurityApplication {

    public static void main(String[] args) {
        SpringApplication.run(ExampleSpringSecurityApplication.class, args);
    }

    @GetMapping("/hi")
    public String hi() {
        return "hi, not authenticated user!";
    }
    @GetMapping("/read")
    public String read() {
        return "read!";
    }
    @GetMapping("/write")
    public String write() {
        return "write!";
    }
    @GetMapping("/hello")
    public String hello() {
        return "hello!";
    }

}


