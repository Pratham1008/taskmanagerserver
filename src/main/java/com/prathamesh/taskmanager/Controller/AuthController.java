package com.prathamesh.taskmanager.Controller;

import com.prathamesh.taskmanager.Service.AuthService;
import com.prathamesh.taskmanager.Model.AuthResponse;
import com.prathamesh.taskmanager.Model.Users;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@RequestBody Users user){
        return ResponseEntity.ok(authService.registerUser(user));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody Users user, HttpServletResponse response){
        return ResponseEntity.ok(authService.authenticate(user, response));
    }

    @GetMapping("/user/{username}")
    public Users getUser(@PathVariable String username){
        return authService.getUser(username);
    }
}
