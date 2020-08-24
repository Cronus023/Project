package by.project.first.controllers;

import by.project.first.models.Message;
import by.project.first.models.RoleModel;
import by.project.first.models.UserModel;
import by.project.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@RestController
@CrossOrigin(origins = "http://localhost:8000")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserModel user) {
        return userService.login(user);
    }

    @PostMapping("/register")
    public ResponseEntity<Message> register(@RequestBody UserModel user) {
        return userService.register(user);
    }

    @GetMapping("/authLogout")
    public ResponseEntity<Message> authLogout(@RequestParam String login) {
        return userService.logout(login);
    }

    @GetMapping("/get_roles")
    public ResponseEntity<Set<RoleModel>> get_user_roles(@RequestParam String login) {
        return userService.get_roles(login);
    }

    @GetMapping("/auth/check")
    public ResponseEntity<Message> checkAuth(@RequestParam String token) {
        return userService.checkAuth(token);
    }
}
