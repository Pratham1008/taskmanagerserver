package com.prathamesh.taskmanager;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
public class TaskManagerApplication {

    public static void main(String[] args) {
        SpringApplication.run(TaskManagerApplication.class, args);
    }

    @GetMapping("/home")
    public String index(){
        return "<h1>Welcome to The Task Manager Server</h1><br>" +
                "<p>The end points of this server are protected if you are here by mistake prefer returning back</p>";
    }
}
