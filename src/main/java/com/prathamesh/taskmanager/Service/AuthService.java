package com.prathamesh.taskmanager.Service;

import com.prathamesh.taskmanager.Model.AuthResponse;
import com.prathamesh.taskmanager.Model.Users;
import com.prathamesh.taskmanager.Repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private JwtService jwtService;
    @Autowired
    private AuthenticationManager authenticationManager;

    public AuthResponse registerUser(Users request, HttpServletResponse response) {
        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user = userRepository.save(user);

        String token = jwtService.generateToken(user,response);

        return new AuthResponse(token);

    }

    public AuthResponse authenticate(Users request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Users user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user,response);

        return new AuthResponse(token);
    }

    public Users getUser(String username){
        return userRepository.findByEmail(username).orElseThrow();
    }
}
