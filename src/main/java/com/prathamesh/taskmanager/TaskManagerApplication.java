package com.prathamesh.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@SpringBootApplication
@EnableScheduling
@Controller
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @GetMapping("/")
    public String index(){
        return "Desclaimer";
    }
}
