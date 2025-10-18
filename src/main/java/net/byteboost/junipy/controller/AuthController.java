package net.byteboost.junipy.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import net.byteboost.junipy.dto.LoginRequest;
import net.byteboost.junipy.dto.RegisterRequest;
import net.byteboost.junipy.model.User;
import net.byteboost.junipy.security.JwtUtil;
import net.byteboost.junipy.service.IUserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired private IUserService userService;
    @Autowired private PasswordEncoder passwordEncoder;
    @Autowired private JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest req) {
        if (userService.getUserByEmail(req.getEmail()) != null)
            return ResponseEntity.badRequest().body("Email in use");

        if (!req.getPassword().equals(req.getConfirmPassword()))
            return ResponseEntity.badRequest().body("Passwords do not match");

        User user = new User(req.getUsername(), req.getEmail(), passwordEncoder.encode(req.getPassword()));
        userService.createUser(user);
        return ResponseEntity.ok("Registered");
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest req) {
        User user = userService.getUserByEmail(req.getEmail());
        if (user == null || !passwordEncoder.matches(req.getPassword(), user.getPassword()))
            return ResponseEntity.status(401).body("Invalid credentials");

        String token = jwtUtil.generateToken(user);
        return ResponseEntity.ok(Map.of("token", token));
    }
}
