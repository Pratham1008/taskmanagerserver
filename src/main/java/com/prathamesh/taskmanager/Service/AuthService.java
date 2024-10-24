package com.prathamesh.taskmanager.Service;

import com.prathamesh.taskmanager.Model.AuthResponse;
import com.prathamesh.taskmanager.Model.Users;
import com.prathamesh.taskmanager.Repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponse registerUser(Users request) {
        Users user = new Users();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setRole(request.getRole());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        return new AuthResponse("User Registered");
    }

    public AuthResponse authenticate(Users request, HttpServletResponse response) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );

        Users user = userRepository.findByEmail(request.getEmail()).orElseThrow();
        String token = jwtService.generateToken(user,response);

        return new AuthResponse(token);
    }

    public Users getUser(String username) {
        return userRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }
}
