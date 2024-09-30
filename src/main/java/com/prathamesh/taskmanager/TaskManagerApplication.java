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

    @GetMapping("/")
    public String index(){
        return "<body style='background-color: black; color: white; display: flex; justify-content:center; align-items : center;'><h1>Welcome to The Task Manager Server, End points are protected prefer not to stay here....</h1></body>";
    }
}
