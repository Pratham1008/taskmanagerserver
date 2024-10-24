package com.prathamesh.taskmanager.Controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@AllArgsConstructor
@RestController
public class PingController {
    private final RestTemplate restTemplate;

    @Scheduled(fixedRate = 180000)
    public void keepServerAlive() {
        String url = "https://taskmanagerserver-9zih.onrender.com/ping";
        String message = "keep-alive";
        try {
            restTemplate.postForEntity(url, message, String.class);
            System.out.println("Ping sent successfully.");
        } catch (Exception e) {
            System.out.println("Error pinging server: " + e.getMessage());
        }
    }

    @PostMapping("/ping")
    public ResponseEntity<String> ping(@RequestBody String message) {
        return ResponseEntity.ok("Ping received: " + message);
    }
}
