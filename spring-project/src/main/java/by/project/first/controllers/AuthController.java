package by.project.first.controllers;

import by.project.first.models.Message;
import by.project.first.models.RoleModel;
import by.project.first.models.UserModel;
import by.project.first.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
public class AuthController {

    private final UserService userService;

    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }

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

    @GetMapping("/getRoles")
    public Set<RoleModel> getRoles(@RequestParam String login) {
        return userService.getRoles(login);
    }

    @GetMapping("/auth/check")
    public ResponseEntity<Message> checkAuth(@RequestParam String token) {
        return userService.checkAuth(token);
    }

}
