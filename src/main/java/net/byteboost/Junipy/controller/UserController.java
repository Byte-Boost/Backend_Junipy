package net.byteboost.junipy.controller;

import net.byteboost.junipy.dto.LoginRequest;
import net.byteboost.junipy.dto.RegisterRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import net.byteboost.junipy.model.User;
import net.byteboost.junipy.service.IUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private final IUserService userService;

    public UserController(IUserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> all() { return userService.getAllUsers(); }

}
